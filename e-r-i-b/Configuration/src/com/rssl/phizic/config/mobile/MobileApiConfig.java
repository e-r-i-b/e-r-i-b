package com.rssl.phizic.config.mobile;

import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.annotation.Singleton;
import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Конфиг mAPI
 * @author Dorzhinov
 * @ created 26.12.2012
 * @ $Author$
 * @ $Revision$
 */
@Singleton
@ThreadSafe
public class MobileApiConfig extends Config
{
	public static final String API_VERSIONS                = "mobile.api.versions";
	public static final String API_INVALID_VERSION_TEXT    = "mobile.api.invalid.version.text";
	public static final String JAILBREAK_ENABLED           = "mobile.api.jailbreak.enabled";
	public static final String JAILBREAK_ENABLED_TEXT      = "mobile.api.jailbreak.enabled.text";
	public static final String JAILBREAK_DISABLED_TEXT     = "mobile.api.jailbreak.disabled.text";
	public static final String CONTACT_SYNC_PER_DAY_LIMIT  = "mobile.api.contact.sync.per.day.limit";
	public static final String CONTACT_SYNC_PER_WEEK_LIMIT = "mobile.api.contact.sync.per.week.limit";
	public static final String FIRST_CONTACT_SYNC_LIMIT    = "mobile.api.contact.sync.first.limit";
	public static final String CONTACT_SYNC_SIZE_LIMIT     = "mobile.api.contact.sync.size.limit";
    public static final String MAX_SEARCH_PERIOD_IN_DAYS   = "operations.history.max.search.period.in.days";
	public static final String OLD_DEPOSIT_GROUP_CODES     = "old.deposit.group.codes";
	public static final String MOBILE_PHONENUMBER_SUM_DEFAULT   = "mobile.api.settings.field.pay.phonenumber.sum.default";
	public static final String MOBILE_PHONENUMBER_COUNT   = "mobile.api.settings.field.pay.phonenumber.count";
	public static final String FUND_SENDERS_COUNT_DAILY_LIMIT = "mobile.api.fund.limit.daily.senders.count";

	public static final String LIFETIME_REQUEST_ON_FUNDRAISER  = "com.rssl.iccs.lifetime-request-on-fundraiser";

	private static final String TEMPLATE_IGNORE_PROVIDER_AVAILABILITY = "mobile.api.template.ignore.provider.availability";
	private static final String REGISTRATION_AVAILABLE      = "mobile.api.registration.available";
	private static final String REGISTRATION_AVAILABLE_LT501 = "mobile.api.registration.available.lt501";
	private static final String REGISTRATION_NOT_AVAILABLE_TEXT = "mobile.api.registration.not.available.text";
	private static final String REGISTRATION_NOT_AVAILABLE_TEXT_LT501 = "mobile.api.registration.not.available.text.lt501";
	private static final String FIELD_DICT_IN_LIGHT_ENABLED = "mobile.api.field.dictionary.in.light.scheme.enabled";

	private static final String CONFIRMATION_TIMEOUT_PROPERTY_NAME = "com.rssl.iccs.csa.back.confirmation.timeout";
	private static final String CONFIRMATION_ATTEMPTS_COUNT_PROPERTY_NAME = "com.rssl.iccs.csa.back.confirmation.attempts.count";
	private static final String FIELD_RISK_INFO_MESSAGE = "mobile.api.field.risk.message";
	public static final String AUTO_SWITCH_PUSH_KEY = "mobile.api.auto.switch.push";
	private static final String SEPARATOR = ".";
	private static final String MOBILE_API_REGISTRATION_CHECK_CARD_NUM = "mobile.api.registration.check.card.num";
	private static final String SOCIAL_API_REGISTRATION_CHECK_CARD_NUM = "social.api.registration.check.card.num";
	private static final String DEVICE_ICON_PREFIX = "mobile.api.device.icon.";

