package com.rssl.phizic.web.util;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.persons.MultiInstancePersonService;
import com.rssl.phizic.business.persons.PersonBase;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.profile.TutorialProfileState;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * @author Egorova
 * @ created 19.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class PersonInfoUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static MultiInstancePersonService personService = new MultiInstancePersonService();
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private final static ClientPromoCodeService promoService = new ClientPromoCodeService();

	/**
	 * Возвращает ФИО пользователя из контекста
	 * 
	 * @return ФИО пользователя
	 */
	public static String getPersonFullName()
	{
		PersonBase personBase = getPersonInfo();

		if(personBase == null)
		{
			return null;
		}
		return personBase.getFullName();
	}
	/**
	 * Получение информации по текущему пользователю
	 * @return данные пользователя
	 */
	public static PersonBase getPersonInfo()
	{
		try
	    {
		    PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		    if (dataProvider == null)
		        return null;
		    PersonData data = dataProvider.getPersonData();
		    if (data == null)
		        return null;
		    if (data.getPerson().getCreationType() == CreationType.ANONYMOUS)
			    return null;
			return data.getPerson();
	    }
		catch (Exception e)
        {
            log.error("Ошибка получения данных пользователя", e);
	        return null;
        }
	}

	/**
	 * Метод возвращает ЕРМБ-профиль
	 * @param personId - id клиента
	 * @param activeOnly только активные профили
	 * @return ЕРМБ - профиль
	 */
	public static ErmbProfileImpl getErmbProfile(Long personId, boolean activeOnly)
	{
		try
		{
			ErmbProfileImpl ermbProfile = profileService.findByPersonId(personId);
			if (!activeOnly)
				return ermbProfile;
			if (ermbProfile!=null && ermbProfile.isServiceStatus() && !ermbProfile.isClientBlocked())
				return ermbProfile;
			return null;
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения данных о ЕРМБ-профиле", e);
			return null;
		}
	}

	/**
	 * Получить уровень безопасности клиента из сессии
	 * @return уровень безопасности клиента
	 */
	public static SecurityType restoreSecurityType()
	{
		PersonBase personBase = getPersonInfo();

		return personBase == null ? null : personBase.restoreSecurityType();
	}

	/**
	 * Получение информации о последнем входе в клиента.
	 * @return null в случае ошибки.
	 */
	public static Calendar getPersonLastLogonDate()
	{
		try
	    {
	        PersonData data = PersonContext.getPersonDataProvider().getPersonData();
			PersonBase person = data.getPerson();
		    Login login = person.getLogin();
		    return login.getLastLogonDate();
	    }
		catch (Exception e)
        {
            log.error("Ошибка получения информации о последнем входе в клиента", e);
	        return null;
        }

	}

	/**
	 * Получение информации об адресе последнего входа клиента.
	 * @return null в случае ошибки.
	 */
	public static String getPersonLastIpAddress()
	{
		try
	    {
	        PersonData data = PersonContext.getPersonDataProvider().getPersonData();
			PersonBase person = data.getPerson();
		    Login login = person.getLogin();
		    return login.getLastIpAddress();
	    }
		catch (Exception e)
        {
            log.error("Ошибка получения информации об адресе последнего входа клиента", e);
	        return null;
        }

	}

	/**
	 *
	 * @param personId идентификатор клиента.
	 * @return зарегистрированы ли изменеия по клиенту. true- зарегистрированы. false - не зарегистрированы.
	 */
	public static Boolean isNotModified(Long personId)
	{
		try
		{
			return personService.getPersonInstanceName(personId)==null;
		}
		catch (Exception e)
		{
			log.error("Ошибка определения регистрации изменений по клиенту", e);
			return null;
		}
	}

	/**
	 * Получить регион пользователя
	 * @return
	 */
	public static Region getPersonRegion()
	{
		try {
			return RegionHelper.getCurrentRegion();
		}
		catch (Exception ex) {
			log.error("Ошибка получения региона пользователя", ex);
			return null;
		}			
	}

	/**
	 * Получить регион пользователя
	 * @return
	 */
	public static Long getPersonRegionId()
	{
		try
		{
			Region region = getPersonRegion();
			return (region == null || region.getId() == null) ? -1L : region.getId();
		}
		catch (Exception ex) {
			log.error("Ошибка получения региона пользователя", ex);
			return -1L;
		}
	}
	/**
	 * Получить название региона пользователя
	 * @return название региона пользователя
	 */
	public static String getPersonRegionName()
	{
		try
		{
			Region currentRegion = RegionHelper.getCurrentRegion();
			if (currentRegion == null || RegionHelper.isAllRegionsSelected(currentRegion.getId()))
				return null;
			return currentRegion.getName();
		}
		catch (Exception ex) {
			log.error("Ошибка получения региона пользователя", ex);
			return null;
		}
	}

	/**
	 * Получить регион пользователя из профиля
	 * @return
	 */
	public static Region getProfileRegion()
	{
		try {
			return RegionHelper.getCurrentRegion();
		}
		catch (Exception ex) {
			log.error("Ошибка получения региона пользователя", ex);
			return null;
		}
	}

	/**
	 * Получить корневой регион для указанного региона
	 * @return корневой регион
	 */
	public static Region getParentRegion(Region region)
	{
		return RegionHelper.getParentRegion(region);
	}

	/**
	 * @return доступны ли клиенту виджеты
	 */
	public static boolean isAvailableWidget()
	{
		return false;
	}

	/**
	 * Получить карту входа клиента
	 * @param person клиент
	 * @return линк карты входа
	 */
	public static CardLink getLastLogonCard(PersonBase person)
	{
		String lastLogonCardNumber = person.getLogin().getLastLogonCardNumber();
		if (StringHelper.isEmpty(lastLogonCardNumber))
		{
			return null;
		}

		try
		{
			return externalResourceService.findInATMLinkByNumber(person.getLogin(), ResourceType.CARD, lastLogonCardNumber);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения карты входа", e);
			return null;
		}
	}

	/**
	 * @return выполнялась ли проверка статуса УДБО клиента
	 */
	public static boolean isCheckedUDBO()
	{
		try
		{
			return AuthenticationContext.getContext().isCheckedCEDBO();
		}
		catch (Exception e)
		{
			log.error("Ошибка проверки статуса УДБО", e);
			return false;
		}
	}

	/**
	 * @return признак, указывающий на необходимость показывать обучающую информацию в новом профиле
	 */
	public static boolean getShowTrainingInfo()
	{
		try
	    {
	        PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		    return data.getShowTrainingInfo();
	    }
		catch (Exception e)
        {
            log.error("Ошибка получения информации о необходимости показа обучения при входе в профиль", e);
	        return false;
        }
	}

	/**
	 * @return признак отображения значка новых пунктов меню для "Личной информации"
	 */
	public static boolean getNewPersonalInfo()
	{
		try
	    {
	        PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		    return data.getNewPersonalInfo();
	    }
		catch (Exception e)
        {
            log.error("Ошибка получения информации о необходимости отметки нового пункта меню 'Личная информация' в профиле", e);
	        return false;
        }
	}

	/**
	 * @return признак отображения значка новых пунктов меню для "Автопоиска счетов к оплате"
	 */
	public static boolean getNewBillsToPay()
	{
		try
	    {
	        PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		    return data.getNewBillsToPay();
	    }
		catch (Exception e)
        {
            log.error("Ошибка получения информации о необходимости отметки нового пункта меню 'Автопоиск счетов к оплате' в профиле", e);
	        return false;
        }
	}

	/**
	 * Отключает необходимость показа обучающей информации
	 */
	public static void closeProfileBubble()
	{
		try
	    {
	        PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		    data.setShowTrainingInfo(false);
	    }
		catch (Exception e)
        {
            log.error("Ошибка установки необходимости показа обучающей информации", e);
        }
	}

	/**
	 * Отключает необходимость показа знака новизны для пункта "Личная информация"
	 */
	public static void setOldPersonalInfo()
	{
		try
	    {
	        PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		    data.setNewPersonalInfo(false);
	    }
		catch (Exception e)
        {
            log.error("Ошибка установки необходимости показа знака новизны для пункта 'Личная информация'", e);
        }
	}

	/**
	 * Отключает необходимость показа знака новизны для пункта "Автопоиск счетов к оплате"
	 */
	public static void setOldBillsToPay()
	{
		try
	    {
	        PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		    data.setNewBillsToPay(false);
	    }
		catch (Exception e)
        {
            log.error("Ошибка установки необходимости показа знака новизны для пункта 'Автопоиск счетов к оплате'", e);
        }
	}

	/**
	 * Установить текущий статус промо в профиле
	 * @param state - статус
	 */
	public static void setPromoState(TutorialProfileState state)
	{
		try
	    {
	        PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		    data.setStateProfilePromo(state);
	    }
		catch (Exception e)
        {
            log.error("Ошибка установки состония промо в профиле", e);
        }
	}

	/**
	 * Проверить статус промо в профиле
	 * @param state - статус
	 * @return признак равенства текущего статуса проверямому
	 */
	public static boolean isPromoState(String state)
	{
		try
	    {
	        PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		    return (data.getStateProfilePromo() == TutorialProfileState.valueOf(state));
	    }
		catch (Exception e)
        {
            log.error("Ошибка получения информации о состоянии промо в профиле", e);
	        return false;
        }
	}

	/**
	 * Установить состояние настройки показа сообщения о перемещении
	 * пунктов "Мобильный банк" и "Мобильные приложения" в закрыто
	 */
	public static void setMobileItemsMovedClosed()
	{
		ConfigFactory.getConfig(UserPropertiesConfig.class).setMobileItemsMovedClosed(true);
	}

	/**
	 * @return состояние настройки показа сообщения о перемещении пунктов "Мобильный банк" и "Мобильные приложения"
	 */
	public static boolean isMobileItemsMovedClosed()
	{
		if (PersonContext.isAvailable() && !PersonHelper.isGuest())
		{
			return ConfigFactory.getConfig(UserPropertiesConfig.class).isMobileItemsMovedClosed();
		}

		return false;
	}

	/**
	 * @return есть ли у клиента ранее введённые промо-коды
	 */
	public static boolean isPersonHasPromoCodes()
	{
		if (PersonContext.isAvailable())
		{
            try
            {
                return promoService.isPersonHasPromoCodes(PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId());
            }
            catch (BusinessException e)
            {
                log.error("Ошибка при получении промокодов клиента" + PersonContext.getPersonDataProvider().getPersonData().getPerson().getId(), e);
            }
		}
		return false;
	}
}
