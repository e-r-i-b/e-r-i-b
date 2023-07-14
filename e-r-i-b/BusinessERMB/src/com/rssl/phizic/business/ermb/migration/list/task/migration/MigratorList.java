package com.rssl.phizic.business.ermb.migration.list.task.migration;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbClientSearchHelper;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.migration.MigrationHelper;
import com.rssl.phizic.business.ermb.migration.list.MigrationStatus;
import com.rssl.phizic.business.ermb.migration.list.config.ErmbListMigrationConfig;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Conflict;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationInfo;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Phone;
import com.rssl.phizic.business.ermb.migration.list.task.PhoneActivity;
import com.rssl.phizic.business.ermb.migration.list.task.TaskLog;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ClientService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mbv.MbvRegistrationInfo;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.*;

import static com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationPhoneUtils.*;
import static com.rssl.phizic.business.ermb.migration.list.task.TaskType.START_MIGRATION;

/**
 * ���������� ��������� ��������
 * @author Puzikov
 * @ created 20.01.14
 * @ $Author$
 * @ $Revision$
 */

public class MigratorList extends MigratorBase
{
	private static final ClientService clientService = new ClientService();

	private final Long currentBlock;
	private final TaskLog log;
	private final MigrationStats stats;

	/**
	 * ctor
	 * @param currentBlock ������� ����, � ������� �������� ��������
	 */
	public MigratorList(Long currentBlock) throws IOException, CounterException
	{
		this.log = new TaskLog(START_MIGRATION);
		log.add("�������� � ����� " + currentBlock + " ��������");
		this.currentBlock = currentBlock;
		this.stats = new MigrationStats();
	}

	/**
	 * ��������� ��������
	 * @param client ������, �������� ����� ������������
	 */
	public void migrate(Client client) throws BusinessException
	{
		try
		{
			//������� ������ CEDBO �� ������ �� csv
			ErmbClientSearchHelper clientSearchHelper = new ErmbClientSearchHelper();
			com.rssl.phizic.gate.clients.Client cedboClient = clientSearchHelper.findClient(
					client.getLastName(),
					client.getFirstName(),
					StringHelper.getNullIfEmpty(client.getMiddleName()),
					client.getBirthday(),
					null,
					//���������� ������� WAY � ���� ����� �� ����������
					client.getDocument(),
					ClientDocumentType.PASSPORT_WAY,
					StringHelper.getNullIfEmpty(client.getTb())
			);
			checkUdboState(client, cedboClient);

			//����� � �� �������� ����� ����������
			ActivePerson eribClient = getEribPerson(client);
			if (eribClient == null)
			{
				boolean isInflatable = currentBlock.equals(ConfigFactory.getConfig(ErmbListMigrationConfig.class).getInflatableBlockNumber());
				if (isInflatable)
				{
					eribClient = createPerson(client, cedboClient);
				}
				else
				{
					sendToNextBlock(client);
					stats.pass(client);
					return;
				}
			}

			MigrationHelper.initContext(eribClient);

			MigrationInfo info = migrate(client, eribClient);
			if (info != null && (info.getMbkMigrationId() != null || info.getMbvMigrationId() != null))
				clientService.saveMigrationInfo(info);
			clientService.setMigrationStatus(client, MigrationStatus.MIGRATED);
			stats.ok(client);
		}
		catch (UnresolvedConflictException e)
		{
			if (log != null)
			{
				log.add(e.getMessage());
			}
			stats.error(client);
			clientService.markClientBlockById(client.getId(), null);
		}
		catch (MigrationLogicException e)
		{
			if (log != null)
			{
				log.add("������ �� ����� �������� ������� id=" + client.getId() + ". " + e.getMessage());
			}
			stats.error(client);
			clientService.setMigrationError(client);
		}
		catch (Exception e)
		{
			if (log != null)
			{
				log.add("������ �� ����� �������� ������� id=" + client.getId())
				.add(e);
			}
			stats.error(client);
			clientService.setMigrationError(client);
		}
		finally
		{
			if (log != null)
				log.flush();
		}
	}

	/**
	 * �������� � ��� ���������� � ������ ���������
	 */
	public void flushInfo()
	{
		log.add("�������� � ����� " + currentBlock + " �����������")
		.add(stats.getInfo())
		.flush();
	}

	private void sendToNextBlock(Client client) throws Exception
	{
		Long nextBlock = getNextBlock();
		clientService.markClientBlockById(client.getId(), nextBlock);
	}