	private int maxSearchPeriodInDays;
	private final Set<VersionNumber> apiVersions = new LinkedHashSet<VersionNumber>(); //множество допустимых версий API
	private String apiVersionsString; //допустимые версии API одной строкой
	private String invalidAPIVersionText; //текстовка, отображаемая клиенту в случае недопустимой версии API
	private Properties invalidAPIVersionTexts; //текстовка, отображаемая клиенту в случае недопустимой версии API
	private boolean jailbreakEnabled; //признак допустимости работы iDevice-ов с jailbreak
	private String jailbreakEnabledText; //текст, отображаемый клиенту, если разрешена работа iDevice-ов с jailbreak
	private Properties jailbreakEnabledTexts; //текст, отображаемый клиенту, если разрешена работа iDevice-ов с jailbreak
	private String jailbreakDisabledText; //текст, отображаемый клиенту, если запрещена работа iDevice-ов с jailbreak
	private Properties jailbreakDisabledTexts; //текст, отображаемый клиенту, если запрещена работа iDevice-ов с jailbreak
	private Integer contactSyncPerDayLimit; //лимит запросов на синхронизацию мобильных контактов в сутки от одного клиента
	private Integer contactSyncPerWeekLimit; //лимит запросов на синхронизацию мобильных контактов в неделю от одного клиента
	private Integer firstContactSyncLimit; //лимит запросов на синхронизацию мобильных контактов в первый раз
	private Integer contactSyncSizeLimit; //лимит номеров телефонов в одном запросе на синхронизацию мобильных контактов
	private Double mobilePhoneNumberSumDefault;//сумма в рублях, которая будет по умолчанию предложена клиенту для оплаты мобильного телефона
	private Integer mobilePhoneNumberCount;//количество номеров, которые будут передаваться в списке самых частооплачиваемых номеров клиента
	private boolean isTemplateIgnoreProviderAvailability; //оплата по шаблону оплаты услуг вне зависимости от доступности ПУ в канале mAPI
	private boolean registrationAvailable = true; //флаг включения регистрации в mAPI >= 5.01
	private boolean registrationAvailableLt501 = true; //флаг включения регистрации в mAPI < 5.01
	private String registrationNotAvailableText = ""; //текстовка при отключенной регистрации mAPI >= 5.01
	private String registrationNotAvailableTextLt501 = ""; //текстовка при отключенной регистрации mAPI < 5.01
	private boolean fieldDictInLightEnabled; //разрешена ли оплата/перевод по справочнику доверенных получателей в Light схеме
	private Integer confirmationTimeout;
	private Integer confirmationAttemptsCount;
	private String fieldRiskInfoMessage; //Текст сообщения о рискованном платеже
	private Map<String, String> oldDepositCodesList; //Коды групп старых (до РО_14) вкладов
	private boolean autoSwitchPush; //автоматическое переключение настройки предпочтительного способа подтверждений и всех оповещений на push

	private int fundSenderCountDailyLimit;
	private int fundLifeTimeInDays;
	private boolean mobileApiRegistrationCheckCardNum; //необходимость указания 4-х последних цифр номера карты при регистрации мобильных приложений
	private boolean socialApiRegistrationCheckCardNum; //необходимость указания 4-х последних цифр номера карты при регистрации социальных приложений

	private static final String PLATFORM_PREFIX = "mobile.api.platform.";

	private static final String PLATFORM_IDS = "mobile.api.platform.ids";

	private static final String NAME = ".name";
	private static final String VERSION = ".version";
	private static final String ERROR_TEXT = ".text";
	private static final String BANK_URL = ".bankURL";
	private static final String EXTERNAL_URL = ".externalURL";
	private static final String USE_CAPTCHA = ".use.captcha";
	private static final String IS_SOCIAL = ".is.social";
	private static final String UNALLOWED_BROWSERS = ".browsers.unallowed";

	private final String QRCODE = ".qrCode";

	private String[] platformNames;
	private List<MobilePlatformInfo> platforms = new ArrayList<MobilePlatformInfo>();
	private Map<String, String> deviceIcons; //пути к файлам иконок для списка мобильных приложений

