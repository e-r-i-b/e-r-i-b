package com.rssl.phizic.person;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbClientPhone;
import com.rssl.phizic.business.ermb.ErmbOfficialInfoNotificationSender;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.migration.mbk.ERMBPhoneService;
import com.rssl.phizic.business.ermb.profile.ErmbProfileChangesNotificator;
import com.rssl.phizic.business.ermb.profile.comparators.mss.ErmbProfileUpdateComparator;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.oldIdentity.PersonIdentityService;
import com.rssl.phizic.common.type.PersonOldIdentity;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 01.08.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Служба по управлению ЕРМБ-профилем клиента
 */
public class ErmbProfileService
{
	private final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final PersonService personService = new PersonService();

	private final ErmbProfileBusinessService ermbProfileBusinessService = new ErmbProfileBusinessService();

	private final ERMBPhoneService ermbPhoneService = new ERMBPhoneService();

	private final PersonIdentityService personIdentityService = new PersonIdentityService();

	private final ErmbOfficialInfoNotificationSender mssNotificator = new ErmbOfficialInfoNotificationSender();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Найти ЕРМБ-профиль по идентификационным данным клиента
	 * @param clientIdentity - ФИО+ДУЛ+ДР(+ТБ) (должны быть указаны)
	 * @param queryOptions - опции поиска (должны быть указаны)
	 * @return ЕРМБ-профиль или null, если профиль не найден
	 */
	public ClientErmbProfile queryProfile(ClientIdentity clientIdentity, QueryErmbProfileOptions queryOptions)
	{
		if (clientIdentity == null)
			throw new IllegalArgumentException("Не указаны идентификационные данные клиента");
		if (queryOptions == null)
			throw new IllegalArgumentException("Не указаны опции поиска");

		ClientErmbProfile profile = null;
		// (1) Найти профиль по анкете
		if (queryOptions.findByActualIdentity)
			profile = findErmbProfileByActualIdentity(clientIdentity);
		// (2) Найти профиль по истории смены ФИО+ДУЛ+ДР
		if (profile == null && queryOptions.findByOldIdentity)
			profile = findErmbProfileByOldIdentity(clientIdentity);

		return profile;
	}