	private MigrationInfo migrate(Client segmentedClient, ActivePerson eribClient) throws Exception
	{
		//��������� ���������� ������
		List<Conflict> conflicts = clientService.findConflictsByClient(segmentedClient.getId(), false);
		ErmbProfileImpl profile = ErmbHelper.getErmbProfileByPerson(eribClient);

		clientResourcesService.updateResources(eribClient, false);
		List<CardLink> cardLinks = externalResourceService.getLinks(eribClient.getLogin(), CardLink.class, null);
		Set<MbkCard> cards = MigrationHelper.getCardsToMigrate(cardLinks);

		MbvRegistrationInfo mbvInfo = MigrationHelper.getMbvInfo(eribClient.asClient());
		//�������� ����������� ��������
		checkData(segmentedClient, conflicts, profile, cards, mbvInfo);

		//��������� ��������� � ���. �������
		if (segmentedClient.getSegment_5())
			processFifthSegment(segmentedClient);

		//����� ���������, ������� ������ � ���� �������, � ��������� �������� �������
		Set<Phone> possiblePhones = getAllErmbPhones(segmentedClient, conflicts);
		ErmbPhones ermbPhones = getPhonesForProfile(segmentedClient, possiblePhones);

		//�������� ��� ������������ �������� � ��������� ����
		Set<String> mbvPhones = getMbvPhoneNumbers(ermbPhones.phones);
		Set<String> mbvPhoneNumberDelete = getMbvPhoneNumberDelete(segmentedClient.getPhones(), conflicts);
		//��� ���������, ���������� � ��������, ��������� �������� ����� ��� �� (�� ��� ������� �������� � �������)
		mbvPhones.addAll(mbvPhoneNumberDelete);
		//�������� �� �������� � ��� ��� �������� ������� ������ � �������
		Set<String> mbkPhoneNumbersMigrate = getPhoneNumbers(ermbPhones.phones);
		Set<String> mbkPhoneNumberDelete = getMbkPhoneNumberDelete(segmentedClient.getPhones(), conflicts);

		MigrationInfo migrationInfo = new MigrationInfo();
		migrationInfo.setClient(segmentedClient);
		migrationInfo.setMigrationDate(Calendar.getInstance());
		try
		{
			//-------------------
			//BEGIN MIGRATION MBV
			//-------------------
			if (ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability()
					&& CollectionUtils.isNotEmpty(mbvPhones))
			{
				if (CollectionUtils.isNotEmpty(mbvInfo.getIdentities()))
				{
					UUID mbvMigrationId = getMbvService().beginMigration(mbvInfo.getIdentities().get(0));
					migrationInfo.setMbvMigrationId(mbvMigrationId);
				}
			}
			//-------------------
			//BEGIN MIGRATION MBK
			//-------------------
			Collection<MbkConnectionInfo> mbkConnections = new ArrayList<MbkConnectionInfo>();
			if (!mbkPhoneNumbersMigrate.isEmpty() || !mbkPhoneNumberDelete.isEmpty())
			{
				BeginMigrationResult mbkBeginResult = getMbkService().beginMigrationErmb(cards, mbkPhoneNumbersMigrate, mbkPhoneNumberDelete, MigrationType.LIST);
				Long migrationId = mbkBeginResult.getMigrationId();
				if (migrationId == null)
					throw new GateException("�� ������� ������������������� ������������ ���������� ���");
				migrationInfo.setMbkMigrationId(migrationId);
				mbkConnections.addAll(mbkBeginResult.getMbkConnectionInfo());
			}

			//���� ������ �������� ������� ��� ����, ������� �������
			if (ermbPhones.active != null)
			{
				profile = createOrUpdateErmbProfile(profile, eribClient, getPhoneNumbers(ermbPhones.phones), ermbPhones.active.getPhoneNumber());
				updateProfileWithMbkData(profile, mbkConnections, segmentedClient.getSegments());
				//��� �������� ������� �������� 3_2_3 ����� ��� ���� ������� ���������� ������� ��������� � ������ ���� � ��������� "���������"
				//��� 4-�� ��������: ��������� ������� ��� �� �.�. ���������
				if (segmentedClient.getSegment_3_2_3() || segmentedClient.getSegment_4())
					hideAccountsInSmsChannel(eribClient);
			}
		}
		//��� ����� ������� ���������� ������������ ���������� � ��������� �����
		catch (Exception e)
		{
			//----------------------
			//ROLLBACK MIGRATION MBK
			//----------------------
			try
			{
				if (migrationInfo.getMbkMigrationId() != null)
				{
					getMbkService().rollbackMigration(migrationInfo.getMbkMigrationId());
				}
			}
			catch (Exception e1)
			{
				log.add("�� ������� ������� ROLLBACK �������� ��� id=" + migrationInfo.getMbkMigrationId())
				.add(e1);
				if (ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability()
						&& migrationInfo.getMbvMigrationId() != null)
				{
					getMbvService().rollbackMigration(migrationInfo.getMbvMigrationId());
				}
				throw e1;
			}

			//----------------------
			//ROLLBACK MIGRATION MBV
			//----------------------
			if (ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability()
					&& migrationInfo.getMbvMigrationId() != null)
			{
				getMbvService().rollbackMigration(migrationInfo.getMbvMigrationId());
			}

			throw e;
		}
		//----------------------
		//COMMIT MIGRATION MBK
		//----------------------
		List<CommitMigrationResult> commitResult = null;
		if (migrationInfo.getMbkMigrationId() != null)
		{
			commitResult = getMbkService().commitMigrationErmb(migrationInfo.getMbkMigrationId());
		}
		//----------------------
		//COMMIT MIGRATION MBV
		//----------------------
		if (ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability()
				&& migrationInfo.getMbvMigrationId() != null)
		{
			getMbvService().commitMigration(migrationInfo.getMbvMigrationId());
		}

		if (migrationInfo.getMbkMigrationId() != null && profile != null)
			setTariffInfo(profile, commitResult);
		if (ermbPhones.active != null)
		{
			//���������� �������
			boolean sendWelcomeSms = ConfigFactory.getConfig(ErmbListMigrationConfig.class).isDefaultWelcomeSms();
			ErmbHelper.saveCreatedProfile(profile, sendWelcomeSms);
			ermbPhoneService.saveOrUpdateERMBPhones(profile.getPhoneNumbers(), Collections.<String>emptyList());

			sendCsaUpdatePhones(profile, ermbPhones.active.getPhoneNumber(), getPhoneNumbers(ermbPhones.phones));
			sendErmbConnected(profile);

			//��� �� ����������� ��������
			try
			{
				Set<String> nonActivePhoneNumbers = getNonActivePhoneNumbers(ermbPhones.phones, ermbPhones.active);
				MigrationSmsSender sender = new MigrationSmsSender(segmentedClient, ermbPhones.active, nonActivePhoneNumbers);
				sender.send();
			}
			catch (Exception e)
			{
				log.add("�� ������� ��������� ��� �� ����������� ��������. id=" + segmentedClient.getId())
						.add(e);
			}
		}

		return migrationInfo;
	}

