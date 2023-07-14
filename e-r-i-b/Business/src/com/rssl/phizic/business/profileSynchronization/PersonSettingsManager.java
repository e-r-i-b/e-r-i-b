package com.rssl.phizic.business.profileSynchronization;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.csa.ProfileService;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.profileSynchronization.ProfileSynchronizationConfig;
import com.rssl.phizic.gate.csa.Profile;
import com.rssl.phizic.limits.handlers.ProfileInfo;
import com.rssl.phizic.limits.profile.information.ProfileInformation;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.utils.xml.xstream.XStreamSerializer;
import org.apache.commons.collections.MapUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rssl.phizic.common.types.limits.Constants.FACTORY_NAME;
import static com.rssl.phizic.common.types.limits.Constants.QUEUE_NAME;
import static com.rssl.phizic.logging.Constants.LOG_MODULE_CORE;

/**
 * @author lepihina
 * @ created 22.05.14
 * $Author$
 * $Revision$
 * Менеджер сохранения данных в централизованное хранилище
 */
public class PersonSettingsManager
{
	private static final String PERSON_SETTINGS_DATA_KEY = "personSettingsData";

	public  static final String CONFIRM_STRATEGY_SETTINGS_DATA_KEY = "userConfirmStrategySettings";
	public  static final String MENU_LINKS_DATA_KEY                = "menuLinks";
	public  static final String USER_REGION_KEY = "userRegion";
	public  static final String USER_CONTACTS_KEY = "userContacts";
	public  static final String USER_SUBSCRIPTION_KEY = "userSubscription";
	public  static final String USER_DOCUMENTS_KEY = "userDocument_";

	public static final String CARDS_INFO_KEY = "cardsInfo";
	public static final String ACCOUNT_INFO_KEY = "accountsInfo";
	public static final String LOAN_INFO_KEY = "loansInfo";
	public static final String IMACCOUNT_INFO_KEY = "imaccountsInfo";
	public static final String DEPO_ACCOUNT_INFO_KEY = "depoInfo";
	public static final String SECURITY_ACCOUNT_INFO_KEY = "securityAccountsInfo";
	public static final String PFR_LINK_INFO_KEY = "pfrLinkInfo";

	public static final String LOGIN_NOTIFICATION_KEY = "loginNotification";
	public static final String MAIL_NOTIFICATION_KEY = "mailNotification";
	public static final String OPERATION_NOTIFICATION_KEY = "operationNotification";
	public static final String NEWS_NOTIFICATION_KEY = "newsNotification";

	public static final String SHOW_CONTACTS_MESSAGE_KEY = "showContactsMessage";
	public static final String NEED_USE_INTERNET_SECURITY_KEY = "needUseInternetSecurity";

	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_CORE);
	private static final JmsService jmsService = new JmsService();
	private static final ProfileService profileService = new ProfileService();

	/**
	 * Сохраняет объект в централизованное хранилище
	 * @param o - объект для сохранения
	 */
	public static void savePersonData(Object o)
	{
		savePersonData(o.getClass().getName(), o);
	}

	/**
	 * Сохраняет объект в централизованное хранилище
	 * @param key - ключ для объекта
	 * @param o - объект для сохранения
	 */
	public static void savePersonData(String key, Object o)
	{
		if (!isUseReplicatePersonSettings())
			return;

		AuthenticationContext authenticationContext = AuthenticationContext.getContext();
		if (authenticationContext == null)
		{
			log.error("При сохранении данных в резервное хранилище произошла ошибка: нет контекста аутентификации текущей сессии");
			return;
		}

		if (authenticationContext.getProfileType() != ProfileType.MAIN)
			return;

		try
		{
			String message = PersonSettingsJMSBuilder.buildSavePersonSettingsMessage(key, serialize(o), authenticationContext);
			jmsService.sendMessageToQueue(message, QUEUE_NAME, FACTORY_NAME, null, null);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Получает объект из централизованного хранилища
	 * @param className - имя класса объекта
	 * @return объект
	 */
	public static Object getPersonData(Class className)
	{
		return getPersonData(className.getName());
	}

	/**
	 * Получает объект из централизованного хранилища
	 * @param key - ключ, по которому получаем объект
	 * @return объект
	 */
	public static Object getPersonData(String key)
	{
		if (!isUseReplicatePersonSettings())
			return null;

		Store store = StoreManager.getCurrentStore();
		if(store == null)
			return null;

		try
		{
			AuthenticationContext context = AuthenticationContext.getContext();
			//noinspection unchecked
			Map<String, Object> personSettingsData = (Map<String, Object>)store.restore(PERSON_SETTINGS_DATA_KEY);
			if (MapUtils.isEmpty(personSettingsData))
			{
				Profile profileWithHistory = profileService.findProfileWithHistory(context.getLastName(),context.getFirstName(),context.getMiddleName(),context.getDocumentNumber(),
																				   getBirthDateFromString(context.getBirthDate()),context.getTB());
				personSettingsData = new HashMap<String, Object>();

				if (profileWithHistory != null)
				{
					for (Profile csaProfile : profileWithHistory.getHistory())
					{
						updatePersonSettingsByProfile(csaProfile, personSettingsData);
					}
					updatePersonSettingsByProfile(profileWithHistory, personSettingsData);
				}

				store.save(PERSON_SETTINGS_DATA_KEY, personSettingsData);
			}
			return personSettingsData.get(key);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	private static void updatePersonSettingsByProfile(Profile csaProfile, Map<String, Object> personSettingsData) throws Exception
	{
		List<ProfileInformation> data = ProfileInformation.find(createProfileInfo(csaProfile));
		for(ProfileInformation information : data)
		{
			personSettingsData.put(information.getInformationType(), deserialize(information));
		}
	}

	private static ProfileInfo createProfileInfo(Profile csaProfile) throws BusinessException
	{
		return new ProfileInfo(
				csaProfile.getFirstName(),
				csaProfile.getSurName(),
				csaProfile.getPatrName(),
				csaProfile.getPassport(),
				csaProfile.getTb(),
				csaProfile.getBirthDay());
	}

	private static Calendar getBirthDateFromString(String birthDateStr) throws BusinessException
	{
		try
		{
			return DateHelper.toCalendar(DateHelper.fromXMlDateToDate(birthDateStr));
		}
		catch (ParseException e)
		{
			throw new BusinessException("Ошибка при преобразовании даты рождения клиента", e);
		}
	}

	private static String serialize(final Object o)
	{
		return XStreamSerializer.serialize(o);
	}

	private static Object deserialize(final ProfileInformation information)
	{
		return XStreamSerializer.deserialize(information.getData());
	}

	/**
	 * @return true - механизм сохранения данных в резервное хранилище включен
	 */
	private static boolean isUseReplicatePersonSettings()
	{
		return ConfigFactory.getConfig(ProfileSynchronizationConfig.class).isUseReplicatePersonSettings();
	}
}
