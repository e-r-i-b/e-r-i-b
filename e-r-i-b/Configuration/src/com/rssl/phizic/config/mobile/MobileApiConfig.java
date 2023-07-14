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
 * ������ mAPI
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
	private final Set<VersionNumber> apiVersions = new LinkedHashSet<VersionNumber>(); //��������� ���������� ������ API
	private String apiVersionsString; //���������� ������ API ����� �������
	private String invalidAPIVersionText; //���������, ������������ ������� � ������ ������������ ������ API
	private Properties invalidAPIVersionTexts; //���������, ������������ ������� � ������ ������������ ������ API
	private boolean jailbreakEnabled; //������� ������������ ������ iDevice-�� � jailbreak
	private String jailbreakEnabledText; //�����, ������������ �������, ���� ��������� ������ iDevice-�� � jailbreak
	private Properties jailbreakEnabledTexts; //�����, ������������ �������, ���� ��������� ������ iDevice-�� � jailbreak
	private String jailbreakDisabledText; //�����, ������������ �������, ���� ��������� ������ iDevice-�� � jailbreak
	private Properties jailbreakDisabledTexts; //�����, ������������ �������, ���� ��������� ������ iDevice-�� � jailbreak
	private Integer contactSyncPerDayLimit; //����� �������� �� ������������� ��������� ��������� � ����� �� ������ �������
	private Integer contactSyncPerWeekLimit; //����� �������� �� ������������� ��������� ��������� � ������ �� ������ �������
	private Integer firstContactSyncLimit; //����� �������� �� ������������� ��������� ��������� � ������ ���
	private Integer contactSyncSizeLimit; //����� ������� ��������� � ����� ������� �� ������������� ��������� ���������
	private Double mobilePhoneNumberSumDefault;//����� � ������, ������� ����� �� ��������� ���������� ������� ��� ������ ���������� ��������
	private Integer mobilePhoneNumberCount;//���������� �������, ������� ����� ������������ � ������ ����� ����������������� ������� �������
	private boolean isTemplateIgnoreProviderAvailability; //������ �� ������� ������ ����� ��� ����������� �� ����������� �� � ������ mAPI
	private boolean registrationAvailable = true; //���� ��������� ����������� � mAPI >= 5.01
	private boolean registrationAvailableLt501 = true; //���� ��������� ����������� � mAPI < 5.01
	private String registrationNotAvailableText = ""; //��������� ��� ����������� ����������� mAPI >= 5.01
	private String registrationNotAvailableTextLt501 = ""; //��������� ��� ����������� ����������� mAPI < 5.01
	private boolean fieldDictInLightEnabled; //��������� �� ������/������� �� ����������� ���������� ����������� � Light �����
	private Integer confirmationTimeout;
	private Integer confirmationAttemptsCount;
	private String fieldRiskInfoMessage; //����� ��������� � ����������� �������
	private Map<String, String> oldDepositCodesList; //���� ����� ������ (�� ��_14) �������
	private boolean autoSwitchPush; //�������������� ������������ ��������� ����������������� ������� ������������� � ���� ���������� �� push

	private int fundSenderCountDailyLimit;
	private int fundLifeTimeInDays;
	private boolean mobileApiRegistrationCheckCardNum; //������������� �������� 4-� ��������� ���� ������ ����� ��� ����������� ��������� ����������
	private boolean socialApiRegistrationCheckCardNum; //������������� �������� 4-� ��������� ���� ������ ����� ��� ����������� ���������� ����������

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
	private Map<String, String> deviceIcons; //���� � ������ ������ ��� ������ ��������� ����������

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
			throw new ConfigurationException("���� � ������� ������ � �������� mAPI " + apiVersionsString, e);
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
			throw new ConfigurationException("�� ������ �������������� �������� ("+PLATFORM_IDS+")");
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
	 * @return ������ API
	 */
	public Set<VersionNumber> getApiVersions()
	{
		return Collections.unmodifiableSet(apiVersions);
	}

	/**
	 * @return ������ API ����� �������
	 */
	public String getApiStringVersions()
	{
		return apiVersionsString;
	}

	/**
	 * @return ��������� �������� ������ API �������
	 */
	public String getInvalidAPIVersionText()
	{
		return invalidAPIVersionText;
	}
	/**
	 * @param localeId - ������������� ������
	 * @return ��������� �������� ������ API �������
	 */
	public String getInvalidAPIVersionText(String localeId)
	{
		String text = (String)invalidAPIVersionTexts.get(API_INVALID_VERSION_TEXT + getLocalizationSuffix(localeId));
		if (StringHelper.isEmpty(text))
			return invalidAPIVersionText;
		return text;
	}

	/**
	 * @return ������� ���������� ������ � ��������� � jailbreak
	 */
	public Boolean isJailbreakEnabled()
	{
		return jailbreakEnabled;
	}

	/**
	 * @return �����, ������������ �������, ���� ��������� ������ � ��������� � jailbreak
	 */
	public String getJailbreakEnabledText()
	{
		return jailbreakEnabledText;
	}
	/**
	 * @param localeId - ������������� ������
	 * @return �����, ������������ �������, ���� ��������� ������ � ��������� � jailbreak
	 */
	public String getJailbreakEnabledText(String localeId)
	{
		String text = (String)jailbreakEnabledTexts.get(JAILBREAK_ENABLED_TEXT + getLocalizationSuffix(localeId));
		if (StringHelper.isEmpty(text))
			return jailbreakEnabledText;
		return text;
	}

	/**
	 * @return �����, ������������ �������, ���� ��������� ������ � ��������� � jailbreak
	 */
	public String getJailbreakDisabledText()
    {
		return jailbreakDisabledText;
    }

	/**
	 * @param localeId - ������������� ������
	 * @return �����, ������������ �������, ���� ��������� ������ � ��������� � jailbreak
	 */
	public String getJailbreakDisabledText(String localeId)
    {
		String text = (String)jailbreakDisabledTexts.get(JAILBREAK_DISABLED_TEXT + getLocalizationSuffix(localeId));
	    if (StringHelper.isEmpty(text))
		    return jailbreakDisabledText;
	    return text;
    }

	/**
	 * @return ����� �������� �� ������������� ��������� ��������� � ����� �� ������ �������
	 */
	public Integer getContactSyncPerDayLimit()
	{
		return contactSyncPerDayLimit;
	}

	/**
	 * @return  ����� �������� �� ������������� ��������� ��������� � ������ �� ������ �������
	 */
	public Integer getContactSyncPerWeekLimit()
	{
		return contactSyncPerWeekLimit;
	}
	/**
	 * @return  ����� �������� �� ������������� ��������� ��������� ������� ���
	 */
	public Integer getFirstContactSyncLimit()
	{
		return firstContactSyncLimit;
	}

	/**
	 * @return ����� ������� ��������� � ����� ������� �� ������������� ��������� ���������
	 */
	@Deprecated
	public Integer getContactSyncSizeLimit()
	{
		return contactSyncSizeLimit;
	}

	/**
	 * @return ������ �� ������� ������ ����� ��� ����������� �� ����������� �� � ������ mAPI
	 */
	public boolean isTemplateIgnoreProviderAvailability()
	{
		return isTemplateIgnoreProviderAvailability;
	}

	/**
	 * @return ������ "��������� ����������� mAPI"
	 */
	public boolean isRegistrationAvailable(final VersionNumber version)
	{
		return version.lt(MobileAPIVersions.V5_01) ? registrationAvailableLt501 : registrationAvailable;
	}

	/**
	 * @return ��������� ��� ������ ����������� �� ��������
	 */
	public String getRegistrationNotAvailableText(final VersionNumber version)
	{
		return version.lt(MobileAPIVersions.V5_01) ? registrationNotAvailableTextLt501 : registrationNotAvailableText;
	}

	/**
	 * @return ��������� �� ������/������� �� ����������� ���������� ����������� � Light �����
	 */
	public boolean isFieldDictInLightEnabled()
	{
		return fieldDictInLightEnabled;
	}

	/**
	 * @return ���������� ������� �������������
	 */

	public int getConfirmationAttemptsCount()
	{
		return confirmationAttemptsCount;
	}

	/**
	 * @return ������� �������� �������������. ����� ��������� ��� ������������� �� �����������.
	 */
	public int getConfirmationTimeout()
	{
		return confirmationTimeout;
	}

	/**
	 * @return ����� ��������� � ����������� �������
	 */
	public String getFieldRiskInfoMessage()
	{
		return fieldRiskInfoMessage;
	}


    /**
     * @return ������������ ������ ������� ��������, ��������� ��� ������ � ������� ��������
     */
    public int getMaxSearchPeriodInDays()
    {
        return maxSearchPeriodInDays;
    }

	/**
	 * @return ������ "������" �������, ������� ����� �������� ������� � ���� < 8
	 */
	public Map<String, String> getOldDepositCodesList()
	{
		return oldDepositCodesList;
	}

	/**
	 * @return ����� � ������, ������� ����� �� ��������� ���������� ������� ��� ������ ���������� ��������
	 */
	public Double getMobilePhoneNumberSumDefault()
	{
		return mobilePhoneNumberSumDefault;
	}

	/**
	 * @return ���������� �������, ������� ����� ������������ � ������ ����� ����������������� ������� �������
	 */
	public Integer getMobilePhoneNumberCount()
	{
		return mobilePhoneNumberCount;
	}

	/**
	 * @return �������������� ������������ ��������� ����������������� ������� ������������� � ���� ���������� �� push
	 */
	public boolean isAutoSwitchPush()
	{
		return autoSwitchPush;
	}

	/**
	 * @return �������� ����� �� ���������� ����������� ����� ��� ������� �� ���� �������
	 */
	public int getFundSenderCountDailyLimit()
	{
		return fundSenderCountDailyLimit;
	}

	/**
	 * @return ����� ����� ������� �� ���� �������
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
	 * @return ���� � ������� ��� ������ ����������
	 */
	public Map<String, String> getDeviceIcons()
	{
		return deviceIcons;
	}
}
