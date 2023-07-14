package com.rssl.phizic.business.ermb.migration.list.task.migration;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.clients.ClientResourcesService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.ermb.*;
import com.rssl.phizic.business.ermb.card.ErmbPaymentCardLinkFilter;
import com.rssl.phizic.business.ermb.migration.CodWayErmbConnectionSender;
import com.rssl.phizic.business.ermb.migration.MigrationHelper;
import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationClient;
import com.rssl.phizic.business.ermb.migration.mbk.ERMBPhoneService;
import com.rssl.phizic.business.ermb.products.ErmbNotificationSettingsController;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.business.persons.clients.PersonImportService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.client.DefaultSchemeType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.ermb.MbkTariff;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * ������� ����� ��������
 * @author Puzikov
 * @ created 11.07.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class MigratorBase
{
	protected static final ExternalResourceService externalResourceService = new ExternalResourceService();
	protected static final ERMBPhoneService ermbPhoneService = new ERMBPhoneService();
	protected static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	protected static final ClientResourcesService clientResourcesService = new ClientResourcesService();

	private static final ErmbTariffService tariffService = new ErmbTariffService();
	private static final PersonService personService = new PersonService();
	private static final DepartmentService departmentService = new DepartmentService();
	@SuppressWarnings("deprecation")
	private final MobileBankService mbkService = GateSingleton.getFactory().service(MobileBankService.class);
	private final DepoMobileBankService mbvService = GateSingleton.getFactory().service(DepoMobileBankService.class);
	@SuppressWarnings("deprecation")
	protected MobileBankService getMbkService()
	{
		return mbkService;
	}

	protected DepoMobileBankService getMbvService()
	{
		return mbvService;
	}

	//����� ���������� �������� �� ��� ������������ � �������������� �� �������� ����
	protected void setTariffInfo(ErmbProfileImpl profile, Collection<CommitMigrationResult> commitResult) throws BusinessException
	{
		CommitMigrationResult bestCondition = MigrationHelper.findBestCondition(commitResult);
		ClientTariffInfo tariffInfo = bestCondition.getTariffInfo();
		int tariffCode = tariffInfo.getLinkTariff();

		String tariffCodeString = MbkTariff.fromMbkCode(tariffCode).getStringCode();
		ErmbTariff tariff = tariffService.getTariffByCode(tariffCodeString);
		if (tariff == null)
			throw new IllegalArgumentException("���������� ����� ����� �� ���� " + tariffCodeString);
		profile.setTarif(tariff);
		profile.setConnectionDate(DateHelper.startOfDay(tariffInfo.getFirstRegistrationDate()));

		boolean paymentBlocked = tariffInfo.getLinkPaymentBlockID() == 1 && !ErmbHelper.isFreeTariff(tariff);
		profile.setPaymentBlocked(paymentBlocked);
		profile.setMbkPaymentBlocked(paymentBlocked);

		int gracePeriodLength = tariff.getGracePeriod();
		profile.setGracePeriodEnd(DateHelper.addMonths(tariffInfo.getFirstRegistrationDate(), gracePeriodLength));
		setErmbPaymentCard(profile, bestCondition.getPaymentCard());

		ErmbChargeDateUpdater updater = new ErmbChargeDateUpdater();
		updater.setChargeDateFromMbk(profile, tariffInfo);

		// �������� �������� ���-����������� �� ���������
		ErmbNotificationSettingsController notificationController = new ErmbNotificationSettingsController(profile);
		notificationController.disableUnsupported();
	}

	protected void setErmbPaymentCard(ErmbProfileImpl profile, String paymentCard)
	{
		if (StringUtils.isEmpty(paymentCard))
			return;

		ErmbPaymentCardLinkFilter filter = new ErmbPaymentCardLinkFilter();
		for (CardLink cardLink : profile.getCardLinks())
		{
			if (paymentCard.equals(cardLink.getNumber()) && filter.evaluate(cardLink))
			{
				profile.setForeginProduct(cardLink);
				break;
			}
		}
	}

	protected ActivePerson getEribPerson(MigrationClient client) throws BusinessLogicException, BusinessException
	{
		return personService.getByFIOAndDocUnique(
				client.getLastName(),
				client.getFirstName(),
				StringHelper.getNullIfEmpty(client.getMiddleName()),
				null,
				client.getDocument(),
				client.getBirthday(),
				StringHelper.getNullIfEmpty(client.getTb())
		);
	}

	protected ActivePerson createPerson(MigrationClient client, com.rssl.phizic.gate.clients.Client gateClient) throws BusinessException, BusinessLogicException
	{
		com.rssl.phizic.gate.clients.Client newClient = gateClient;

		Department department = departmentService.getDepartment(client.getTb(), client.getOsb(), client.getVsp());
		if (department == null)
		{
			throw new NotFoundException("�� ������� ������������� ��� �������� ������� ������� � ����: "
					+ client.getTb() + ", "
					+ client.getOsb() + ", "
					+ client.getVsp());
		}

		//�� ������ �� CEDBO - ���������, ��������� �� ������ ���������
		if (newClient == null)
		{
			ClientImpl cardClient = new ClientImpl(client.getFirstName(), client.getMiddleName(), client.getLastName());
			cardClient.setBirthDay(client.getBirthday());

			ClientDocumentImpl document = new ClientDocumentImpl();
			document.setDocumentType(ClientDocumentType.PASSPORT_WAY);
			document.setDocSeries(client.getDocument());
			document.setDocIdentify(true);
			cardClient.setOffice(department);
			cardClient.setId(PersonHelper.generateClientId(department));

			List<ClientDocument> documents = new ArrayList<ClientDocument>(1);
			documents.add(document);
			cardClient.setDocuments(documents);

			newClient = cardClient;
		}
		CreationType creationType = newClient.isUDBO() ? CreationType.UDBO : CreationType.CARD;

		PersonImportService personImportService = new PersonImportService();
		return personImportService.addOrUpdatePerson(null, newClient, creationType, DefaultSchemeType.getDefaultSchemeType(creationType), department);
	}

	protected ErmbProfileImpl createOrUpdateErmbProfile(ErmbProfileImpl oldProfile, ActivePerson person, Set<String> allPhones, String activePhone) throws BusinessException
	{
		if (!allPhones.contains(activePhone))
			throw new IllegalArgumentException("�������� ������� ������� �� ���������� � ����� ������ ���������");

		ErmbProfileImpl ermbProfile = oldProfile;
		if (ermbProfile == null)
		{
			ermbProfile = new ErmbProfileImpl();
			ermbProfile.setConnectionDate(DateHelper.getCurrentDate());
			ermbProfile.setConfirmProfileVersion(0L);
			ermbProfile.setPhones(new HashSet<ErmbClientPhone>());
		}

		//����� ��� ������������ �������
		if (!ermbProfile.isServiceStatus())
		{
			ermbProfile.setPerson(person);
			ermbProfile.setServiceStatus(true);
			MigrationHelper.updateDefaultNotificationTime(ermbProfile);
		}

		Set<String> oldPhoneNumbers = ermbProfile.getPhoneNumbers();
		for (String phone : allPhones)
		{
			if (!oldPhoneNumbers.contains(phone))
			{
				ErmbClientPhone ermbPhone = new ErmbClientPhone(phone, ermbProfile);
				ermbProfile.getPhones().add(ermbPhone);
			}
		}

		for (ErmbClientPhone phone : ermbProfile.getPhones())
		{
			boolean main = phone.getNumber().equals(activePhone);
			phone.setMain(main);
		}

		if (ermbProfile.isServiceStatus() && ermbProfile.getMainPhoneNumber() == null)
			throw new BusinessException("������ ���������� ��������� ���� ��� ��������� �������� �����������");

		return ermbProfile;
	}

	protected void updateProfileWithMbkData(ErmbProfileImpl profile, Collection<MbkConnectionInfo> mbkBeginResults) throws BusinessException, BusinessLogicException
	{
		updateProfileWithMbkData(profile, mbkBeginResults, Collections.<Segment>emptySet());
	}

	/**
	 * �������� ������ ������� �� ���������� �� ���
	 *  - �������
	 *  - ���� �������������
	 *  - ������� ������� ��������
	 *  - ������� �������� �������
	 *  - ������� ��������� ����� ���������
	 * @param profile ����������� ����-�������
	 * @param mbkBeginResults ������ �� ��� �� BEGIN MIGRATION
	 * @param segments ������ ������������ ��������� (����� ��� �� ��������� ��������)
	 */
	protected void updateProfileWithMbkData(ErmbProfileImpl profile, Collection<MbkConnectionInfo> mbkBeginResults, Set<Segment> segments) throws BusinessException, BusinessLogicException
	{
		List<MobileBankTemplate> mbkTemplates = new ArrayList<MobileBankTemplate>();
		List<String> mbkPhoneOffers = new ArrayList<String>();
		//���������� ���������� �������� � �������:
		for (MbkConnectionInfo resEntity : mbkBeginResults)
		{
			//��������
			List<MobileBankTemplate> currentMbaTemplates = resEntity.getTemplates();
			if (currentMbaTemplates != null)
			{
				mbkTemplates.addAll(currentMbaTemplates);
			}
			//�����
			List<String> currentMbkPhoneOffers = resEntity.getPhoneOffers();
			if (currentMbkPhoneOffers != null)
			{
				mbkPhoneOffers.addAll(currentMbkPhoneOffers);
			}
		}

		if (CollectionUtils.isNotEmpty(mbkTemplates))
		{
			MigrationHelper.saveMbkTemplates(profile, mbkTemplates);
		}
		if (CollectionUtils.isNotEmpty(mbkPhoneOffers))
		{
			MigrationHelper.savePhoneOffers(profile, mbkPhoneOffers, null);
		}

		boolean fastServicesAvailable = getFastServicesAvailable(segments, mbkBeginResults);
		profile.setFastServiceAvailable(fastServicesAvailable);

		// ������� ������� �������� ������� ��������������� � OFF
		profile.setSuppressAdv(false);

		//������� ��������� ����� ��������� ��������������� � ON
		profile.setNewProductShowInSms(true);
		profile.setNewProductNotification(true);
	}

	private boolean getFastServicesAvailable(Set<Segment> segments, Collection<MbkConnectionInfo> mbkBeginResults)
	{
		//��� 4-�� �������� ��������� ��������: ������� ������� �������� �.�. ������ ��������
		if (!CollectionUtils.isEmpty(segments) && segments.contains(Segment.SEGMENT_4))
		{
			return false;
		}

		// �� ��� ����������� �������� ������� �������� (��). ��� ���������� � ��� �������� ��
		// ��� ������� ������ ��������� ��, �� ��������������� � ���� � �������� OFF.

		//����������� - OFF
		if (CollectionUtils.isEmpty(mbkBeginResults))
		{
			return false;
		}
		//���� �� � ����� ��������� - OFF
		for (MbkConnectionInfo phone : mbkBeginResults)
		{
			if (phone.getPhoneQuickServices() == 0)
			{
				return false;
			}
		}

		return true;
	}

	protected void sendErmbConnected(ErmbProfileImpl profile)
	{
		CodWayErmbConnectionSender codWaySender = new CodWayErmbConnectionSender();
		codWaySender.sendErmbConnected(profile);
	}

	protected void sendCsaUpdatePhones(ErmbProfileImpl profile, String activePhone, Collection<String> allPhones) throws BusinessException, BusinessLogicException
	{
		if (StringUtils.isEmpty(activePhone) || CollectionUtils.isEmpty(allPhones))
			throw new IllegalArgumentException("���������� ��������� ������ �� ���������� ��������� � ���: ������������ ����� ��������� �� ����������� ��������");

		try
		{
			CSABackRequestHelper.sendUpdatePhoneRegistrationsRq(
					activePhone,
					PersonHelper.buildUserInfo(profile.getPerson()),
					new ArrayList<String>(allPhones),
					Collections.<String>emptyList()
			);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}
}