package com.rssl.phizic.operations.ermb.person;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ermb.*;
import com.rssl.phizic.business.ermb.migration.mbk.ERMBPhoneService;
import com.rssl.phizic.business.ermb.profile.ErmbProfileListener;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbProfileEvent;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.type.TimeZone;
import com.rssl.phizic.common.types.Day;
import com.rssl.phizic.common.types.DaysOfWeek;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.userprofile.ErmbProfileSettings;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.sql.Time;
import java.util.*;

/**
 @author: Egorovaa
 @ created: 25.09.2012
 @ $Author$
 @ $Revision$
 */
public class EditErmbOperation extends ConfirmableOperationBase implements EditEntityOperation
{
	private PersonData personData;
	private List<CardLink> cards;

	private ErmbProfileOperationEntity entity;
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private final static ErmbTariffService tariffService = new ErmbTariffService();
	private final static SimpleService simpleService = new SimpleService();
	private final ERMBPhoneService ermbPhoneService = new ERMBPhoneService();

	private ErmbProfileParams initParams;
	private Map<String, Object> ermbSettings;

	private ErmbProfileListener updateListener = ErmbUpdateListener.getListener();
	private ErmbProfileEvent event;

	public void initialize() throws BusinessException,BusinessLogicException
	{
		personData = PersonContext.getPersonDataProvider().getPersonData();
		entity = new ErmbProfileOperationEntity();
		initParams = new ErmbProfileParams();
		ermbSettings = new HashMap<String, Object>();

		Long personId = personData.getPerson().getId();

		ErmbProfileImpl profile = profileService.findByPersonId(personId);
		if (profile!=null)
		{
			event = new ErmbProfileEvent(ErmbHelper.copyProfile(profile));

			List<ErmbTariff> tarifs = tariffService.getAllTariffs();
			if (tarifs.isEmpty())
				throw  new BusinessException("Необходимо добавить хотя бы один тариф подключения ЕРМБ");
			entity.setTarifs(tarifs);
			entity.setProfile(profile);
			entity.setSelectedTarif(String.valueOf(profile.getTarif().getId()));
			ErmbClientPhone mainPhone = getMainMobilePhone();
			if (mainPhone!=null)
				entity.setMainPhoneNumber(mainPhone.getNumber());
			Set<String> phoneNumbers = profile.getPhoneNumbers();
			if (phoneNumbers != null && !phoneNumbers.isEmpty())
				entity.setPhonesNumber(phoneNumbers);
			CardLink foreginProduct = ErmbHelper.getForeginProduct(profile);
			if(foreginProduct != null)
				entity.setMainCardId(foreginProduct.getId().toString());

			fillParams(entity, initParams);
		}
		else
		{
			log.info("У клиента " + personId + " нет профиля ЕРМБ");
		}
	}

	/**
	 * Высчитать дату окончания льготного периода
	 * Не отображается если эта дата уже прошла или если тарифный план не предполагает льготный период.
	 * @return дата в формате Calendar
	 */
	public Calendar getGracePeriodEndDate()
	{
		ErmbProfileImpl profile = entity.getProfile();
		Calendar gracePeriodEndDate = profile.getGracePeriodEnd();
		if (gracePeriodEndDate == null)
			return null;
		if (DateHelper.getCurrentDate().after(gracePeriodEndDate))
			return null;
		if (profile.getTarif().getGracePeriod() == 0)
			return null;

		return gracePeriodEndDate;
	}

	/**
	 * Запомнить изначальные параметры подключения
	 * @param entity -
	 * @param ermbParams - параметры
	 */
	private void fillParams(ErmbProfileOperationEntity entity, ErmbProfileParams ermbParams)
	{
		ErmbProfileImpl profile = entity.getProfile();
		ermbParams.setSelectedTarifId(Integer.parseInt(entity.getSelectedTarif()));
		ermbParams.setMainCardId(entity.getMainCardId());
		ermbParams.setPhones(entity.getPhonesNumber());
		ermbParams.setMainPhoneNumber(entity.getMainPhoneNumber());
		ermbParams.setNotificationStartTime(profile.getNotificationStartTime());
		ermbParams.setNotificationEndTime(profile.getNotificationEndTime());
		ermbParams.setTimeZone(profile.getTimeZone());
		ermbParams.setNtfDays(profile.getDaysOfWeek());
		ermbParams.setFastServiceAvailable(profile.getFastServiceAvailable());
		ermbParams.setDepositsTransfer(profile.getDepositsTransfer());
	}

