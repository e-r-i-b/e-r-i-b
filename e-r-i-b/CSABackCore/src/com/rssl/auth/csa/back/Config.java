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
	 * ������������ ��������� ��������� ������ � �������������
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


	private long guestEntrySMSConfirmationAttempts;                        //���������� ������� ������������� ������ ���-������
	private int guestEntryAttempts;                  //���������� ������� ��������� ����� �� ��������
	private int guestEntryAttemptsLimitsMax;                    //������������ �������� ������ �� ������� ��������� ����� (������ �� ������ guestEntryAttempts)
	private long guestEntrySMSCooldown;                     //������ ������ ����������� �� �������� ��� (� ��������)

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
	 * @return URL ����� ������� iPas.
	 */
	public String getIPasURL()
	{
		return iPasURL;
	}

	/**
	 * @return URL ����� ������� ����.
	 */
	public String getERIBURL()
	{
		return eribURL;
	}

	/**
	 * @return ��������� �� ��������� �����������. ���� ����������� ��������� ���������� �����.
	 * ���� ��������� ���������� ���������� ������ ������ ���������������� �������.  
	 */
	public boolean isDenyMultipleRegistration()
	{
		return denyMultipleRegistration;
	}

	/**
	 * @return ��������� �� ����������� �������, ���� �� ����� ������ � ����
	 */
	public boolean isDenyExistEnteredRegistration()
	{
		return denyExistEnteredRegistration;
	}

	/**
	 * @return ������� �������� �������������. ����� ��������� ��� ������������� �� �����������.
	 */
	public int getConfirmationTimeout()
	{
		return confirmationTimeout;
	}

	/**
	 * @return ������� ��������� ���������� � �������� ����� ���������� ������������� getMaxAuthenticationFailed ���.
	 */
	public int getAuthenticationFailedBlockingTimeOut()
	{
		return authenticationFailedBlockingTimeOut;
	}

	/**
	 * @return ���������� ��������� ������� �������������, ����� �������� ����� ����������� ��������� ����������
	 */
	public long getMaxAuthenticationFailed()
	{
		return maxAuthenticationFailed;
	}

	/**
	 * ������� �����������. �� ��������� ����� �������, � ����������� �� ��������� ������ ����� ��������.
	 * @return ������� �����������
	 */
	public int getRegistrationTimeout()
	{
		return registrationTimeout;
	}

	/**
	 * ������� �������������� ������. �� ��������� ����� �������, � ������������� ������ �� ��������� ������ ����� ��������.
	 * @return ������� �������������� ������
	 */
	public int getRestorePasswordTimeout()
	{
		return restorePasswordTimeout;
	}

	/**
	 * ������� ����������� ���������� ����������. �� ��������� ����� �������, � ����������� �� ��������� ������ ����� ��������.
	 * @return ������� �����������
	 */
	public int getMobileRegistrationTimeout()
	{
		return mobileRegistrationTimeout;
	}

	/**
	 * @return ������������ ���������� ���������� ������������������ ��������� �� �������
	 */
	public int getMaxMobileConnectorsCount()
	{
		return maxMobileConnectorsCount;
	}

	/**
	 * @return ����������� ���������� ���������� ���������������� �������� �� ����������� �� getMobileRegistrationRequestCheckInterval ���
	 */
	public int getMaxMobileRegistrationRequestCount()
	{
		return maxMobileRegistrationRequestCount;
	}

	/**
	 * @return ���������� ������ (������� ������, ������� �� �������� �������) ��� �������� ���������� ���������������� �������� �� �����������
	 */
	public int getMobileRegistrationRequestCheckInterval()
	{
		return mobileRegistrationRequestCheckInterval;
	}

	/**
	 * @return ������������ ����� ���� ������ � �����.
	 */
	public int getSessionTimeout()
	{
		return sessionTimeout;
	}

	/**
	 * @return ����� ��� ���� �������������
	 */
	public int getConfirmCodeLength()
	{
		return confirmCodeLength;
	}

	/**
	 * @return ������ ���������� �������� ��� ���� �������������
	 */
	public String getConfirmCodeAllowedChars()
	{
		return confirmCodeAllowedChars;
	}

	/**
	 * @return ����� ���������������� ������ ��� ������� � ����
	 */
	public int getGeneratedPasswordLength()
	{
		return generatedPasswordLength;
	}

	/**
	 * @return ��������� ���������� �������� ����������������� ������ ��� ������� � ����
	 */
	public String getGeneratedPasswordAllowedChars()
	{
		return generatedPasswordAllowedChars;
	}

	/**
	 * ������� ������������� �� ������������ ����� ����� ��������� �� ������ �������� ������ � ���������� ���������� �� ������.
	 * �� ��������� ����� �������, ������������� �� ��������� ������ ����� ��������.
	 * @return ������� �������������� � ��������
	 */
	public int getAuthenticationTimeout()
	{
		return authenticationTimeout;
	}

	/**
	 * @return ���������� ��������� ��� �������������, �������� ������� ��������� �����������. Null, ���� ������� ���
	 */
	public Pattern getUserRegistrationCbCodeDenyPattern()
	{
		return userRegistrationCbCodeDenyPattern;
	}

	/**
	 * @return ���������� ��������� ��� �������������, �������� ������� ��������� �������������� ������. Null, ���� ������� ��� 
	 */
	public Pattern getRestorePasswordCbCodeDenyPattern()
	{
		return restorePasswordCbCodeDenyPattern;
	}


	/**
	 * @return ����������� ���������� ���������� ���������������� �������� �� ����������� �� getUserRegistrationRequestCheckInterval ���
	 */
	public int getMaxUserRegistrationRequestCount()
	{
		return maxUserRegistrationRequestCount;
	}

	/**
	 * @return ���������� ������ ����� ��������� ��� �������� ���������������� �������� �� �����������
	 */
	public int getUserRegistrationRequestCheckInterval()
	{
		return userRegistrationRequestCheckInterval;
	}

	/**
	 * @return ����� �������� ������ � ������������� ��� CsaBack
	 */
	public int getIPasCsaBackTimeout()
	{
		return iPasCsaBackTimeout;
	}

	/**
	 * @return ����� �������� ������ �� ���� � ������������� ��� CsaBack
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
	 * @return ��������� �� ������������� ����� ����������� ��������������(�� ������ �� IPas)
	 */
	public boolean isPostAuthenticationSyncAllowed()
	{
		return postAuthenticationSyncAllowed;
	}
	/**
	 * @return ���������� ������� �������������
	 */

	public int getConfirmationAttemptsCount()
	{
		return confirmationAttemptsCount;
	}

	/**
	 * @return ��� �� ������� �������� ������ ������� �����
	 */
	public String getBusinessEnvironmentURL()
	{
		return businessEnvironmentURL;
	}

	/**
	 * @return SOAPAction ������� �����
	 */
	public String getBusinessEnvironmentSOAPAction()
	{
		return businessEnvironmentSOAPAction;
	}

	/**
	 * @return ��������� �� ��������� ������ iPas.
	 */
	public boolean isIPasPasswordStoreAllowed()
	{
		return iPasPasswordStoreAllowed;
	}

	/**
	 * @return ��������� �� �������������� � iPas.
	 */
	public boolean isIPasAuthenticationAllowed()
	{
		return iPasAuthenticationAllowed;
	}

	/**
	 * @return ��� ��������� ��������� ������ � �������������
	 */
	public UserInfoProviderName getUserInfoProvider()
	{
		return userInfoProvider;
	}

	/**
	 * @return true - ��������� �� ����� ���������� ������� � WAY4 ������ ������ � ���(BUG089795: [ISUP] ����������� ��������� ��� ���������� ��������� � ��).
	 */
	public boolean getWAY4UWorkaround089795()
	{
		return way4uWorkaround089795;
	}

	/**
	 * @return true - ��������� ���� �� ���������� ���������� ����� ������� �����������
	 */
	public boolean isPostRegistrationLogin()
	{
		return postRegistrationLogin;
	}

	/**
	 * @return true - ��������� ��������������� �� ������ ������ ���
	 */
	public boolean isAccessAutoRegistration()
	{
		return accessAutoRegistration;
	}

	/**
	 * @return ���������� ������� ������������� ������ ���-������
	 */
	public long getGuestEntrySMSConfirmationAttempts()
	{
		return guestEntrySMSConfirmationAttempts;
	}

	/**
	 * @return ������ ������ ����������� �� �������� ��� (� ��������)
	 */
	public long getGuestEntrySMSCooldown()
	{
		return guestEntrySMSCooldown;
	}

	/**
	 * @return ���������� ������� ��������� ����� �� ��������
	 */
	public int getGuestEntryAttempts()
	{
		return Math.min(guestEntryAttempts, guestEntryAttemptsLimitsMax);
	}

	/**
	 * @return ������������ �������� ������ �� ������� ��������� ����� (������ �� ������ guestEntryGlobalSMSCount )
	 */
	public long getGuestEntryAttemptsLimitsMax()
	{
		return guestEntryAttemptsLimitsMax;
	}

	/**
	 * @return true - ������� ����� StandIn
	 */
	public boolean isStandInMode()
	{
		return standInMode;
	}
}