	/**
	 * ����� ��������, ������� ������ � ������� �������
	 * ����� �������� ���������� ��������� 3+4, � ����� ��������� ��������
	 * @param segmentedClient ������ �� ���������
	 * @param possiblePhones ��������, ������� ����� ������������ �������
	 * @return �������� �������, ��� ��������
	 * @throws MigrationLogicException
	 */
	private ErmbPhones getPhonesForProfile(Client segmentedClient, Set<Phone> possiblePhones) throws MigrationLogicException
	{
		ErmbPhones ermbPhones = new ErmbPhones();

		//�������������� ���������� ��������� 3/4
		if (segmentedClient.getSegment_3() && segmentedClient.getSegment_4())
		{
			PhoneActivity phoneActivity = new PhoneActivity(segmentedClient, possiblePhones);
			PhoneActivity.Result result = phoneActivity.calculate();
			ermbPhones.active = result.getMain();
			ermbPhones.phones.addAll(result.getPhones());
		}
		//��� �������� 4 (��� 3) ����� ������� �������� ������� �� ���� �� ���������
		else if (segmentedClient.getSegment_4())
		{
			if (CollectionUtils.isEmpty(possiblePhones))
				throw new MigrationLogicException("����������� ������ ���������: ��� ������� �� �������� 4 �� ������� ���������");
			PhoneActivity phoneActivity = new PhoneActivity(segmentedClient, possiblePhones);
			PhoneActivity.Result result = phoneActivity.calculate();
			ermbPhones.active = result.getMain();
			//���� �������� �� ����� ����������� ��������, ������� �����
			if (ermbPhones.active == null)
			{
				ermbPhones.active = possiblePhones.iterator().next();
			}

			ermbPhones.phones.addAll(possiblePhones);
		}
		//����� ��� �������� ���� � �������
		else if (CollectionUtils.isNotEmpty(possiblePhones))
		{
			ermbPhones.active = possiblePhones.iterator().next();
			ermbPhones.phones.addAll(possiblePhones);
		}

		//���� �� ������� ��������� ��� ���� ������� ����� �������� ��������� �� ������,
		//�.�. �������� ����� �������� ��� �������� � ���������� �� ������ ����� ��������
		if (ermbPhones.active == null)
		{
			//����� �������� �� ������� ����� ����������:
			//  - ��� �������� ����������� ���������� ���������� (3 �������)
			//  - ��� ��������� ���������� ���. ���� (5 �������)
			if (!(segmentedClient.getSegment_3() || segmentedClient.getSegment_5()))
				throw new MigrationLogicException("����������� ������ ���������: �� ������� ��������� ��� ������� ����");

			log.add("WARNING ������� ���� �� ���������. ������ � ��� ����� ����������� �������. clientId=" + segmentedClient.getId());
		}

		return ermbPhones;
	}