	public MobileApiConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh()
	{
		readApiVersionsInfo();
		readJailbreakInfo();
		readContactSyncInfo();
		isTemplateIgnoreProviderAvailability = getBoolProperty(TEMPLATE_IGNORE_PROVIDER_AVAILABILITY);
		readRegistrationSettings();
		fieldDictInLightEnabled = getBoolProperty(FIELD_DICT_IN_LIGHT_ENABLED);
		confirmationTimeout = getIntProperty(CONFIRMATION_TIMEOUT_PROPERTY_NAME);
		confirmationAttemptsCount = getIntProperty(CONFIRMATION_ATTEMPTS_COUNT_PROPERTY_NAME);
		fieldRiskInfoMessage = getProperty(FIELD_RISK_INFO_MESSAGE);
        maxSearchPeriodInDays = getIntProperty(MAX_SEARCH_PERIOD_IN_DAYS);
		oldDepositCodesList = getOldDepositCodes();
		mobilePhoneNumberSumDefault = getDoubleProperty(MOBILE_PHONENUMBER_SUM_DEFAULT);
		mobilePhoneNumberCount = getIntProperty(MOBILE_PHONENUMBER_COUNT);
		autoSwitchPush = getBoolProperty(AUTO_SWITCH_PUSH_KEY);
		fundSenderCountDailyLimit = getIntProperty(FUND_SENDERS_COUNT_DAILY_LIMIT);
		fundLifeTimeInDays = getIntProperty(LIFETIME_REQUEST_ON_FUNDRAISER);
		mobileApiRegistrationCheckCardNum = getBoolProperty(MOBILE_API_REGISTRATION_CHECK_CARD_NUM);
		socialApiRegistrationCheckCardNum = getBoolProperty(SOCIAL_API_REGISTRATION_CHECK_CARD_NUM);
		deviceIcons = getDeviceIconsSettings();
	}

	private void readApiVersionsInfo() throws ConfigurationException
	{
		apiVersions.clear();
		apiVersionsString = getProperty(API_VERSIONS);
		invalidAPIVersionText = getProperty(API_INVALID_VERSION_TEXT);
		invalidAPIVersionTexts = getProperties(API_INVALID_VERSION_TEXT);
		try
		{
			if (apiVersionsString == null)
				return;

			for (String versionString : StringUtils.split(apiVersionsString, ","))
				apiVersions.add(VersionNumber.fromString(versionString));
		}
		catch (MalformedVersionFormatException e)
		{
			apiVersions.clear();
			throw new ConfigurationException("Сбой в разборе строки с версиями mAPI " + apiVersionsString, e);
		}
	}

	private void readJailbreakInfo()
	{
		jailbreakEnabled = getBoolProperty(JAILBREAK_ENABLED);
		jailbreakEnabledText = getProperty(JAILBREAK_ENABLED_TEXT);
		jailbreakEnabledTexts = getProperties(JAILBREAK_ENABLED_TEXT);
		jailbreakDisabledText = getProperty(JAILBREAK_DISABLED_TEXT);
		jailbreakDisabledTexts = getProperties(JAILBREAK_DISABLED_TEXT);
	}

	private void readContactSyncInfo()
	{
		contactSyncPerDayLimit = getIntProperty(CONTACT_SYNC_PER_DAY_LIMIT);
		contactSyncPerWeekLimit = getIntProperty(CONTACT_SYNC_PER_WEEK_LIMIT);
		firstContactSyncLimit = getIntProperty(FIRST_CONTACT_SYNC_LIMIT);
		contactSyncSizeLimit = getIntProperty(CONTACT_SYNC_SIZE_LIMIT);
	}

	private void readRegistrationSettings()
	{
		registrationAvailable = getBoolProperty(REGISTRATION_AVAILABLE);
		registrationAvailableLt501 = getBoolProperty(REGISTRATION_AVAILABLE_LT501);
		registrationNotAvailableText = getProperty(REGISTRATION_NOT_AVAILABLE_TEXT);
		registrationNotAvailableTextLt501 = getProperty(REGISTRATION_NOT_AVAILABLE_TEXT_LT501);
	}

	public List<MobilePlatformInfo> getPlatforms()
	{
		if (platformNames == null) readPlatformNames();
		if (platforms.isEmpty()) readPlatformInfo();
		return platforms;
	}

	private void readPlatformNames()
	{
		String platforms = getProperty(PLATFORM_IDS);
		System.out.println("platforms = "+platforms);
		if (StringHelper.isNotEmpty(platforms))
		{
			platformNames = StringUtils.split(platforms,',');
		}
		else
		{
			throw new ConfigurationException("не заданы идентификаторы плафторм ("+PLATFORM_IDS+")");
		}
	}