	/**
	 * Были ли внесены изменения
	 * @return true, если не было изменений
	 * @throws BusinessException
	 */
	public boolean noChanges() throws BusinessException
	{
		checkChanges();
		return ermbSettings.isEmpty();
	}

	/**
	 * Фиксируем изменения, сделанные в настройках подключения
	 * @throws BusinessException
	 */
	private void checkChanges() throws BusinessException
	{
		ErmbProfileImpl profile = entity.getProfile();

		int selectedTarif = Integer.parseInt(entity.getSelectedTarif());
		if (selectedTarif != initParams.getSelectedTarifId())
		{
			for (ErmbTariff tarif : entity.getTarifs())
			{
				if (tarif.getId() == selectedTarif)
					ermbSettings.put(ErmbProfileSettings.TARIF, tarif);
			}
		}

		if(!StringHelper.equals(entity.getMainCardId(), StringHelper.getEmptyIfNull(initParams.getMainCardId())))
		{
			//Если карта списания была изменена, надо это зафиксировать для отображения в окне ввода пароля
			//(в мапе должен быть ключ, соответствующий карте)
			CardLink cardLink = null;
			if (StringHelper.isNotEmpty(entity.getMainCardId()))
			{
				Long mainCardId = Long.valueOf(entity.getMainCardId());
				cardLink = simpleService.findById(CardLink.class, mainCardId);

			}
			ermbSettings.put(ErmbProfileSettings.MAIN_CARD, cardLink);
		}

		if(!StringHelper.equals(entity.getMainPhoneNumber(),initParams.getMainPhoneNumber()))
			ermbSettings.put(ErmbProfileSettings.MAIN_PHONE, entity.getMainPhoneNumber());

		Set<String> phones = initParams.getPhones();
		phones.removeAll(entity.getPhonesNumber());
		if(!phones.isEmpty())
			ermbSettings.put(ErmbProfileSettings.PHONES, entity.getPhonesNumber());

		Time ntfStartTime = profile.getNotificationStartTime();
		if(!ntfStartTime.equals(initParams.getNotificationStartTime()))
			ermbSettings.put(ErmbProfileSettings.START_TIME, ntfStartTime);

		Time ntfEndTime = profile.getNotificationEndTime();
		if(!ntfEndTime.equals(initParams.getNotificationEndTime()))
			ermbSettings.put(ErmbProfileSettings.END_TIME, ntfEndTime);

		if(profile.getTimeZone() != initParams.getTimeZone())
		{
			TimeZone timeZone = TimeZone.getKnownTimeZone(profile.getTimeZone());
			ermbSettings.put(ErmbProfileSettings.TIME_ZONE, timeZone);
		}

		DaysOfWeek notificationDays = profile.getDaysOfWeek();
		Set<Day> oldDays = initParams.getNtfDays() != null ? initParams.getNtfDays().getDays() : new HashSet<Day>();
		if(!notificationDays.getDays().equals(oldDays))
		{
			String[] days = null;
			if (notificationDays != null)
				days = notificationDays.getFullNameStrDaysRus();
			ermbSettings.put(ErmbProfileSettings.DAYS, days);
		}

		boolean depositsTransfer = profile.getDepositsTransfer();
		if(depositsTransfer != initParams.getDepositsTransfer())
			ermbSettings.put(ErmbProfileSettings.DEPOSIT_TRANSFER, depositsTransfer);

		boolean fastServiceAvailable = profile.getFastServiceAvailable();
		if(fastServiceAvailable != initParams.getFastServiceAvailable())
			ermbSettings.put(ErmbProfileSettings.FAST_SERVICE_AVAILABLE, fastServiceAvailable);
	}

