package com.rssl.phizic.business.ermb.migration.mbk.registrator;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.business.ermb.ErmbTariffService;
import com.rssl.phizic.business.ermb.migration.MigrationHelper;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationClient;
import com.rssl.phizic.business.ermb.migration.list.task.migration.MigratorBase;
import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.business.ermb.profile.ErmbProfileListener;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbProfileEvent;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.CardLinkHelper;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mbv.MbvRegistrationInfo;
import com.rssl.phizic.gate.mobilebank.BeginMigrationResult;
import com.rssl.phizic.gate.mobilebank.CommitMigrationResult;
import com.rssl.phizic.gate.mobilebank.DepoMobileBankConfig;
import com.rssl.phizic.gate.mobilebank.MigrationType;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * �������� ���->���� �� ����
 * @author Puzikov
 * @ created 11.07.14
 * @ $Author$
 * @ $Revision$
 */

public class MigratorOnTheFly extends MigratorBase
{
	private final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private final ErmbTariffService tariffService = new ErmbTariffService();

	/**
	 * ����������� ������� � ����
	 * @param reg ������ �� ���
	 * @param client ������ �� �������
	 * @param officeCode ����������� �������� ������� (�������� ����)
	 * @return ������� �� ������������
	 */
	public boolean migrate(MBKRegistration reg, Client client, Code officeCode) throws BusinessLogicException, BusinessException, GateException, GateLogicException
	{
		MigrationClient migrationClient = new MigrationClient(client);
		String phoneNumber = PhoneNumberFormat.MOBILE_INTERANTIONAL.format(reg.getPhoneNumber());
		Set<String> newPhoneNumbers = new HashSet<String>();
		newPhoneNumbers.add(phoneNumber);

		//����� � �� �������� ����� ����������
		ActivePerson person = getEribPerson(migrationClient);

		ErmbProfileImpl profile = null;
		ErmbProfileImpl oldProfile = null;
		if (person == null)
		{
			migrationClient.setTb(officeCode.getFields().get("region"));
			migrationClient.setOsb(officeCode.getFields().get("office"));
			migrationClient.setVsp(officeCode.getFields().get("branch"));
			person = createPerson(migrationClient, client);
		}
		else
		{
			profile = ErmbHelper.getErmbProfileByPerson(person);
			if (profile != null)
			{
				oldProfile = ErmbHelper.copyProfile(profile);
			}
		}
		MigrationHelper.initContext(person);
		clientResourcesService.updateResources(person, false, Card.class);
		List<CardLink> cardLinks = externalResourceService.getLinks(person.getLogin(), CardLink.class, null);
		if (checkPhoneConflict(cardLinks, phoneNumber))
		{
			log.error("�������� �� �������� ���. �������� �� ���� ����������.");
			return false;
		}

		MbvRegistrationInfo mbvInfo = MigrationHelper.getMbvInfo(client);
		boolean mbvContainsPhone = CollectionUtils.isNotEmpty(mbvInfo.getPhones()) && mbvInfo.getPhones().contains(phoneNumber);

		//---------------
		//BEGIN MIGRATION
		//---------------
		UUID mbvMigrationId = null;
		Long mbkMigrationId = null;
		try
		{
			if (ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability()
					&& mbvContainsPhone)
			{
				mbvMigrationId = getMbvService().beginMigration(mbvInfo.getIdentities().get(0));
			}

			BeginMigrationResult mbkMigrationResult = getMbkService().beginMigrationErmb(MigrationHelper.getCardsToMigrate(cardLinks), Collections.singleton(phoneNumber), null, MigrationType.ON_THE_FLY);
			mbkMigrationId = mbkMigrationResult.getMigrationId();

			//�������� �� ���+��� ������� ���� � �������
			newPhoneNumbers.addAll(MigrationHelper.extractMbkPhoneNumbers(mbkMigrationResult));
			profile = createOrUpdateErmbProfile(profile, person, newPhoneNumbers, phoneNumber);
			updateProfileWithMbkData(profile, mbkMigrationResult.getMbkConnectionInfo());
		}
		catch (Exception e)
		{
			//------------------
			//ROLLBACK MIGRATION
			//------------------
			log.error("�� ������� ������ �������� �� ����", e);
			if (mbkMigrationId != null)
			{
				try
				{
					getMbkService().rollbackMigration(mbkMigrationId);
				}
				catch (Exception e1)
				{
					log.error("�� ������� ������� ROLLBACK �������� ��� id=" + mbkMigrationId, e1);
				}
			}
			if (ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability()
					&& mbvMigrationId != null)
			{
				try
				{
					getMbvService().rollbackMigration(mbvMigrationId);
				}
				catch (Exception e1)
				{
					log.error("�� ������� ������� ROLLBACK �������� ��� id=" + mbvMigrationId, e1);
				}
			}
			return false;
		}

		//----------------
		//COMMIT MIGRATION
		//----------------
		List<CommitMigrationResult> commitResult = null;
		try
		{
			if (mbkMigrationId != null)
			{
				commitResult = getMbkService().commitMigrationErmb(mbkMigrationId);
			}

			if (ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability()
					&& mbvMigrationId != null)
			{
				getMbvService().commitMigration(mbvMigrationId);
			}
		}
		catch (Exception e)
		{
			log.error("�� ������� ������� COMMIT �������� �� ����", e);
			return false;
		}

		if (mbkMigrationId != null)
		{
			//���������� �� �������������� ������� �� ���
			setTariffInfo(profile, commitResult);
		}
		else
		{
			//���������� �� ������� ������
			setErmbPaymentCard(profile, reg.getPaymentCardNumber());
			String tariffCodeString = reg.getTariff().getStringCode();
			ErmbTariff tariff = tariffService.getTariffByCode(tariffCodeString);
			ErmbHelper.updateErmbTariff(profile, tariff);
		}

		saveOrUpdateProfile(oldProfile, profile);
		sendCsaUpdatePhones(profile, profile.getMainPhoneNumber(), newPhoneNumbers);
		if (oldProfile == null || !oldProfile.isServiceStatus())
			sendErmbConnected(profile);

		return true;
	}

	private void saveOrUpdateProfile(ErmbProfileImpl oldProfile, ErmbProfileImpl profile) throws BusinessException
	{
		ErmbProfileEvent profileEvent = new ErmbProfileEvent(oldProfile);
		profileEvent.setNewProfile(profile);
		ErmbProfileListener profileListener = ErmbUpdateListener.getListener();
		profileListener.beforeProfileUpdate(profileEvent);
		profileService.addOrUpdate(profile);
		profileListener.afterProfileUpdate(profileEvent);

		ermbPhoneService.saveOrUpdateERMBPhones(profile.getPhoneNumbers(), Collections.<String>emptyList());
	}

	//�������� ����, ��� ��� ����� ��� � ������ �� ������������ �������� ���������� � ������ ���� �������
	private boolean checkPhoneConflict(List<CardLink> cardLinks, String phoneNumber) throws GateException, GateLogicException
	{
		List<String> clientCards = CardLinkHelper.listCardNumbers(cardLinks);
		Set<String> mbkCards = getMbkService().getCardsByPhone(phoneNumber);

		return !clientCards.containsAll(mbkCards);
	}
}