	//�������� ����������� ��������
	private void checkData(Client segmentedClient, List<Conflict> conflicts, ErmbProfileImpl profile, Set<MbkCard> cards, MbvRegistrationInfo mbvInfo)
			throws IKFLException
	{
		if (segmentedClient.getSegments().isEmpty())
			throw new MigrationLogicException("�� ����������� ����������� ������ �� ����� �� � ���� �������.");

		if (segmentedClient.getSegment_5_1())
			throw new MigrationLogicException("�������������� �������� �������� �������� 5_1 �� ��������������.");

		if (hasUnresolvedConflict(conflicts))
			throw new UnresolvedConflictException("� ������� ������� ������������� ���������. id=" + segmentedClient.getId());

		if (profile != null && profile.isServiceStatus())
			throw new MigrationLogicException("����-������� ������� ��� ����������. profileId=" + profile.getId());
		//TODO ������� � ������ �����?

		if (segmentedClient.getSegment_4())
			checkAndThrow4SegmentActivity(segmentedClient);

		String[] cardNumbers = new String[cards.size()];
		int i = 0;
		for (MbkCard card : cards)
		{
			cardNumbers[i++] = card.getNumber();
		}

		//��������� ����������� ��� (�� ���������+�������������� ������, mb_WWW_GetRegistrations2)
		List<MobileBankRegistration> mbkRegistrations = new ArrayList<MobileBankRegistration>();
		if (ConfigFactory.getConfig(MobileBankConfig.class).isUsePackRegistrations())
		{
			List<MobileBankRegistration> registrations = getMbkService().getRegistrationsPack(null, cardNumbers);
			mbkRegistrations.addAll(registrations);
		}
		else
		{
			GroupResult<String, List<MobileBankRegistration>> groupResult = getMbkService().getRegistrations(null, cardNumbers);
			for (String cardNumber : cardNumbers)
			{
				List<MobileBankRegistration> result = GroupResultHelper.getResult(groupResult, cardNumber);
				if (result != null)
				{
					mbkRegistrations.addAll(groupResult.getResult(cardNumber));
				}
			}
		}
		checkAndThrowNewPhones(segmentedClient.getPhones(), mbkRegistrations, mbvInfo);
	}

	/**
	 * �������� ����������� �������� 4 �������� (����� ������ ��������)
	 * 4 ������� ������ ����������� � ��������� ��������
	 *  - ��� �������� ������� ��������� (�� ���� �������� ���)
	 *  - ��� ���� ���� ��������� ������� ��������������� �� ��������� ��������� 5 ����������� ���� �� ����������� ������� ������(��) ��������(��), ��� � �� �� ����, ��� � ������ �������(�)
	 * @param segmentedClient ������ �� 4 ��������
	 * @throws MigrationLogicException ������ �����������
	 */
	private void checkAndThrow4SegmentActivity(Client segmentedClient) throws MigrationLogicException
	{
		Set<Phone> phones = segmentedClient.getPhones();
		if (phones.size() < 2)
			throw new MigrationLogicException("����������� ������ ���������: � ������� �� 4 �������� ����� 2 ���������.");

		//���� �������� ������� - ��
		for (Phone phone : phones)
		{
			if (phone.isLastSmsActivity())
				return;
		}

		Phone lastPhone = Collections.max(phones, new Comparator<Phone>()
		{
			public int compare(Phone o1, Phone o2)
			{
				return DateHelper.nullSafeCompare(o1.getRegistrationDate(), o2.getRegistrationDate());
			}
		});

		//�� �� ������ �������� �� �������� ���� ����������� - ��
		if (lastPhone.getRegistrationDate() == null)
			return;

		//���� ����������� ��������� �� �������� � ���������� 5 ���� �� ����������
		Calendar lastRegistrationDate = lastPhone.getRegistrationDate();
		for (Phone phone : phones)
		{
			boolean error = !phone.equals(lastPhone)
					&& phone.getRegistrationDate() != null
					&& DateHelper.daysDiff(phone.getRegistrationDate(), lastRegistrationDate) <= 5;
			if (error)
				throw new MigrationLogicException("���������� �������� ������� 4 �������� �� ���������� ���������: "
						+ "1. ����������� �������� ��������."
						+ "2. ��������� ������� ��������������� �� ��������� ��������� 5 ����������� ���� �� ����������� ������� ������(��) ��������(��)");
		}
	}

