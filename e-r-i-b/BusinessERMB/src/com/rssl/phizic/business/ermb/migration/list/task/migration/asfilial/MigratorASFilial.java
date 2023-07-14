package com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.bankroll.BankrollProductRulesService;
import com.rssl.phizic.business.ermb.migration.MigrationHelper;
import com.rssl.phizic.business.ermb.migration.list.task.migration.MigratorBase;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.CardLinkHelper;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mbv.MbvRegistrationInfo;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Миграция МБК,МБВ -> ЕРМБ в АС Филиал
 * @author Puzikov
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */

public class MigratorASFilial extends MigratorBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final BankrollProductRulesService bankrollProductRulesService = new BankrollProductRulesService();

	/**
	 * Запуск миграции на QUERY_PROFILE
	 * @param person клиент
	 * @return информация по результатам миграции
	 */
	public AsFilialMigrationResult migrateOnQueryProfile(ActivePerson person) throws BusinessException, BusinessLogicException
	{
		List<CardLink> links = externalResourceService.getLinks(person.getLogin(), CardLink.class, null);

		MbkState mbkState = checkConflict(links);
		if (CollectionUtils.isNotEmpty(mbkState.conflictPhones))
		{
			log.info("Миграция на QUERY_PROFILE. Телефоны конфликтны (есть в МБК) " + StringUtils.join(mbkState.conflictPhones.toArray()));
			Set<String> conflictPhones = new HashSet<String>(mbkState.conflictPhones);
			conflictPhones.addAll(filterErmbPhones(mbkState.phones));
			return new AsFilialMigrationResult(updateProfileOnMigration(person, true), mbkState.infoCardConflict, conflictPhones);
		}

		MbvRegistrationInfo mbvInfo = MigrationHelper.getMbvInfo(person.asClient());
		//TODO искать телефоны из МБВ в МБК и наоборот. Проверять конфликты.
		Set<String> mbvPhones = mbvInfo.getPhones();

		Set<String> allPhones = new HashSet<String>(mbkState.phones);
		allPhones.addAll(mbvPhones);
		//нечего мигрировать
		if (CollectionUtils.isEmpty(allPhones))
		{
			return new AsFilialMigrationResult(updateProfileOnMigration(person, false), mbkState.infoCardConflict, Collections.<String>emptySet());
		}

		//проверить, есть ли мигрируемые телефоны в ЕРМБ
		Set<String> ermbPhones = filterErmbPhones(allPhones);
		if (CollectionUtils.isNotEmpty(ermbPhones))
		{
			return new AsFilialMigrationResult(updateProfileOnMigration(person, true), mbkState.infoCardConflict, ermbPhones);
		}

		//---------------
		//BEGIN MIGRATION
		//---------------
		UUID mbvMigrationId = null;
		Long mbkMigrationId = null;
		ErmbProfileImpl newProfile = null;
		ErmbProfileImpl ermbProfile = (ErmbProfileImpl) person.getErmbProfile();
		ErmbProfileImpl oldProfile = ermbProfile != null ? ErmbHelper.copyProfile(ermbProfile) : null;
		try
		{
			if (ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability()
					&& CollectionUtils.isNotEmpty(mbvPhones))
			{
				mbvMigrationId = getMbvService().beginMigration(mbvInfo.getIdentities().get(0));
			}

			BeginMigrationResult mbkMigrationResult = getMbkService().beginMigrationErmb(MigrationHelper.getCardsToMigrate(links), mbkState.phones, null, MigrationType.PILOT);
			mbkMigrationId = mbkMigrationResult.getMigrationId();
			if (mbkMigrationId == null)
				throw new GateException("Не удалось проинициализировать миграционную транзакцию МБК");

			//активный телефон - любой
			newProfile = createOrUpdateErmbProfile(ermbProfile, person, allPhones, allPhones.iterator().next());
			updateProfileWithMbkData(newProfile, mbkMigrationResult.getMbkConnectionInfo());
		}
		catch (Exception e)
		{
			//------------------
			//ROLLBACK MIGRATION
			//------------------
			if (mbkMigrationId != null)
			{
				try
				{
					getMbkService().rollbackMigration(mbkMigrationId);
					newProfile.setMigrationConflict(true);
				}
				catch (Exception e1)
				{
					log.error("Не удалось сделать ROLLBACK миграции МБК id=" + mbkMigrationId, e1);
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
					log.error("Не удалось сделать ROLLBACK миграции МБВ id=" + mbvMigrationId, e1);
				}
			}
			throw new BusinessException("Не удалось сделать BEGIN_MIGRATION", e);
		}

		//----------------
		//COMMIT MIGRATION
		//----------------
		try
		{
			if (mbkMigrationId != null)
			{
				List<CommitMigrationResult> commitResult = getMbkService().commitMigrationErmb(mbkMigrationId);
				setTariffInfo(newProfile, commitResult);
				newProfile.setMigrationConflict(false);
			}

			if (ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability()
					&& mbvMigrationId != null)
			{
				getMbvService().commitMigration(mbvMigrationId);
			}
		}
		catch (Exception e)
		{
			throw new BusinessException("Не удалось сделать COMMIT_MIGRATION", e);
		}

		ErmbHelper.saveOrUpdateProfile(oldProfile, newProfile, true);
		sendCsaUpdatePhones(newProfile, newProfile.getMainPhoneNumber(), allPhones);
		sendErmbConnected(newProfile);
		ermbPhoneService.saveOrUpdateERMBPhones(allPhones, Collections.<String>emptyList());

		return new AsFilialMigrationResult(newProfile, mbkState.infoCardConflict, Collections.<String>emptySet());
	}

	private Set<String> filterErmbPhones(Set<String> allPhones) throws BusinessException
	{
		Set<String> ermbPhones = new HashSet<String>();
		for (String phone : allPhones)
		{
			ErmbProfileImpl ermbProfile = ErmbHelper.getErmbProfileByPhone(phone);

			if (ermbProfile != null)
			{
				log.info("Миграция на QUERY_PROFILE. Телефон конфликтен (уже в ЕРМБ) " + phone);
				ermbPhones.add(phone);
			}
		}
		return ermbPhones;
	}

	private ErmbProfileImpl createEmpryProfileOnQueryProfile(ActivePerson person, boolean migrationConflict) throws BusinessException, BusinessLogicException
	{
		ErmbProfileImpl ermbProfil = ErmbHelper.createErmbProfile(person, false, null, person.getDepartmentId());
		bankrollProductRulesService.checkAndApplyRulesToProfile(ermbProfil);
		ermbProfil.setMigrationConflict(migrationConflict);
		ErmbHelper.saveCreatedProfile(ermbProfil, false);
		//В случае создания нового профиля, телефоны в профиль не добавлялись.
		//Записываем телефоны в профиль только для целей использования в методе buildQueryProfileResponse, без дальнейшего сохранения.
		Set<String> clientPhonesStr = MigrationHelper.getAllPhoneNumbersForResponse(person);
		ermbProfil.setPhones(ErmbHelper.getAllErmbClientPhones(clientPhonesStr, ermbProfil));
		return ermbProfil;
	}

	private ErmbProfileImpl updateProfileOnMigration(ActivePerson person,  boolean migrationConflict) throws BusinessLogicException, BusinessException
	{
		ErmbProfileImpl ermbProfile = (ErmbProfileImpl) person.getErmbProfile();

		if (ermbProfile == null)
			ermbProfile = createEmpryProfileOnQueryProfile(person, migrationConflict);
		else
		{
			ermbProfile.setMigrationConflict(migrationConflict);
			profileService.addOrUpdate(ermbProfile);
		}
		return ermbProfile;
	}

	/**
	 * Проверка конфликта в МБК по телефонам и доп. картам
	 */
	private MbkState checkConflict(Collection<CardLink> gflCards) throws BusinessException
	{
		MbkState result = new MbkState();

		Set<String> phones          = new HashSet<String>();    //телефоны в МБК, в связках с переданными картами в качестве платежных
		Set<String> infoCards       = new HashSet<String>();    //инфокарты в МБК, в связках с переданными картами в качестве платежных
		Set<String> allMbkCards     = new HashSet<String>();    //карты в связках по телефону, не пришедшие по gfl

		try
		{
			//поиск телефонов, на которых есть связка в МБК с платежной, пришедшей из GFL
			//чужие связки, для которых карта клиента указана только как инфокарта, здесь не нужны, т.к. в миграции они не участвуют
			String[] param = CardLinkHelper.listCardNumbers(gflCards).toArray(new String[gflCards.size()]);
			if (ConfigFactory.getConfig(MobileBankConfig.class).isUsePackRegistrations())
			{
				List<MobileBankRegistration> registrations = getMbkService().getRegistrationsPack(false, param);
				addPhones(phones, infoCards, registrations);
			}
			else
			{
				GroupResult<String, List<MobileBankRegistration>> registrationsByCard = getMbkService().getRegistrations(false, param);
				GroupResultHelper.checkAndThrowAnyException(registrationsByCard);
				for (List<MobileBankRegistration> registrations : GroupResultHelper.getResults(registrationsByCard))
				{
					addPhones(phones, infoCards, registrations);
				}
			}
			//нечего мигрировать из МБК
			if (CollectionUtils.isEmpty(phones))
			{
				return result;
			}

			//получить все карты из МБК (и платежные, и информационные) по телефонам клиента
			for (String phone : phones)
			{
				Set<String> cardsByPhone = getMbkService().getCardsByPhone(phone);
				allMbkCards.addAll(cardsByPhone);
			}

			Set<String> conflictPhoneNumbers = checkPhoneConflict(allMbkCards, gflCards, phones);
			boolean infoCardConflict = checkInfoCardConflict(infoCards, gflCards);

			result.phones.addAll(phones);
			result.conflictPhones.addAll(conflictPhoneNumbers);
			result.infoCardConflict = infoCardConflict;
			return result;
		}
		catch (IKFLException e)
		{
			throw new BusinessException("Не удалось получить информацию из МБК", e);
		}
	}

	private void addPhones(Set<String> phones, Set<String> infoCards, List<MobileBankRegistration> registrations)
	{
		for (MobileBankRegistration registration : registrations)
		{
			phones.add(PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(registration.getMainCardInfo().getMobilePhoneNumber()));
			for (MobileBankCardInfo infoCard : registration.getLinkedCards())
			{
				infoCards.add(infoCard.getCardNumber());
			}
		}
	}

	/**
	 * Проверить конфликт по телефонам в МБК.
	 * Конфликт = по телефону клиента есть несколько платежных карта на разных клиентов
	 * @param allMbkCards все карты по телефонам клиента (телефоны найдены по связкам с платежной картой из GFL)
	 * @param gflCards карты из GFL
	 * @param phones телефоны из МБК мигрируемого клиента
	 * @return Список номеров телефонов по которым есть конфликт.
	 */
	private Set<String> checkPhoneConflict(Set<String> allMbkCards, Collection<CardLink> gflCards, Set<String> phones) throws IKFLException
	{
		Set<String> conflictPhoneNumbers = new HashSet<String>();
		List<String> gflCardNumbers = CardLinkHelper.listCardNumbers(gflCards);
		//найти карты, не вошедшие в список карт клиента
		Set<String> unknownCards = new HashSet<String>(allMbkCards);
		unknownCards.removeAll(gflCardNumbers);

		if (CollectionUtils.isNotEmpty(unknownCards))
		{
			String[] param = unknownCards.toArray(new String[unknownCards.size()]);
			if (ConfigFactory.getConfig(MobileBankConfig.class).isUsePackRegistrations())
			{
				List<MobileBankRegistration> registrations = getMbkService().getRegistrationsPack(false, param);
				addComflictedPhones(phones, conflictPhoneNumbers, gflCardNumbers, registrations);
			}
			else
			{
				GroupResult<String, List<MobileBankRegistration>> registrationsByUnknownCards = getMbkService().getRegistrations(false, param);
				GroupResultHelper.checkAndThrowAnyException(registrationsByUnknownCards);
				for (List<MobileBankRegistration> registrations : GroupResultHelper.getResults(registrationsByUnknownCards))
				{
					addComflictedPhones(phones, conflictPhoneNumbers, gflCardNumbers, registrations);
				}
			}
		}
		return conflictPhoneNumbers;
	}

	private void addComflictedPhones(Set<String> phones, Set<String> conflictPhoneNumbers, List<String> gflCardNumbers, List<MobileBankRegistration> registrations)
	{
		for (MobileBankRegistration registration : registrations)
		{
			MobileBankCardInfo mobileBankCardInfo = registration.getMainCardInfo();
			String mainCardNumber = mobileBankCardInfo.getCardNumber();
			String phoneNumber = PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(mobileBankCardInfo.getMobilePhoneNumber());
			//найдена платежная карта связки клиента, не пришедшая по GFL => ее владелец - другой => конфликт
			//причем телефон связки содержится в списке телефонов клиента
			if (!gflCardNumbers.contains(mainCardNumber) && phones.contains(phoneNumber))
			{
				conflictPhoneNumbers.add(phoneNumber);
			}
		}
	}

	/**
	 * Проверить конфликт по доп. картам в МБК.
	 * Конфликт = в связках МБК клиента есть карты других (или доп. карта, выпущенная клиентом другому)
	 * @param infoCards инфокарты, найденные в мбк
	 * @param gflCards карты из GFL
	 * @return есть ли конфликт
	 */
	private boolean checkInfoCardConflict(Set<String> infoCards, Collection<CardLink> gflCards)
	{
		List<String> gflCardNumbers = CardLinkHelper.listCardNumbers(gflCards);
		//в списке инфокарт есть карта, не пришедшая по GFL => есть чужая карта в связке => конфликт
		if (!gflCardNumbers.containsAll(infoCards))
		{
			return true;
		}
		for (String infoCard : infoCards)
			for (CardLink cardLink : gflCards)
				if (cardLink.getNumber().equals(infoCard))
				{
					AdditionalCardType cardType = cardLink.getAdditionalCardType();
					//в списке инфокарт есть карта, пришедшая по GFL со статусом "доп. карта на другого" => конфликт
					if (cardType != null && (cardType == AdditionalCardType.OTHERTOCLIENT || cardType == AdditionalCardType.CLIENTTOOTHER))
						return true;
				}

		return false;
	}
}