	/**
	 * Получить основной номер моб. телефона
	 * @return
	 * @throws BusinessException
	 */
	public ErmbClientPhone getMainMobilePhone() throws BusinessException
	{
		Set<ErmbClientPhone> phones =  entity.getProfile().getPhones();
		for (ErmbClientPhone phone : phones)
		{
			if (phone.isMain())
				return phone;
		}
		return null;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		ErmbProfileImpl profile = entity.getProfile();
		Long tarifId = Long.valueOf(entity.getSelectedTarif());
		ErmbTariff tarif = tariffService.findById(tarifId);
		ErmbHelper.updateErmbTariff(profile, tarif);

		if (!StringHelper.isEmpty(entity.getMainCardId()))
		{
			//Приоритетная карта для списания абонентской платы
			Long mainCardId = Long.valueOf(entity.getMainCardId());
			CardLink cardLink = simpleService.findById(CardLink.class, mainCardId);
				profile.setForeginProduct(cardLink);
		}
		else
			profile.setForeginProduct(null);

		Set<ErmbClientPhone> clientPhones = profile.getPhones();
		// Назначаем основной номер телефона
		for (ErmbClientPhone phone : clientPhones)
		{
			if (StringHelper.equalsNullIgnore(phone.getNumber(), entity.getMainPhoneNumber()))
				phone.setMain(true);
			else
				phone.setMain(false);
		}

		// Удаляем отмеченные клиентом телефоны
		List<String> phonesToDelNumbers = new ArrayList<String>(profile.getPhoneNumbers().size());
		for (Iterator<ErmbClientPhone> iterator = profile.getPhones().iterator(); iterator.hasNext();)
		{
			String phoneNumber = iterator.next().getNumber();
			if (!entity.getPhonesNumber().contains(phoneNumber))
			{
				iterator.remove();
				phonesToDelNumbers.add(phoneNumber);
			}
		}

		if (entity.getPhonesNumber().isEmpty())
		{
			// Не выставляем клиентскую блокировку согласно
			// BUG075813: ЕРМБ : АРМ Сотрудника : Блокировка услуги при удалении номера
			// ErmbHelper.blockProfile(profile, ErmbHelper.NO_PHONE_BLOCK_DESCRIPTION);
			profile.setServiceStatus(false);
		}

		event.setNewProfile(profile);
		updateListener.beforeProfileUpdate(event);
		profileService.addOrUpdate(profile);
		ermbPhoneService.saveOrUpdateERMBPhones(Collections.<String>emptyList(), phonesToDelNumbers);
		updateListener.afterProfileUpdate(event);

		try
		{
			CSABackRequestHelper.sendUpdatePhoneRegistrationsRq(
					entity.getMainPhoneNumber(),
					PersonHelper.buildUserInfo(profile.getPerson()),
					Collections.<String>emptyList(),
					phonesToDelNumbers
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

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return entity;
	}

	/**
	 * Получить код предпочтительной карты для списания средств
	 * @return код карты, если указана предпочтительная карта для списания средств
	 */
	public String getCardCode()
	{
		CardLink link = ErmbHelper.getForeginProduct(entity.getProfile());
		if (link!=null)
			return link.getCode();
		return null;
	}

	/**
	 * Преобразовать список дней рассылки в строку (для jsp)
	 * @return строка с перечисленными через запятую днями. Если в профиле не указаны дни - null
	 */
	public String getDaysOfWeek()
	{
		DaysOfWeek daysOfWeek = entity.getProfile().getDaysOfWeek();
		if (daysOfWeek != null)
		{
			String[] days = daysOfWeek.getStringDays();
			if (days.length == 0)
				return null;
			return StringUtils.join(days, ",");
		}
		else
			return null;
	}

	/**
	 * Получить кардлинки с смс-алиасами
	 * @return кардлинки с смс-алиасами
	 */
	public List<CardLink> getCards () throws BusinessException, BusinessLogicException
	{
	    if (cards == null)
	        cards = personData.getCards();
	    return cards;
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		save();
	}

	public ConfirmableObject getConfirmableObject()
	{
		return new ErmbProfileSettings(ermbSettings);
	}
}
