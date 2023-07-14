package com.rssl.phizic.config;

import com.rssl.auth.csa.front.operations.auth.verification.VerificationState;
import com.rssl.phizic.utils.StringHelper;

import java.util.Properties;
import java.util.regex.Pattern;

import static com.rssl.phizic.config.csa.Constants.*;

/**
 * ������ ����� �������� ��� CSAFront
 * @author niculichev
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CSAFrontConfig extends Config
{
	private static Pattern FALSE_PATTERN = Pattern.compile("$.^");
	private static Pattern TRUE_PATTERN = Pattern.compile(".*");

	private boolean accessInternalRegistration;
	private boolean accessExternalRegistration;
	private boolean accessRecoverPassword;
	private boolean useCaptchaRestrict;
	private boolean constantCaptchaControlEnabled;
	private boolean chooseRegionMode;

	private Pattern oldCSACbCodesPattern;

	private boolean accessToBusinessEnvironment;
	private String businessEnvironmentMainURL;
	private String businessEnvironmentUserURL;
	private Properties businessEnvironmentVerifyStateURLMap;
	private String restrictionMessagePropertyName;
	private RegistrationAccessType registrationAccessType;
	private String promoterDomainName;
	private long registrationFinishCaptchaAttempts;
	private long registrationFinishCaptchaTime;
	private boolean isAsyncCheckingFieldsEnabled;
	private boolean isPromoBlockEnabled;
	private int minimumLoginLength;
	private boolean showPopupAfterRegistration;

	private String uecWebSiteURL;

	private boolean smsMessageLogAvailable;
	private boolean checkImsiMessageLogAvailable;
	private boolean updateClientMessageLogAvailable;
	private boolean updateResourceMessageLogAvailable;
	private boolean confirmProfileMessageLogAvailable;
	private boolean feeResultMessageLogAvailable;

	private boolean queueOptimizedMode;

	private long commonCaptchaDelay;
	private long captchaBlockRulingStoppingDelay;

	private long trustedGuestCaptchaDelay;
	private long untrustedGuestCaptchaDelay;

	private boolean guestEntryMode;                         //����� ��������� �����
	private long guestEntryPhonesLimit;                     //����� ��������� ���������� ����� �� Ip-����� ��� ��������� �����
	private long guestEntryPhonesLimitMax;
	private long guestEntryPhonesLimitCooldown;
	private long daysClaimsAreShown;
	private boolean guestCapthcaActive = true;

	private boolean pixelMetricActivity;                    //���������� DMP-Pixel � CSAFront


	public CSAFrontConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		accessInternalRegistration = getBoolProperty(ACCESS_INTERNAL_REGISTRATION_PROPERTY_NAME);
		accessExternalRegistration = getBoolProperty(ACCESS_EXTERNAL_REGISTRATION_PROPERTY_NAME);
		accessRecoverPassword = getBoolProperty(ACCESS_RECOVER_PASSWORD_PROPERTY_NAME);
		useCaptchaRestrict = getBoolProperty(USE_CAPTCHA_RESTRICT_PROPERTY_NAME);
		constantCaptchaControlEnabled = getBoolProperty(CAPTCHA_CONTROL_ENABLED_PROPERTY_NAME);
		chooseRegionMode =  getBoolProperty(CHOOSE_REGION_MODE_PROPERTY_NAME);
		oldCSACbCodesPattern = getOldCSACbCodesPattern(getProperty(OLD_CSA_CBCODES_PROPERTY_NAME));
		accessToBusinessEnvironment = getBoolProperty(BUSINESS_ENVIRONMENT_ACCESS_PROPERTY_NAME);
		businessEnvironmentMainURL = getProperty(BUSINESS_ENVIRONMENT_MAINURL_PROPERTY_NAME);
		businessEnvironmentUserURL = getProperty(BUSINESS_ENVIRONMENT_USERURL_PROPERTY_NAME);
		businessEnvironmentVerifyStateURLMap = getProperties(BUSINESS_ENVIRONMENT_URL_MAP_PROPERTY_NAME_PREFIX);
		restrictionMessagePropertyName = getProperty(RESTRICTION_MESSAGE_NAME);
		uecWebSiteURL = getProperty(UEC_WEBSITE_URL_PROPERTY_NAME);
		registrationAccessType =  RegistrationAccessType.valueOf(getProperty(RESTRICTION_ACCESS_TYPE_PROPERTY_NAME));
		promoterDomainName = getProperty(PROMOTER_DOMAIN_NAME);
		registrationFinishCaptchaAttempts = getLongProperty(REGISTRATION_CAPTCHA_ATTEMPTS);
		registrationFinishCaptchaTime = getLongProperty(REGISTRATION_CAPTCHA_TIME);
		isAsyncCheckingFieldsEnabled = getBoolProperty(AJAX_FIELDS_CHECK_ENABLED);
		isPromoBlockEnabled = getBoolProperty(PROMO_BLOCK_ENABLED);
		minimumLoginLength = getIntProperty(LOGIN_MINIMUM_LENGTH);
		showPopupAfterRegistration = getBoolProperty(SHOW_POPUP_AFTER_REGISTRATION);

		smsMessageLogAvailable = getBoolProperty(SMS_MESSAGE_LOG_AVAILABLE_PROPERTY_NAME);
		checkImsiMessageLogAvailable = getBoolProperty(CHECK_IMSI_MESSAGE_LOG_AVAILABLE_PROPERTY_NAME);
		updateClientMessageLogAvailable = getBoolProperty(UPDATE_CLIENT_MESSAGE_LOG_AVAILABLE_PROPERTY_NAME);
		updateResourceMessageLogAvailable = getBoolProperty(UPDATE_RESOURCE_MESSAGE_LOG_AVAILABLE_PROPERTY_NAME);
		confirmProfileMessageLogAvailable = getBoolProperty(CONFIRM_PROFILE_MESSAGE_LOG_AVAILABLE_PROPERTY_NAME);
		feeResultMessageLogAvailable = getBoolProperty(FEE_RESULT_MESSAGE_LOG_AVAILABLE_PROPERTY_NAME);

		queueOptimizedMode = getBoolProperty("com.rssl.iccs.ermb.queueOptimizedMode");

		commonCaptchaDelay = getLongProperty(COMMON_CAPTCHA_DELAY);
		captchaBlockRulingStoppingDelay = getLongProperty(CAPTCHA_RULE_STOPPING_MINIMAL_DELAY);

		guestEntryMode        = getBoolProperty(GUEST_ENTRY_MODE);
		guestEntryPhonesLimit = getLongProperty(GUEST_ENTRY_PHONES_LIMIT);
		guestEntryPhonesLimitMax = getLongProperty(GUEST_ENTRY_PHONES_LIMIT_MAX);
		guestEntryPhonesLimitCooldown = getLongProperty(GUEST_ENTRY_PHONES_LIMIT_COOLDOWN);
		guestCapthcaActive = getBoolProperty(GUEST_ENTRY_GUEST_CAPTCHA, true);

		daysClaimsAreShown = getLongProperty(GUEST_ENTRY_CLAIMS_SHOW_PERIOD);

		trustedGuestCaptchaDelay = getLongProperty(COMMON_GUEST_CAPTCHA_DELAY);
		untrustedGuestCaptchaDelay = getLongProperty(UNTRUSTED_GUEST_CAPTCHA_DELAY);

		pixelMetricActivity = getBoolProperty(PIXEL_METRIC_ACTIVITY);
	}

	private Pattern getOldCSACbCodesPattern(String pattern)
	{
		if(AUTH_GLOBAL_OLD_CSA_KEY_WORD.equals(pattern))
			return TRUE_PATTERN;

		if(StringHelper.isEmpty(pattern))
			return FALSE_PATTERN;

		String modifyPattern = "(" + pattern.replaceAll(" ", "")
				.replaceAll(",", ")|(")
				.replaceAll("\\?", ".{1}")
				.replaceAll("\\*", ".*") + ")";

		return Pattern.compile(modifyPattern);
	}

	/**
	 * ����������� ��������������� ����������� c ���������� ������
	 * @return true - ��������������� ����������� ��������
	 */
	public boolean isAccessInternalRegistration()
	{
		return accessInternalRegistration;
	}

	/**
	 * ����������� ��������������� ����������� c ������� ������
	 * @return true - ��������������� ����������� ��������
	 */
	public boolean isAccessExternalRegistration()
	{
		return accessExternalRegistration;
	}

	/**
	 * ����������� ���������������� �������������� ������
	 * @return true - �������������� ������ ��������
	 */
	public boolean isAccessRecoverPassword()
	{
		return accessRecoverPassword;
	}

	/**
	 * @return ������ CbCode ��� �������������� ����� ������ ���
	 */
	public Pattern getOldCSACbCodesPattern()
	{
		return oldCSACbCodesPattern;
	}

	/**
	 * ���������� ����������� �����
	 * @return true - ����� ��������
	 */
	public boolean isUseCaptchaRestrict()
	{
		return useCaptchaRestrict;
	}

	/**
	 * ���������� �������� ����� (� ������ ��������� useCaptchaRestrict)
	 * @return true - ����� �������� ������ (� ����� ����� ���������� ��������� �����)
	 */
	public boolean isConstantCaptchaControlEnabled()
	{
		return constantCaptchaControlEnabled;
	}

	/**
	 * @return �������� ��� ������� �����
	 */
	public String getBusinessEnvironmentMainURL()
	{
		return businessEnvironmentMainURL;
	}

	/**
	 * @return ��� ������� ������� ������� �����
	 */
	public String getBusinessEnvironmentUserURL()
	{
		return businessEnvironmentUserURL;
	}

	/**
	 * @return true -- ������������ ���������� ����������� ������ ��� ������� �����
	 */
	public boolean isAccessToBusinessEnvironment()
	{
		return accessToBusinessEnvironment;
	}

	/**
	 * @param state ��������� �����������
	 * @return ��� �������� ����� �����������
	 */
	public String getBusinessEnvironmentVerifyStateURL(VerificationState state)
	{
		String key = BUSINESS_ENVIRONMENT_URL_MAP_PROPERTY_NAME_PREFIX.concat(state.name());
		return businessEnvironmentVerifyStateURLMap.getProperty(key);
	}

	/**
	 * ����� ������ �������
	 * @return true/false(�������������������/���������������)
	 */
	public boolean isChooseRegionMode()
	{
		return chooseRegionMode;
	}

	public String getRestrictionMessage()
	{
		return restrictionMessagePropertyName;
	}

	/**
	 * @return ��� �����������(�� ��������� ��������, ������������ ������)
	 */
	public RegistrationAccessType getRegistrationAccessType()
	{
		return registrationAccessType;
	}


	/**
	 * ���������� ����� ���
	 * @return ����� ����� ���
	 */
	public String getUECWebSiteUrl()
	{
		return uecWebSiteURL;
	}

	/**
	 * ���������� �������� ������ ��� ��� �� ������ ����������
	 * @return
	 */
	public String getPromoterDomainName()
	{
		return promoterDomainName;
	}

	/**
	 * @return ���������� ������� ����� ���������� ����� �� ��������� ���� ��������������� �����������
	 */
	public Long getRegistrationFinishCaptchaAttempts()
	{
		return registrationFinishCaptchaAttempts;
	}

	/**
	 * @return �����, �� ��������� �������� ����� ������������ �� ��������� ���� ��������������� �����������
	 */
	public Long getRegistrationFinishCaptchaTime()
	{
		return registrationFinishCaptchaTime;
	}

	/**
	 * @return �������� �� ��������� ����������� �������� �� ��������� �����
	 */
	public boolean getAsyncCheckingFieldsIsEnabled()
	{
		return isAsyncCheckingFieldsEnabled;
	}

	public boolean isPromoBlockEnabled()
	{
		return isPromoBlockEnabled;
	}

	public int getMinimumLoginLength()
	{
		return minimumLoginLength;
	}

	 public boolean getShowPopupAfterRegistration()
	 {
		 return showPopupAfterRegistration;
	 }

	public static boolean PromoBlockEnabled()
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.isPromoBlockEnabled();
	}

	public static boolean asyncCheckingFieldsIsEnabled()
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.getAsyncCheckingFieldsIsEnabled();
	}

	public static boolean showPopupAfterRegistrationEnabled()
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.getShowPopupAfterRegistration();
	}

	/**
	 * @return �������� �� ����������� ��� ���������
	 */
	public boolean isSmsMessageLogAvailable()
	{
		return smsMessageLogAvailable;
	}

	/**
	 * @return �������� �� ����������� ��� ��������� � ��������� IMSI
	 */
	public boolean isCheckImsiMessageLogAvailable()
	{
		return checkImsiMessageLogAvailable;
	}

	/**
	 * @return �������� �� ����������� ��������� �� ���������� �������
	 */
	public boolean isUpdateClientMessageLogAvailable()
	{
		return updateClientMessageLogAvailable;
	}

	/**
	 * @return �������� �� ����������� ��������� �� ���������� ��������� �������
	 */
	public boolean isUpdateResourceMessageLogAvailable()
	{
		return updateResourceMessageLogAvailable;
	}

	/**
	 * @return �������� �� ����������� ��������� �� ������������� ���������� �������
	 */
	public boolean isConfirmProfileMessageLogAvailable()
	{
		return confirmProfileMessageLogAvailable;
	}

	/**
	 * @return �������� �� ����������� ��������� �� ������������� �������� ����������� �����
	 */
	public boolean isFeeResultMessageLogAvailable()
	{
		return feeResultMessageLogAvailable;
	}

	/**
	 * ������ "����� ������������ ������������� ����-��������"
	 * @return ���� ������, ��������� ������� ������������ ��� ������� ���� ��������� ����
	 * ���� ������, ��������� ������� ������������ ��� ������� ����-������
	 */
	public boolean isQueueOptimizedMode()
	{
		return queueOptimizedMode;
	}

	public long getGuestEntryPhonesLimit()
	{
		return Math.min(guestEntryPhonesLimit, getGuestEntryPhonesLimitMax());
	}

	public long getGuestEntryPhonesLimitCooldown()
	{
		return guestEntryPhonesLimitCooldown;
	}

	public long getGuestEntryPhonesLimitMax()
	{
		return guestEntryPhonesLimitMax;
	}

	public boolean isGuestEntryMode()
	{
		return guestEntryMode;
	}

	public long getDaysClaimsAreShown()
	{
		return daysClaimsAreShown;
	}

	public long getCaptchaBlockRulingStoppingDelay()
	{
		return captchaBlockRulingStoppingDelay;
	}

	public long getCommonCaptchaDelay()
	{
		return commonCaptchaDelay;
	}

	public long getTrustedGuestCaptchaDelay()
	{
		return trustedGuestCaptchaDelay;
	}

	public long getUntrustedGuestCaptchaDelay()
	{
		return untrustedGuestCaptchaDelay;
	}

	public boolean isPixelMetricActivity()
	{
		return pixelMetricActivity;
	}

	public void setPixelMetricActivity(boolean pixelMetricActivity)
	{
		this.pixelMetricActivity = pixelMetricActivity;
	}

	/**
	 * @return ���������� ��� ������ ��� ��������� �����.
	 */
	public boolean isGuestCapthcaActive()
	{
		return guestCapthcaActive;
	}
}
