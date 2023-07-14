package com.rssl.auth.csa.back;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.regex.Pattern;

import static com.rssl.phizic.config.csa.Constants.*;

/**
 * @author krenev
 * @ created 31.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class Config extends com.rssl.phizic.config.Config
{
	/**
	 * Наименование источника получения данных о пользователях
	 */
	public enum UserInfoProviderName
	{
		MOBILE_BANK,
		WAY4U
	}

	private boolean denyMultipleRegistration;
	private boolean denyExistEnteredRegistration;
	private int confirmationTimeout;
	private int registrationTimeout;
	private int authenticationFailedBlockingTimeOut;
	private int maxAuthenticationFailed;
	private int restorePasswordTimeout;
	private int mobileRegistrationTimeout;
	private int maxMobileConnectorsCount;
	private int maxMobileRegistrationRequestCount;
	private int mobileRegistrationRequestCheckInterval;
	private int maxUserRegistrationRequestCount;
	private int userRegistrationRequestCheckInterval;
	private String iPasURL;
	private String eribURL;
	private int sessionTimeout;
	private int confirmCodeLength;
	private String confirmCodeAllowedChars;
	private int generatedPasswordLength;
	private String generatedPasswordAllowedChars;
	private int authenticationTimeout;
	private Pattern restorePasswordCbCodeDenyPattern;
	private Pattern userRegistrationCbCodeDenyPattern;
	private int iPasCsaBackTimeout;
	private int eribCsaBackTimeout;
	private boolean additionalRequestForSyncAllowed;
	private boolean postAuthenticationSyncAllowed;
	private int confirmationAttemptsCount;
	private String businessEnvironmentURL;
	private String businessEnvironmentSOAPAction;
	private boolean iPasPasswordStoreAllowed;
	private boolean iPasAuthenticationAllowed;
	private UserInfoProviderName userInfoProvider;
	private boolean postRegistrationLogin;
	private boolean accessAutoRegistration;
	private boolean way4uWorkaround089795;


	private long guestEntrySMSConfirmationAttempts;                        //Количество попыток подтверждения одного смс-пароля
	private int guestEntryAttempts;                  //Количество попыток гостевого входа по телефону
	private int guestEntryAttemptsLimitsMax;                    //Максимальное значение лимита на попытки гостевого вхрда (защита от дурака guestEntryAttempts)
	private long guestEntrySMSCooldown;                     //Период сброса ограничений на отправку СМС (в секундах)

	private boolean standInMode;

	public Config(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		denyMultipleRegistration = getBoolProperty(DENY_MULTIPLE_REGISTRATION_PROPERTY_NAME);
		denyExistEnteredRegistration = getBoolProperty(DENY_EXIST_ENTERED_REGISTRATION_PROPERTY_NAME);
		confirmationTimeout = getIntProperty(CONFIRMATION_TIMEOUT_PROPERTY_NAME);
		confirmCodeLength = getIntProperty(CONFIRMATION_CODE_LENGTH_PROPERTY_NAME);
		confirmCodeAllowedChars = getProperty(CONFIRMATION_CODE_ALLOWED_CHARS_PROPERTY_NAME);
		confirmationAttemptsCount = getIntProperty(CONFIRMATION_ATTEMPTS_COUNT_PROPERTY_NAME);
		registrationTimeout = getIntProperty(REGISTRATION_TIMEOUT_PROPERTY_NAME);
		authenticationFailedBlockingTimeOut = getIntProperty(AUTHENTICATION_FAILED_BLOCKING_TIME_OUT_PROPERTY_NAME);
		maxAuthenticationFailed = getIntProperty(MAX_AUTHENTICATION_FAILED_PROPERTY_NAME);
		restorePasswordTimeout = getIntProperty(RESTORE_PASSWORD_TIMEOUT_PROPERTY_NAME);
		mobileRegistrationTimeout = getIntProperty(MOBILE_REGISTRATION_TIMEOUT_PROPERTY_NAME);
		maxMobileConnectorsCount = getIntProperty(MAX_MOBILE_CONNECTORS_COUNT_PROPERTY_NAME);
		maxMobileRegistrationRequestCount = getIntProperty(MAX_MOBILE_REGISTRATION_REQUEST_COUNT_PROPERTY_NAME);
		mobileRegistrationRequestCheckInterval = getIntProperty(MOBILE_REGISTRATION_REQUEST_CHECK_INTERVAL_PROPERTY_NAME);
		maxUserRegistrationRequestCount = getIntProperty(MAX_USER_REGISTRATION_REQUEST_COUNT_PROPERTY_NAME);
		userRegistrationRequestCheckInterval = getIntProperty(USER_REGISTRATION_REQUEST_CHECK_INTERVAL_PROPERTY_NAME);
		iPasURL = getProperty(IPAS_URL_PROPERTY_NAME);
		eribURL = getProperty(ERIB_URL_PROPERTY_NAME);
		sessionTimeout = getIntProperty(SESSION_TIMEOUT);
		generatedPasswordLength = getIntProperty(GENERATED_PASSWORD_LENGTH_PROPERTY_NAME);
		generatedPasswordAllowedChars = getProperty(GENERATED_PASSWORD_ALLOWED_CHARS_PROPERTY_NAME);
		authenticationTimeout = getIntProperty(AUTHENTICATION_TIMEOUT_PROPERTY_NAME);
		restorePasswordCbCodeDenyPattern = getPatternProperty(RESTORE_PASSWORD_CB_CODE_DENY_PATTERN_PROPERTY_NAME);
		userRegistrationCbCodeDenyPattern = getPatternProperty(USER_REGISTRATION_CB_CODE_DENY_PATTERN_PROPERTY_NAME);
		iPasCsaBackTimeout = getIntProperty(IPAS_CSA_BACK_TIMEOUT);
		eribCsaBackTimeout = getIntProperty(ERIB_CSA_BACK_TIMEOUT);
		additionalRequestForSyncAllowed = getBoolProperty(ADDITIONAL_REQUEST_FOR_SYNC_ALLOWED_PROPERTY_NAME);
		postAuthenticationSyncAllowed = getBoolProperty(POST_AUTHENTICATION_SYNC_ALLOWED_PROPERTY_NAME);
		businessEnvironmentURL = getProperty(BUSINESS_ENVIRONMENT_URL_PROPERTY_NAME);
		businessEnvironmentSOAPAction = getProperty(BUSINESS_ENVIRONMENT_SOAPACTION_PROPERTY_NAME);
		iPasPasswordStoreAllowed = getBoolProperty(IPAS_PASSWORD_STORE_ALLOWED_PROPERTY_NAME);
		iPasAuthenticationAllowed = getBoolProperty(IPAS_AUTHENTICATION_ALLOWED_PROPERTY_NAME);
		userInfoProvider = UserInfoProviderName.valueOf(getProperty(USER_INFO_PROVIDER_PROPERTY_NAME));
		postRegistrationLogin = getBoolProperty(POST_REGISTRATION_LOGIN_PROPERTY_NAME);
		accessAutoRegistration = getBoolProperty(ACCESS_AUTO_REGISTRATION_PROPERTY_NAME);
		guestEntrySMSConfirmationAttempts = getLongProperty(GUEST_ENTRY_SMS_COUNT);
		guestEntryAttempts = getIntProperty(GUEST_ENTRY_SMS_COUNT_GLOBAL);
		guestEntryAttemptsLimitsMax = getIntProperty(GUEST_ENTRY_SMS_COUNT_MAX);
		guestEntrySMSCooldown = getLongProperty(GUEST_ENTRY_SMS_COOLDOWN);
		standInMode = getBoolProperty(STAND_IN_MODE_PROPERTY_NAME);
		way4uWorkaround089795 = getBoolProperty(WAY4U_WORKAROUND_089795_PROPERTY_NAME);
	}

	/**
	 * @return URL адрес сервиса iPas.
	 */
	public String getIPasURL()
	{
		return iPasURL;
	}

	/**
	 * @return URL адрес сервиса ЕРИБ.
	 */
	public String getERIBURL()
	{
		return eribURL;
	}

	/**
	 * @return запрещена ли повторная регистрация. Если регистрация запрещена высылается отказ.
	 * Если разрешена происходит обновление логина новыми регистрационными данными.  
	 */
	public boolean isDenyMultipleRegistration()
	{
		return denyMultipleRegistration;
	}

	/**
	 * @return Запрещена ли регистрация клиента, если он ранее входил в ЕРИБ
	 */
	public boolean isDenyExistEnteredRegistration()
	{
		return denyExistEnteredRegistration;
	}

	/**
	 * @return таймаут ожидания подтверждения. после истечения код подтверждения не принимается.
	 */
	public int getConfirmationTimeout()
	{
		return confirmationTimeout;
	}

	/**
	 * @return таймаут временной блокировки в секундах после неуспешной аутентифкации getMaxAuthenticationFailed раз.
	 */
	public int getAuthenticationFailedBlockingTimeOut()
	{
		return authenticationFailedBlockingTimeOut;
	}

	/**
	 * @return количество неудачных попыток аутентифкации, после которого будет произведена временная блокировка
	 */
	public long getMaxAuthenticationFailed()
	{
		return maxAuthenticationFailed;
	}

	/**
	 * Таймаут регистрации. По истечении этого времени, в регистрации по созданной заявке будет отказано.
	 * @return таймаут регистрации
	 */
	public int getRegistrationTimeout()
	{
		return registrationTimeout;
	}

	/**
	 * Таймаут восстановления пароля. По истечении этого времени, в востановлении пароля по созданной заявке будет отказано.
	 * @return таймаут восстановления пароля
	 */
	public int getRestorePasswordTimeout()
	{
		return restorePasswordTimeout;
	}

	/**
	 * Таймаут регистрации мобильного приложения. По истечении этого времени, в регистрации по созданной заявке будет отказано.
	 * @return таймаут регистрации
	 */
	public int getMobileRegistrationTimeout()
	{
		return mobileRegistrationTimeout;
	}

	/**
	 * @return максимальное допустимое количество зарегистрированных устройств на профиль
	 */
	public int getMaxMobileConnectorsCount()
	{
		return maxMobileConnectorsCount;
	}

	/**
	 * @return максимально допустимое количество неподтвержденных запросов на регистрацию за getMobileRegistrationRequestCheckInterval сек
	 */
	public int getMaxMobileRegistrationRequestCount()
	{
		return maxMobileRegistrationRequestCount;
	}

	/**
	 * @return количество секунд (глубина поиска, начиная от текущего времени) для проверки количество неподтвержденных запросов на регистрацию
	 */
	public int getMobileRegistrationRequestCheckInterval()
	{
		return mobileRegistrationRequestCheckInterval;
	}

	/**
	 * @return Максимальное время жини сессии в часах.
	 */
	public int getSessionTimeout()
	{
		return sessionTimeout;
	}

	/**
	 * @return длина смс кода подтверждения
	 */
	public int getConfirmCodeLength()
	{
		return confirmCodeLength;
	}

	/**
	 * @return список допустимых символов смс кода подтверждения
	 */
	public String getConfirmCodeAllowedChars()
	{
		return confirmCodeAllowedChars;
	}

	/**
	 * @return длина сгенерированного пароля для доступа в ЕРИБ
	 */
	public int getGeneratedPasswordLength()
	{
		return generatedPasswordLength;
	}

	/**
	 * @return множество допустимых символов сгененрированного пароля для доступа в ЕРИБ
	 */
	public String getGeneratedPasswordAllowedChars()
	{
		return generatedPasswordAllowedChars;
	}

	/**
	 * Таймаут аутентфикации те максимальное время между запросами на начало открытия сессии и получением информации по сесиии.
	 * По истечении этого времени, аутентфикации по созданной заявке будет отказано.
	 * @return таймаут аутентификации в секундах
	 */
	public int getAuthenticationTimeout()
	{
		return authenticationTimeout;
	}

	/**
	 * @return Регулярное выражение для подразделений, клиентам которых запрещена регистрация. Null, если запрета нет
	 */
	public Pattern getUserRegistrationCbCodeDenyPattern()
	{
		return userRegistrationCbCodeDenyPattern;
	}

	/**
	 * @return Регулярное выражение для подразделений, клиентам которых запрещено восстановление пароля. Null, если запрета нет 
	 */
	public Pattern getRestorePasswordCbCodeDenyPattern()
	{
		return restorePasswordCbCodeDenyPattern;
	}


	/**
	 * @return максимально допустимое количество неподтвержденных запросов на регистрацию за getUserRegistrationRequestCheckInterval сек
	 */
	public int getMaxUserRegistrationRequestCount()
	{
		return maxUserRegistrationRequestCount;
	}

	/**
	 * @return количество секунд между попытками для подсчета неподтвержденных запросов на регистрацию
	 */
	public int getUserRegistrationRequestCheckInterval()
	{
		return userRegistrationRequestCheckInterval;
	}

	/**
	 * @return время ожидания ответа в миллисекундах для CsaBack
	 */
	public int getIPasCsaBackTimeout()
	{
		return iPasCsaBackTimeout;
	}

	/**
	 * @return время ожидания ответа от ЕРИБ в миллисекундах для CsaBack
	 */
	public int getERIBCsaBackTimeout()
	{
		return eribCsaBackTimeout;
	}

	public boolean isAdditionalRequestForSyncAllowed()
	{
		return additionalRequestForSyncAllowed;
	}

	/**
	 * @return Требуется ли синхронизация после прохождения аутентификации(по данным из IPas)
	 */
	public boolean isPostAuthenticationSyncAllowed()
	{
		return postAuthenticationSyncAllowed;
	}
	/**
	 * @return количество попыток подтвреждения
	 */

	public int getConfirmationAttemptsCount()
	{
		return confirmationAttemptsCount;
	}

	/**
	 * @return урл на котором крутится сервис деловой среды
	 */
	public String getBusinessEnvironmentURL()
	{
		return businessEnvironmentURL;
	}

	/**
	 * @return SOAPAction деловой среды
	 */
	public String getBusinessEnvironmentSOAPAction()
	{
		return businessEnvironmentSOAPAction;
	}

	/**
	 * @return требуется ли сохранять пароли iPas.
	 */
	public boolean isIPasPasswordStoreAllowed()
	{
		return iPasPasswordStoreAllowed;
	}

	/**
	 * @return Разрешена ли аутентификация в iPas.
	 */
	public boolean isIPasAuthenticationAllowed()
	{
		return iPasAuthenticationAllowed;
	}

	/**
	 * @return имя источника получения данных о пользователях
	 */
	public UserInfoProviderName getUserInfoProvider()
	{
		return userInfoProvider;
	}

	/**
	 * @return true - требуется ли после неудачного запроса в WAY4 делать запрос в МБК(BUG089795: [ISUP] Реализовать настройку для отключения обращения в МБ).
	 */
	public boolean getWAY4UWorkaround089795()
	{
		return way4uWorkaround089795;
	}

	/**
	 * @return true - требуется вход по созданному коннектору после внешней регистрации
	 */
	public boolean isPostRegistrationLogin()
	{
		return postRegistrationLogin;
	}

	/**
	 * @return true - разрешена авторегистрация по алиасу старой цса
	 */
	public boolean isAccessAutoRegistration()
	{
		return accessAutoRegistration;
	}

	/**
	 * @return Количество попыток подтверждения одного смс-пароля
	 */
	public long getGuestEntrySMSConfirmationAttempts()
	{
		return guestEntrySMSConfirmationAttempts;
	}

	/**
	 * @return Период сброса ограничений на отправку СМС (в секундах)
	 */
	public long getGuestEntrySMSCooldown()
	{
		return guestEntrySMSCooldown;
	}

	/**
	 * @return Количество попыток гостевого входа по телефону
	 */
	public int getGuestEntryAttempts()
	{
		return Math.min(guestEntryAttempts, guestEntryAttemptsLimitsMax);
	}

	/**
	 * @return Максимальное значение лимита на попытки гостевого вхрда (защита от дурака guestEntryGlobalSMSCount )
	 */
	public long getGuestEntryAttemptsLimitsMax()
	{
		return guestEntryAttemptsLimitsMax;
	}

	/**
	 * @return true - включен режим StandIn
	 */
	public boolean isStandInMode()
	{
		return standInMode;
	}
}
