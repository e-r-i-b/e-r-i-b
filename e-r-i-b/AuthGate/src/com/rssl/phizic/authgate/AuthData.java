package com.rssl.phizic.authgate;

import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.common.types.client.CSAType;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.common.types.csa.MigrationState;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.security.RegistrationStatus;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.TreeMap;

/**
 * ����� � ������� ��������������, ��� �������������� ����� ���.
 * @author egorova
 * @ created 20.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class AuthData
{
	private String SID;                                 //���������� ������������� ���������������� ������ � ���.
	private String ASPKey;
	private boolean isMoveSession;                      //������� ��������� ������.
	private String userId;                              //����� ������������ (��, ��� ���� ������������).
	private String userAlias;                           //�����/����� ������������.
	private boolean MB;                                 //������� ����������� � ���������� �����.
	private String CB_CODE;                             //�����������, � �������� ��������� �����, �� ������� ����� ������
	private String PAN;                                 //����� �����, �� ������� ����� ������
	private String lastName;                            //������� ������������.
	private String firstName;                           //��� ������������.
	private String middleName;                          //�������� ������������.
	private String document;                            //����� (����� � �����) ��������� ��������������� ��������.
	private String series;                              //����� ��������� ��������������� ��������.
	private String birthDate;                           //���� ��������.
	private boolean cameFromYoungWebsite = false;       //������� ����, ��� ������������ ������ � ���������� �����
	private String documentType;                        //��� ��������� ��������������� ��������.
	private Calendar lastLoginDate;                     //���� ���������� �����.
	private LoginType loginType;                        //��� ������ (������� ����, IPAS, mAPI).
	private boolean expiredPassword = false;            //������� ����������� ������
	private String csaGuid;                             //guid ���������� � ����� ���
	private CSAType csaType;                            //��� ������������ ���
	private MobileAppScheme mobileAppScheme;            //����� ���������� ����������
	private RegistrationStatus registrationStatus;      //������ ����������� �������
	private RegistrationType registrationType;          //��� ����������� �������
	private String deviceId;                            //������������� ����������
	private String deviceInfo;                          //��� ����������
	private String mobileAppVersion;                    //������ ���������� ����������
	private AuthorizedZoneType authorizedZoneType;      //��� ���� ����� ������������
	private SecurityType securityType;                  //������� ������������
	private MigrationState migrationState;              //��������� �������� ������ �� ����������(���������) � �������� ����
	private ProfileType profileType;                    //��� �������
	private Long csaProfileId;                          //Id ������� CSA. ������������ ������ ��� ����������� ����������� ��������.
														// ������ ������������ ��� ������������� ������������, �.�. ����� ���������� ��� ������� ��������
	private boolean mobileCheckout;
	private String browserInfo;
	// ������ � ������ ��������� �����
	private String guestPhone;
	private Long guestCode;
	private Long guestProfileId;

	private TreeMap<String, String> rsaData = new TreeMap<String, String>();//������ �� ���������� ������������ ��� �������� � ������� RSA

	public String getSID()
	{
		return SID;
	}

	public void setSID(String SID)
	{
		this.SID = SID;
	}

	public String getASPKey()
	{
		return ASPKey;
	}

	public void setASPKey(String ASPKey)
	{
		this.ASPKey = ASPKey;
	}

	public boolean isMoveSession()
	{
		return isMoveSession;
	}

	public void setMoveSession(boolean moveSession)
	{
		isMoveSession = moveSession;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getUserAlias()
	{
		if (StringHelper.isEmpty(userAlias))
			return userId;
		
		return userAlias;
	}

	public void setUserAlias(String userAlias)
	{
		this.userAlias = userAlias;
	}

	public boolean isMB()
	{
		return MB;
	}

	public void setMB(boolean MB)
	{
		this.MB = MB;
	}

	public String getCB_CODE()
	{
		return CB_CODE;
	}

	public void setCB_CODE(String CB_CODE)
	{
		this.CB_CODE = CB_CODE;
	}

	public String getPAN()
	{
		return PAN;
	}

	public void setPAN(String PAN)
	{
		this.PAN = PAN;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public String getDocument()
	{
		return document;
	}

	public void setDocument(String document)
	{
		this.document = document;
	}

	public String getSeries()
	{
		return series;
	}

	public void setSeries(String series)
	{
		this.series = series;
	}

	public String getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(String birthDate)
	{
		this.birthDate = birthDate;
	}

	public boolean isCameFromYoungWebsite()
	{
		return cameFromYoungWebsite;
	}

	public void setCameFromYoungWebsite(boolean cameFromYoungWebsite)
	{
		this.cameFromYoungWebsite = cameFromYoungWebsite;
	}

	public String getDocumentType()
	{
		return documentType;
	}

	public void setDocumentType(String documentType)
	{
		this.documentType = documentType;
	}

	public LoginType getLoginType()
	{
		return loginType;
	}

	public void setLoginType(LoginType loginType)
	{
		this.loginType = loginType;
	}

	public Calendar getLastLoginDate()
	{
		return lastLoginDate;
	}

	public void setLastLoginDate(Calendar lastLoginDate)
	{
		this.lastLoginDate = lastLoginDate;
	}

	public boolean isExpiredPassword()
	{
		return expiredPassword;
	}

	public void setExpiredPassword(boolean expiredPassword)
	{
		this.expiredPassword = expiredPassword;
	}

	public String getCsaGuid()
	{
		return csaGuid;
	}

	public void setCsaGuid(String csaGuid)
	{
		this.csaGuid = csaGuid;
	}

	public CSAType getCsaType()
	{
		return csaType;
	}

	public void setCsaType(CSAType csaType)
	{
		this.csaType = csaType;
	}

	public MobileAppScheme getMobileAppScheme()
	{
		return mobileAppScheme;
	}

	public void setMobileAppScheme(MobileAppScheme mobileAppScheme)
	{
		this.mobileAppScheme = mobileAppScheme;
	}

	public RegistrationStatus getRegistrationStatus()
	{
		return registrationStatus;
	}

	public void setRegistrationStatus(RegistrationStatus registrationStatus)
	{
		this.registrationStatus = registrationStatus;
	}

	public RegistrationType getRegistrationType()
	{
		return registrationType;
	}

	public void setRegistrationType(RegistrationType registrationType)
	{
		this.registrationType = registrationType;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	public String getDeviceInfo()
	{
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo)
	{
		this.deviceInfo = deviceInfo;
	}

	public String getMobileAppVersion()
	{
		return mobileAppVersion;
	}

	public void setMobileAppVersion(String mobileAppVersion)
	{
		this.mobileAppVersion = mobileAppVersion;
	}

	public AuthorizedZoneType getAuthorizedZoneType()
	{
		return authorizedZoneType;
	}

	public void setAuthorizedZoneType(AuthorizedZoneType authorizedZoneType)
	{
		this.authorizedZoneType = authorizedZoneType;
	}

	public SecurityType getSecurityType()
	{
		return securityType;
	}

	public void setSecurityType(SecurityType securityType)
	{
		this.securityType = securityType;
	}

	public Long getCsaProfileId()
	{
		return csaProfileId;
	}

	public void setCsaProfileId(Long csaProfileId)
	{
		this.csaProfileId = csaProfileId;
	}

	public MigrationState getMigrationState()
	{
		return migrationState;
	}

	public void setMigrationState(MigrationState migrationState)
	{
		this.migrationState = migrationState;
	}

	public ProfileType getProfileType()
	{
		return profileType;
	}

	public void setProfileType(ProfileType profileType)
	{
		this.profileType = profileType;
	}

	public String getBrowserInfo()
	{
		return browserInfo;
	}

	public void setBrowserInfo(String browserInfo)
	{
		this.browserInfo = browserInfo;
	}

	public String getGuestPhone()
	{
		return guestPhone;
	}

	public void setGuestPhone(String guestPhone)
	{
		this.guestPhone = guestPhone;
	}

	public Long getGuestCode()
	{
		return guestCode;
	}

	public void setGuestCode(Long guestCode)
	{
		this.guestCode = guestCode;
	}

	/**
	 * @return ������������� ��������� �������
	 */
	public Long getGuestProfileId()
	{
		return guestProfileId;
	}

	/**
	 * @param guestProfileId ������������� ��������� �������
	 */
	public void setGuestProfileId(Long guestProfileId)
	{
		this.guestProfileId = guestProfileId;
	}

	/**
	 * @param mobileCheckout - ������� ����, ��� ���� ������ ������, ������������ ������ ��� mobileCheckout
	 */
	public void setMobileCheckout(boolean mobileCheckout)
	{
		this.mobileCheckout = mobileCheckout;
	}

	/**
	 * @return - ������� ����, ��� ���� ������ ������, ������������ ������ ��� mobileCheckout
	 */
	public boolean isMobileCheckout()
	{
		return mobileCheckout;
	}

	public TreeMap<String, String> getRsaData()
	{
		return rsaData;
	}
}