	private void readPlatformInfo()
	{
		for (String platformId : platformNames)
		{
			MobilePlatformInfo platform = new MobilePlatformInfo();
			platform.setPlatformId(platformId);
			platform.setName(getProperty(PLATFORM_PREFIX + platformId + NAME));
			platform.setVersion(Integer.valueOf(getProperty(PLATFORM_PREFIX + platformId + VERSION)));
			platform.setErrText(getProperty(PLATFORM_PREFIX + platformId + ERROR_TEXT));
			platform.setBankURL(getProperty(PLATFORM_PREFIX + platformId + BANK_URL));
			platform.setExternalURL(getProperty(PLATFORM_PREFIX + platformId + EXTERNAL_URL));
			platform.setQrCode(getProperty(PLATFORM_PREFIX + platformId + QRCODE));
			platform.setUseCaptcha(getBoolProperty(PLATFORM_PREFIX + platformId + USE_CAPTCHA));
			platform.setSocial(getBoolProperty(PLATFORM_PREFIX + platformId + IS_SOCIAL));
			platform.setUnallowedBrowsers(getProperty(PLATFORM_PREFIX + platformId + UNALLOWED_BROWSERS));
			platforms.add(platform);
		}
	}

	private Map<String, String> getOldDepositCodes()
	{
		String codesStr = getProperty(OLD_DEPOSIT_GROUP_CODES);
		String[] codes = codesStr.replace(" ", "").split(",");

		Map<String, String> depositCodesMap = new HashMap<String, String>(codes.length);

		for (String pair : codes)
		{
			String[] entity = pair.split(":");
			depositCodesMap.put(entity[0], entity[1]);
		}

		return depositCodesMap;
	}

	/**
	 * @return версии API
	 */
	public Set<VersionNumber> getApiVersions()
	{
		return Collections.unmodifiableSet(apiVersions);
	}

	/**
	 * @return версии API одной строкой
	 */
	public String getApiStringVersions()
	{
		return apiVersionsString;
	}

	/**
	 * @return текстовка неверной версии API клиента
	 */
	public String getInvalidAPIVersionText()
	{
		return invalidAPIVersionText;
	}
	/**
	 * @param localeId - идентификатор локали
	 * @return текстовка неверной версии API клиента
	 */
	public String getInvalidAPIVersionText(String localeId)
	{
		String text = (String)invalidAPIVersionTexts.get(API_INVALID_VERSION_TEXT + getLocalizationSuffix(localeId));
		if (StringHelper.isEmpty(text))
			return invalidAPIVersionText;
		return text;
	}

	/**
	 * @return признак разрешения работы с устройств с jailbreak
	 */
	public Boolean isJailbreakEnabled()
	{
		return jailbreakEnabled;
	}

	/**
	 * @return текст, отображаемый клиенту, если разрешена работа с устройств с jailbreak
	 */
	public String getJailbreakEnabledText()
	{
		return jailbreakEnabledText;
	}
	/**
	 * @param localeId - идентификатор локали
	 * @return текст, отображаемый клиенту, если разрешена работа с устройств с jailbreak
	 */
	public String getJailbreakEnabledText(String localeId)
	{
		String text = (String)jailbreakEnabledTexts.get(JAILBREAK_ENABLED_TEXT + getLocalizationSuffix(localeId));
		if (StringHelper.isEmpty(text))
			return jailbreakEnabledText;
		return text;
	}

	/**
	 * @return текст, отображаемый клиенту, если запрещена работа с устройств с jailbreak
	 */
	public String getJailbreakDisabledText()
    {
		return jailbreakDisabledText;
    }

	/**
	 * @param localeId - идентификатор локали
	 * @return текст, отображаемый клиенту, если запрещена работа с устройств с jailbreak
	 */
	public String getJailbreakDisabledText(String localeId)
    {
		String text = (String)jailbreakDisabledTexts.get(JAILBREAK_DISABLED_TEXT + getLocalizationSuffix(localeId));
	    if (StringHelper.isEmpty(text))
		    return jailbreakDisabledText;
	    return text;
    }

	/**
	 * @return лимит запросов на синхронизацию мобильных контактов в сутки от одного клиента
	 */
	public Integer getContactSyncPerDayLimit()
	{
		return contactSyncPerDayLimit;
	}

	/**
	 * @return  лимит запросов на синхронизацию мобильных контактов в неделю от одного клиента
	 */
	public Integer getContactSyncPerWeekLimit()
	{
		return contactSyncPerWeekLimit;
	}
	/**
	 * @return  лимит запросов на синхронизацию мобильных контактов впервый раз
	 */
	public Integer getFirstContactSyncLimit()
	{
		return firstContactSyncLimit;
	}