	private ClientErmbProfile findErmbProfileByActualIdentity(ClientIdentity clientIdentity)
	{
		try
		{
			String fio = clientIdentity.getFullName();
			String doc = clientIdentity.passport;
			Calendar birthday = DateHelper.toCalendar(clientIdentity.birthDay);
			String tb = clientIdentity.tb;
			ErmbProfileImpl profileDatabaseBean = ermbProfileBusinessService.findByFIOAndDocInTB(fio, doc, null, birthday, tb);
			if (profileDatabaseBean == null)
				return null;

			ClientErmbProfile profile = makeProfileBusinessBean(profileDatabaseBean);
			log.debug("ЕРМБ-профиль id=" + profile.id + " найден по анкете клиента " + clientIdentity);
			return profile;
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}

	private ClientErmbProfile findErmbProfileByOldIdentity(ClientIdentity clientIdentity)
	{
		try
		{
			PersonOldIdentity oldIdentity = personIdentityService.getByFIOAndDoc(clientIdentity.getFullName(), clientIdentity.passport, DateHelper.toCalendar(clientIdentity.birthDay));
			if (oldIdentity == null)
				return null;

			ErmbProfileImpl ermbProfileImpl = ermbProfileBusinessService.findByPersonId(oldIdentity.getPerson().getId());
			if (ermbProfileImpl == null)
				throw new RuntimeException("Не найден ЕРМБ-профиль id=" + oldIdentity.getPerson().getId());

			ClientErmbProfile profile = makeProfileBusinessBean(ermbProfileImpl);
			log.debug("ЕРМБ-профиль id=" + profile.id + " найден по истории изменений анкеты " + clientIdentity);
			return profile;
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}

	private ClientErmbProfile findErmbProfileById(long profileId)
	{
		try
		{
			ErmbProfileImpl profileDatabaseBean = ermbProfileBusinessService.findByProfileId(profileId);
			if (profileDatabaseBean == null)
				return null;
			return makeProfileBusinessBean(profileDatabaseBean);
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Сохранить изменения в ЕРМБ-профиле
	 * @param profile - ЕРМБ-профиль (должен быть указан)
	 * @param updateOptions - опции обновления (должны быть указаны)
	 */
	public void updateProfile(ClientErmbProfile profile, UpdateErmbProfileOptions updateOptions)
	{
		if (profile == null)
			throw new IllegalArgumentException("Не указан профиль");

		if (updateOptions == null)
			throw new IllegalArgumentException("Не указаны опции обновления");

		// noinspection UnnecessaryLocalVariable
		ClientErmbProfile newProfile = profile;
		ClientErmbProfile oldProfile = findErmbProfileById(profile.id);
		if (oldProfile == null)
			throw new IllegalArgumentException("Указан неизвестный профиль: " + newProfile.id);

		// 1. Валидируем корректность заполнения полей профиля
		validateProfile(newProfile);

		// 2. Сохраняем профиль
		saveProfile(newProfile);

		// (3) Отправляем изменения телефонов в ЦСА
		if (updateOptions.notifyCSA)
			sendProfileChangesToCSA(newProfile, oldProfile);

		// (4) Отправляем изменения профиля в СОС
		if (updateOptions.notifyMSS)
			sendProfileChangesToMSS(newProfile, oldProfile);

		// (5) Отправляем изменения телефонов в МБК
		if (updateOptions.notifyMBK)
			sendProfileChangesToMBK(newProfile, oldProfile);

		// (6) Отправляем переключение ЕРМБ/МБК в ЦОД
		if (updateOptions.notifyCOD)
			sendProfileChangesToCOD(newProfile, oldProfile);

		// (7) Отправляем переключение ЕРМБ/МБК в WAY
		if (updateOptions.notifyWAY)
			sendProfileChangesToWAY(newProfile, oldProfile);

		// (8) Отправляем СМС клиенту
		if (updateOptions.notifyClient)
			sendProfileChangesToClient(newProfile, oldProfile);
	}

	private void validateProfile(ClientErmbProfile profile)
	{
		// 1. Если главный телефон не входит в общий список телефонов, добавляем его туда
		if (profile.mainPhone != null)
		{
			if (profile.phones == null)
				profile.phones = Collections.singleton(profile.mainPhone);
			else profile.phones.add(profile.mainPhone);
		}

		// 2. Проверяем корректность заполнения полей для услуги в статусе Подключена
		if (profile.serviceStatus)
		{
			if (CollectionUtils.isEmpty(profile.phones))
				throw new IllegalArgumentException("Для активации услуги ЕРМБ необходимо указать список телефонов");
			if (profile.mainPhone == null)
				throw new IllegalArgumentException("Для активации услуги ЕРМБ необходимо указать основной телефон");
		}
	}

	private void saveProfile(ClientErmbProfile profile)
	{
		try
		{
			ermbProfileBusinessService.addOrUpdate(makeProfileDatabaseBean(profile));
		}
		catch (BusinessException e)
		{
			throw new RuntimeException("Не удалось сохранить ЕРМБ-профиль id=" + profile.id, e);
		}
	}

	private void sendProfileChangesToCSA(ClientErmbProfile newProfile, ClientErmbProfile oldProfile)
	{
		try
		{
			boolean mainPhoneChanged = isMainPhoneChanged(newProfile, oldProfile);
			Collection<PhoneNumber> addedPhones = collectAddedPhones(newProfile, oldProfile);
			Collection<PhoneNumber> removedPhones = collectRemovedPhones(newProfile, oldProfile);
			boolean phonesChanged = CollectionUtils.isNotEmpty(addedPhones) || CollectionUtils.isNotEmpty(removedPhones);
			if (mainPhoneChanged || phonesChanged)
			{
				String mainPhoneString = (newProfile.mainPhone != null) ? formatCSAPhone(newProfile.mainPhone) : null;

				UserInfo userInfo = PersonHelper.buildUserInfo(personService.findById(newProfile.id));

				List<String> addedPhoneStrings = new ArrayList<String>();
				for (PhoneNumber phone : addedPhones)
					addedPhoneStrings.add(formatCSAPhone(phone));

				List<String> removedPhoneStrings = new ArrayList<String>();
				for (PhoneNumber phone : removedPhones)
					removedPhoneStrings.add(formatCSAPhone(phone));

				CSABackRequestHelper.sendUpdatePhoneRegRemoveDuplicateRq(mainPhoneString, userInfo, addedPhoneStrings, removedPhoneStrings);
			}
		}
		catch (BusinessLogicException e)
		{
			log.error("Не удалось отправить в ЦСА телефоны ЕРМБ-профиля id=" + newProfile.id, e);
		}
		catch (BusinessException e)
		{
			log.error("Не удалось отправить в ЦСА телефоны ЕРМБ-профиля id=" + newProfile.id, e);
		}
		catch (BackLogicException e)
		{
			log.error("Не удалось отправить в ЦСА телефоны ЕРМБ-профиля id=" + newProfile.id, e);
		}
		catch (BackException e)
		{
			log.error("Не удалось отправить в ЦСА телефоны ЕРМБ-профиля id=" + newProfile.id, e);
		}
	}

	private void sendProfileChangesToMSS(ClientErmbProfile newProfile, ClientErmbProfile oldProfile)
	{
		ErmbProfileUpdateComparator comparator = new ErmbProfileUpdateComparator();
		if (comparator.compare(makeProfileDatabaseBean(newProfile), makeProfileDatabaseBean(oldProfile)) != 0)
		{
			newProfile.version++;
			mssNotificator.sendNotification(Collections.singletonList(makeProfileDatabaseBean(newProfile)));
		}
	}

	private void sendProfileChangesToMBK(ClientErmbProfile newProfile, ClientErmbProfile oldProfile)
	{
		try
		{
			Collection<PhoneNumber> addedPhones = collectAddedPhones(newProfile, oldProfile);
			Collection<PhoneNumber> removedPhones = collectRemovedPhones(newProfile, oldProfile);
			if (CollectionUtils.isNotEmpty(addedPhones) || CollectionUtils.isNotEmpty(removedPhones))
			{
				Collection<String> addedPhoneStrings = new ArrayList<String>();
				if (addedPhones != null)
				{
					for (PhoneNumber phone : addedPhones)
						addedPhoneStrings.add(formatMBKPhone(phone));
				}

				Collection<String> removedPhoneStrings = new ArrayList<String>();
				if (removedPhones != null)
				{
					for (PhoneNumber phone : removedPhones)
						removedPhoneStrings.add(formatMBKPhone(phone));
				}

				ermbPhoneService.saveOrUpdateERMBPhones(addedPhoneStrings, removedPhoneStrings);
			}
		}
		catch (BusinessException e)
		{
			log.error("Не удалось отправить в МБК телефоны ЕРМБ-профиля id=" + newProfile.id, e);
		}
	}

	private void sendProfileChangesToCOD(ClientErmbProfile newProfile, ClientErmbProfile oldProfile)
	{
		//To change body of created methods use File | Settings | File Templates.
	}

	private void sendProfileChangesToWAY(ClientErmbProfile newProfile, ClientErmbProfile oldProfile)
	{
		//To change body of created methods use File | Settings | File Templates.
	}

	private void sendProfileChangesToClient(ClientErmbProfile newProfile, ClientErmbProfile oldProfile)
	{
		ErmbProfileChangesNotificator notificator = new ErmbProfileChangesNotificator();
		notificator.sendOnProfileUpdate(makeProfileDatabaseBean(oldProfile), makeProfileDatabaseBean(newProfile));
	}

	///////////////////////////////////////////////////////////////////////////

	private ClientErmbProfile makeProfileBusinessBean(ErmbProfileImpl ermbProfileImpl)
	{
		PhoneNumber mainPhone = null;
		if (StringHelper.isNotEmpty(ermbProfileImpl.getMainPhoneNumber()))
			mainPhone = PhoneNumber.fromString(ermbProfileImpl.getMainPhoneNumber());

		Collection<PhoneNumber> phones = null;
		if (CollectionUtils.isNotEmpty(ermbProfileImpl.getPhones()))
		{
			phones = new HashSet<PhoneNumber>();
			for (ErmbClientPhone phone : ermbProfileImpl.getPhones())
				phones.add(PhoneNumber.fromString(phone.getNumber()));
		}

		ClientErmbProfile profile = new ClientErmbProfile();
		profile.id = ermbProfileImpl.getId();
		profile.serviceStatus = ermbProfileImpl.isServiceStatus();
		profile.clientBlocked = ermbProfileImpl.isClientBlocked();
		profile.paymentBlocked = ermbProfileImpl.isPaymentBlocked();
		profile.mainPhone = mainPhone;
		profile.phones = phones;
		profile.version = ermbProfileImpl.getProfileVersion();
		return profile;
	}

	private ErmbProfileImpl makeProfileDatabaseBean(ClientErmbProfile profile)
	{
		try
		{
			ErmbProfileImpl ermbProfileImpl = ermbProfileBusinessService.findByProfileId(profile.id);

			ermbProfileImpl.setServiceStatus(profile.serviceStatus);
			ermbProfileImpl.setClientBlocked(profile.clientBlocked);
			ermbProfileImpl.setPaymentBlocked(profile.paymentBlocked);

			Set<ErmbClientPhone> ermbClientPhones = ermbProfileImpl.getPhones();
			if (ermbClientPhones == null)
			{
				ermbClientPhones = new HashSet<ErmbClientPhone>();
				ermbProfileImpl.setPhones(ermbClientPhones);
			}
			if (CollectionUtils.isNotEmpty(profile.phones))
			{
				// Убираем удалённые телефоны
				Iterator<ErmbClientPhone> iterator = ermbClientPhones.iterator();
				while (iterator.hasNext())
				{
					ErmbClientPhone ermbClientPhone = iterator.next();
					boolean del = true;
					for (PhoneNumber phone : profile.phones)
					{
						if (phone.equals(parseErmbProfilePhone(ermbClientPhone.getNumber())))
						{
							del = false;
							break;
						}
					}
					if (del)
						iterator.remove();
				}

				// Добавляем новые телефоны
				for (PhoneNumber phone : profile.phones)
				{
					boolean add = true;
					for (ErmbClientPhone ermbClientPhone : ermbClientPhones)
					{
						if (phone.equals(parseErmbProfilePhone(ermbClientPhone.getNumber())))
						{
							add = false;
							break;
						}
					}
					if (add)
						ermbClientPhones.add(new ErmbClientPhone(formatErmbProfilePhone(phone), ermbProfileImpl));
				}

				// Обновляем главный номер
				if (profile.mainPhone != null)
				{
					for (ErmbClientPhone ermbClientPhone : ermbClientPhones)
					{
						if (profile.mainPhone.equals(parseErmbProfilePhone(ermbClientPhone.getNumber())))
							ermbClientPhone.setMain(true);
						else ermbClientPhone.setMain(false);
					}
				}
				else
				{
					for (ErmbClientPhone ermbClientPhone : ermbClientPhones)
						ermbClientPhone.setMain(false);
				}
			}
			else ermbClientPhones.clear();

			ermbProfileImpl.setProfileVersion(profile.version);

			return ermbProfileImpl;
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}

	private boolean isMainPhoneChanged(ClientErmbProfile newProfile, ClientErmbProfile oldProfile)
	{
		if (newProfile.mainPhone != null)
			return !newProfile.mainPhone.equals(oldProfile.mainPhone);
		else
			return (oldProfile.mainPhone == null);
	}

	private Collection<PhoneNumber> collectAddedPhones(ClientErmbProfile newProfile, ClientErmbProfile oldProfile)
	{
		if (CollectionUtils.isEmpty(newProfile.phones))
			return Collections.emptyList();

		if (CollectionUtils.isEmpty(oldProfile.phones))
			return newProfile.phones;

		Collection<PhoneNumber> addedPhones = new HashSet<PhoneNumber>();
		for (PhoneNumber phone : newProfile.phones)
		{
			if (!oldProfile.phones.contains(phone))
				addedPhones.add(phone);
		}
		return addedPhones;
	}

	private Collection<PhoneNumber> collectRemovedPhones(ClientErmbProfile newProfile, ClientErmbProfile oldProfile)
	{
		if (CollectionUtils.isEmpty(oldProfile.phones))
			return Collections.emptyList();

		if (CollectionUtils.isEmpty(newProfile.phones))
			return oldProfile.phones;

		Collection<PhoneNumber> removedPhones = new HashSet<PhoneNumber>();
		for (PhoneNumber phone : oldProfile.phones)
		{
			if (!newProfile.phones.contains(phone))
				removedPhones.add(phone);
		}
		return removedPhones;
	}

	private String formatCSAPhone(PhoneNumber phone)
	{
		return PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phone);
	}

	private String formatMBKPhone(PhoneNumber phone)
	{
		return PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phone);
	}

	private String formatErmbProfilePhone(PhoneNumber phone)
	{
		return PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phone);
	}

	private PhoneNumber parseErmbProfilePhone(String phone)
	{
		return PhoneNumberFormat.MOBILE_INTERANTIONAL.parse(phone);
	}
}