	/**
	 * ��������� ��������� �� ������ ������� ��������� �� �� ��������� � ����������
	 * ���������� ����������, ���� �� ���������
	 * @param phones �������� �� �� ���������
	 * @param mbkRegistrations ������ ��� �� ������
	 * @param mbvInfo ���������� �� �������� ����������� ���
	 */
	private void checkAndThrowNewPhones(Set<Phone> phones, Collection<MobileBankRegistration> mbkRegistrations, MbvRegistrationInfo mbvInfo)
			throws MigrationLogicException
	{
		Set<String> mbkPhones = ErmbHelper.getPhonesFromMbkRegistrations(mbkRegistrations);
		Set<String> mbvPhones = mbvInfo.getPhones();

		if (!isSamePhones(phones, mbkPhones, mbvPhones))
		{
			StringBuilder messageBuilder = new StringBuilder("� ������� �������� csv � ������� ��������� ����� ��������� � ��� ��� ���.");
			messageBuilder.append(" �� ���������(���):");
			messageBuilder.append(getMaskedPhones(getMbkPhoneNumbers(phones)));
			messageBuilder.append(" ���:");
			messageBuilder.append(getMaskedPhones(mbkPhones));
			messageBuilder.append(" �� ���������(���):");
			messageBuilder.append(getMaskedPhones(getMbvPhoneNumbers(phones)));
			messageBuilder.append(" ���:");
			messageBuilder.append(getMaskedPhones(mbvPhones));

			throw new MigrationLogicException(messageBuilder.toString());
		}
	}

	//��������� �� ������ ���������, ������� ������ ����������� ��-�� ���. ����
	private void processFifthSegment(Client client) throws MigrationLogicException
	{
		Iterator<Phone> it = client.getPhones().iterator();
		while (it.hasNext())
		{
			Phone phone = it.next();
			//��������� ������ ��������, � ������� ��������� ���. �����
			if (phone.isHasAdditional())
			{
				//������ ��� ������� � 5_1
				if (phone.getVipOrMbc())
				{
					throw new MigrationLogicException("������ (���� ���� �� ���������� �������������� ���� � ��� �������) �������� ���/���. " +
							"�������������� �������� �� ��������������.");
				}

				//����� ���� ��������� � ������� ��������� �������, ������ � ������ ��������� � ��� �������� �������������� ���� �������, ������������ �� ���� �����
				//��� ������� ���
				if (phone.isAdditionalCardOwner())
				{
					if (!phone.isHasMain() || isMbvPhone(phone))
						it.remove();
				}
				//���������� ���. ���� �� ����������
				else
				{
					it.remove();
				}
			}
		}
	}

	private void checkUdboState(Client client, com.rssl.phizic.gate.clients.Client cedboClient) throws MigrationLogicException
	{
		boolean oldUdbo = client.getUDBO();
		boolean newUdbo = cedboClient != null && cedboClient.isUDBO();

		if (oldUdbo && !newUdbo)
		{
			throw new MigrationLogicException("������ ��������� ��� ���� ������, �� ����������� �� ������� �� CEDBO. ���������� ���������������.");
		}
		if (!oldUdbo && newUdbo)
		{
			throw new MigrationLogicException("������ ��������� ��� ��������� ������, �� ������� ����������� �� CEDBO. ���������� ���������������.");
		}
	}

	private Long getNextBlock()
	{
		List<Long> blockSequence = ConfigFactory.getConfig(ErmbListMigrationConfig.class).getMigrationBlockSequence();
		if (!blockSequence.contains(currentBlock))
			throw new ConfigurationException("������� ���� �" + currentBlock + " �� ������ � ������� ��������� ��������");

		int indexNext = blockSequence.lastIndexOf(currentBlock) + 1;
		if (indexNext >= blockSequence.size())
			throw new ConfigurationException("�� ������ ��������� ���� ��� �������� �����");
		else
			return blockSequence.get(indexNext);
	}

	private void hideAccountsInSmsChannel(ActivePerson person) throws BusinessException, BusinessLogicException
	{
		List<AccountLink> accountLinks = externalResourceService.getLinks(person.getLogin(), AccountLink.class);
		for (AccountLink accountLink : accountLinks)
		{
			if (accountLink.getShowInSms())
			{
				accountLink.setShowInSms(false);
				externalResourceService.updateLink(accountLink);
			}
		}
	}
}