	/**
	 * @return лимит номеров телефонов в одном запросе на синхронизацию мобильных контактов
	 */
	@Deprecated
	public Integer getContactSyncSizeLimit()
	{
		return contactSyncSizeLimit;
	}

	/**
	 * @return оплата по шаблону оплаты услуг вне зависимости от доступности ПУ в канале mAPI
	 */
	public boolean isTemplateIgnoreProviderAvailability()
	{
		return isTemplateIgnoreProviderAvailability;
	}

	/**
	 * @return флажок "разрешена регистрация mAPI"
	 */
	public boolean isRegistrationAvailable(final VersionNumber version)
	{
		return version.lt(MobileAPIVersions.V5_01) ? registrationAvailableLt501 : registrationAvailable;
	}

	/**
	 * @return текстовка для случая регистрация не доступна
	 */
	public String getRegistrationNotAvailableText(final VersionNumber version)
	{
		return version.lt(MobileAPIVersions.V5_01) ? registrationNotAvailableTextLt501 : registrationNotAvailableText;
	}

	/**
	 * @return разрешена ли оплата/перевод по справочнику доверенных получателей в Light схеме
	 */
	public boolean isFieldDictInLightEnabled()
	{
		return fieldDictInLightEnabled;
	}

	/**
	 * @return количество попыток подтвреждения
	 */

	public int getConfirmationAttemptsCount()
	{
		return confirmationAttemptsCount;
	}

	/**
	 * @return таймаут ожидания подтверждения. после истечения код подтверждения не принимается.
	 */
	public int getConfirmationTimeout()
	{
		return confirmationTimeout;
	}

	/**
	 * @return Текст сообщения о рискованном платеже
	 */
	public String getFieldRiskInfoMessage()
	{
		return fieldRiskInfoMessage;
	}


    /**
     * @return Максимальный период истории операций, доступный для поиска в истории операций
     */
    public int getMaxSearchPeriodInDays()
    {
        return maxSearchPeriodInDays;
    }

	/**
	 * @return список "старых" вкладов, которые будут доступны клиенту в мАпи < 8
	 */
	public Map<String, String> getOldDepositCodesList()
	{
		return oldDepositCodesList;
	}

	/**
	 * @return сумма в рублях, которая будет по умолчанию предложена клиенту для оплаты мобильного телефона
	 */
	public Double getMobilePhoneNumberSumDefault()
	{
		return mobilePhoneNumberSumDefault;
	}

	/**
	 * @return количество номеров, которые будут передаваться в списке самых частооплачиваемых номеров клиента
	 */
	public Integer getMobilePhoneNumberCount()
	{
		return mobilePhoneNumberCount;
	}

	/**
	 * @return автоматическое переключение настройки предпочтительного способа подтверждений и всех оповещений на push
	 */
	public boolean isAutoSwitchPush()
	{
		return autoSwitchPush;
	}

	/**
	 * @return суточный лимит на количество получателей денег для запроса на сбор средств
	 */
	public int getFundSenderCountDailyLimit()
	{
		return fundSenderCountDailyLimit;
	}

	/**
	 * @return Время жизни запроса на сбор средств
	 */
	public int getFundLifeTimeInDays()
	{
		return fundLifeTimeInDays;
	}

	private String getLocalizationSuffix(String localeId)
	{
		if (StringHelper.isNotEmpty(localeId))
			return SEPARATOR + localeId;
		return "";
	}

	public boolean isMobileApiRegistrationCheckCardNum()
	{
		return mobileApiRegistrationCheckCardNum;
	}

	public boolean isSocialApiRegistrationCheckCardNum()
	{
		return socialApiRegistrationCheckCardNum;
	}

	private Map<String, String> getDeviceIconsSettings()
	{
		Properties properties = getProperties(DEVICE_ICON_PREFIX);
		Map<String, String> deviceIconsMap = new HashMap<String, String>();
		for (Map.Entry entry : properties.entrySet())
			deviceIconsMap.put(((String)(entry.getKey())).substring(DEVICE_ICON_PREFIX.length()), (String)entry.getValue());
		return deviceIconsMap;
	}

	/**
	 * @return пути к иконкам для списка приложений
	 */
	public Map<String, String> getDeviceIcons()
	{
		return deviceIcons;
	}
}
