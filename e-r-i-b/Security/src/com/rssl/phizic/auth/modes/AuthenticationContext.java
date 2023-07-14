package com.rssl.phizic.auth.modes;

import com.rssl.auth.csa.back.servises.GuestClaimType;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.client.CSAType;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.common.types.csa.MigrationState;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.RegistrationStatus;
import com.rssl.phizic.security.crypto.Certificate;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.io.Serializable;
import java.util.*;

import static com.rssl.phizic.common.types.security.Constants.AUTHENTICATION_CONTEXT_KEY;

/**
 * @author Evgrafov
 * @ created 12.12.2006
 * @ $Author: niculichev $
 * @ $Revision: 74362 $
 */
public class AuthenticationContext implements Serializable
{
	private final Map<String, String> authenticationParameters = new HashMap<String, String>();
	private CommonLogin         login;
	private Person              person; // ��������� �������
	private final AccessPolicy  policy;
	private UserVisitingMode    visitingMode = UserVisitingMode.BASIC;
	private String              randomString;
	private Certificate         certificate;
	private Integer             passwordNumber;
	private Properties          policyProperties;
	private String              CSA_SID;
	private boolean             isMoveSession;
	private boolean             MB;
	private String              UserId;
	private String              userAlias;
	private String              CB_CODE;
	private String              TB;
	private String              PAN;
	private Long                csaProfileId;
	private Long                guestProfileId;
	private boolean             updatedEntity;
	private List<Long>          personIds;
	private String              lastName;
	private String              firstName;
	private String              middleName;
	private String              documentNumber;
    private String              birthDate;
	private boolean             cameFromYoungPeopleWebsite = false;
	private String              startJobPagePath;
	private boolean             expiredPassword = false;
	private String              csaGuid;                                    //guid ��������� ���, ��� ������� ����� ������
	private CSAType             csaType;                                    //��� ������������ ��� ��� �����
	private LoginType           loginType;                                  //��� ������
	private RegistrationStatus  registrationStatus;                         //������ ����������� �������

	private VersionNumber       clientMobileAPIVersion = null;
	//��� ��������� � ���������� �������� ���������� ������������� ����������� �������
	private boolean             platformPasswordConfirm;
	private final Object        MESSAGES_LOCKER = new Object();
	private final Set<String>   messages = new LinkedHashSet<String>();
	private final Object        INACTIVE_ES_MESSAGE_LOCKER = new Object();
	private final Set<String>   inactiveESMessage = new LinkedHashSet<String>();
	private MobileAppScheme     mobileAppScheme;
	private String              deviceId;                                   //������������� ����������
	private String deviceInfo;                                              //��� ����������
	private boolean checkedCEDBO = false;
	private MigrationState      migrationState;                             //��������� �������� ������ �� ����������(���������) � �������� ����
	private ProfileType         profileType;                                //��� �������
	private Calendar            lastLoginDate;                              //���� ���������� ����� �� �������� ������
	private boolean             needReloadProducts = true;                  //������� ������������� ���������� ��������� ��� ����� �������
	private String              browserInfo;
	private SecurityType        securityType;
	private AuthorizedZoneType authorizedZoneType;
	private GuestClaimType      guestClaimType;


	/**
	 * ���������� �������� �������������� ������� ������
	 * @return ������� ��������
	 */
	public static AuthenticationContext getContext()
	{
		Store store = StoreManager.getCurrentStore();
		if (store == null)
			throw new IllegalStateException("��� ������");
		return (AuthenticationContext) store.restore(AUTHENTICATION_CONTEXT_KEY);
	}

	/**
	 * ������������� �������� �������������� � ������� ������
	 * @param context - �������� ��������������
	 */
	public static void setContext(AuthenticationContext context)
	{
		Store store = StoreManager.getCurrentStore();
		if (store == null)
			throw new IllegalStateException("��� ������");
		store.save(AUTHENTICATION_CONTEXT_KEY, context);
	}

	/**
	 * ������� �������� �������������� �� ������� ������
	 */
	public static void removeContext()
	{
		Store store = StoreManager.getCurrentStore();
		if (store == null)
			throw new IllegalStateException("��� ������");
		store.remove(AUTHENTICATION_CONTEXT_KEY);
	}

	/**
	 * ���������� �������� ��������������
	 * @param key - ���� ���������
	 * @return �������� ���������
	 */
	public String getAuthenticationParameter(String key)
	{
		return authenticationParameters.get(key);
	}

	/**
	 * @return ��������� ��������������
	 */
	public Map<String, String> getAuthenticationParameters()
	{
		return authenticationParameters;
	}

	/**
	 * �������� ��������� ��������������
	 */
	public void clearAuthenticationParameters()
	{
		authenticationParameters.clear();
	}

	public boolean isAuthGuest()
	{
		return visitingMode == UserVisitingMode.GUEST;
	}

	public void setAuthenticationParameters(Map<String, String> parameters)
	{
		authenticationParameters.clear();
		authenticationParameters.putAll(parameters);
	}

	/**
	 * ������ �������� ���������������
	 * ��� ��������� ��� ���� �� ��� ����
	 * @param name  ���� ���������
	 * @param parameter �������� ���������
	 */
	public void addOrUpdateAuthenticationParameter(String name,String parameter)
	{
		authenticationParameters.put(name,parameter);
	}

	/*�������� ����������� � ���������� �����, ����������� �� ������� � Way4*/
	public boolean isMB()
	{
		return MB;
	}

	public void setMB(boolean MB)
	{
		this.MB = MB;
	}

	public String getTB()
	{
		return TB;
	}

	public void setTB(String TB)
	{
		this.TB = TB;
	}

	/**
	 * @return ����� ������������, �� �������� ��� ����������� ����
	 */
	public String getUserId()
	{
		return UserId;
	}

	public void setUserId(String userId)
	{
		UserId = userId;
	}

	/**
	 * @return �����/����� ������������.
	 */
	public String getUserAlias()
	{
		return userAlias;
	}

	public void setUserAlias(String userAlias)
	{
		this.userAlias = userAlias;
	}

	/**
	 * @return ������ ���� ���������������� �����, ����������� �� ������� � Way4
	 */
	public String getCB_CODE()
	{
		return CB_CODE;
	}

	public void setCB_CODE(String CB_CODE)
	{
		this.CB_CODE = CB_CODE;
	}

	/**
	 * @return ����� ����� ������������, ��� ������� ������� ����������� ������
	 */
	public String getPAN()
	{
		return PAN;
	}

	/**
	 * @param PAN - ����� ����� ������������, ��� ������� ������� ����������� ������
	 */
	public void setPAN(String PAN)
	{
		this.PAN = PAN;
	}

	/**
	 * ������������ ������� ����, ��� ������ �� ��������� � ���� �� ������ �������
	 * ����� ��� ���. ������ ������������ � ��� �� ���������� ����.
	 * � ������ ���� �������� true, ���. ����������� ���-������� �� �������������.
	 * @return �������� ��������� �������� ������� �� ������ ���������� �� ����
	 */
	public boolean isMoveSession()
	{
		return isMoveSession;
	}

	/**
	 * ��������� �������� �������� ������� �� ������ ����������
	 * @param moveSession ������� ��������.
	 */
	public void setMoveSession(boolean moveSession)
	{
		isMoveSession = moveSession;
	}

	/**
	 *
	 * @return ����� ������ �� ����� �����
	 */
	public Integer getPasswordNumber()
	{
		return passwordNumber;
	}

	/**
	 *
	 * @param passwordNumber - ����� ������
	 */
	public void setPasswordNumber(Integer passwordNumber)
	{
		this.passwordNumber = passwordNumber;
	}

	/**
	 * @param policy ��������
	 */
	public AuthenticationContext(AccessPolicy policy)
	{
		this.policy = policy;
	}

	/**
	 * @return �����
	 */
	public CommonLogin getLogin()
	{
		return login;
	}

	/**
	 * @param login �����
	 */
	public void setLogin(CommonLogin login)
	{
		this.login = login;
	}


	/**
	 * @return ����� ��������������
	 */
	public AccessPolicy getPolicy()
	{
		return policy;
	}

	/**
	 * @return ����� ������ ������������
	 */
	public UserVisitingMode getVisitingMode()
	{
		return visitingMode;
	}

	/**
	 * @param visitingMode ����� ������ ������������
	 */
	public void setVisitingMode(UserVisitingMode visitingMode)
	{
		this.visitingMode = visitingMode;
	}

	/**
	 * @return ��������� ������ ��� ���
	 */
	public String getRandomString()
	{
		return randomString;
	}

	/**
	 * @param randomString ��������� ������ ��� ���
	 */
	public void setRandomString(String randomString)
	{
		this.randomString = randomString;
	}

	/**
	 * @return ���������� ��� �������
	 */
	public Certificate getCertificate()
	{
		return certificate;
	}

	/**
	 * @param certificate ���������� ��� �������
	 */
	public void setCertificate(Certificate certificate)
	{
		this.certificate = certificate;
	}

	/**
	 * @return ��������� �������� ������������
	 */
	public Properties getPolicyProperties()
	{
		return policyProperties;
	}

	/**
	 * @param policyProperties ��������� �������� ������������
	 */
	public void setPolicyProperties(Properties policyProperties)
	{
		this.policyProperties = policyProperties;
	}

	/**
	 * ��������� �������������� ������ � ���. (������ ������ ������� � ����)
	 * @return SID
	 */
	public String getCSA_SID()
	{
		return CSA_SID;
	}

	/**
	 * ��������� ������������� ������ � ���
	 * @param CSA_SID - ������������ ������
	 */
	public void setCSA_SID(String CSA_SID)
	{
		this.CSA_SID = CSA_SID;
	}

	/**
 	 * @return ������������������� ����� ��� ����������� (���� - ����) ��������
	 */
	public boolean isUpdatedEntity()
	{
		return updatedEntity;
	}

	/**
	 * ������ ���, ������������������� ����� ��� ����������� (���� - ����) ��������
	 * @param updatedEntity - ����� ��� ����������� ��������. �� ��������� ��� - false.
	 */
	public void setUpdatedEntity(boolean updatedEntity)
	{
		this.updatedEntity = updatedEntity;
	}

	/**
	 *
	 * @return ������ ��������������� �������������, ��������� �� ������ ��������������
	 */
	public List<Long> getPersonIds()
	{
		return Collections.unmodifiableList(personIds);
	}

	/**
	 *
	 * @param personIds �������������� �������������, ��������� �� ������ ��������������
	 */
	public void setPersonIds(List<Long> personIds)
	{
		this.personIds = personIds;
	}

	/**
	 * ������� �������
	 * @return
	 */
	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 * ��� �������
	 * @return
	 */
	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * �������� �������
	 * @return
	 */
	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	/**
	 * ����� ��������� ������� ����� �������
	 * @return
	 */
	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	/**
	 * ���� ��������
	 * @return
	 */
	public String getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(String birthDate)
	{
		this.birthDate = birthDate;
	}

	/**
	 * @return ������ "������ � ���������� �����"
	 */
	public boolean isCameFromYoungPeopleWebsite()
	{
		return cameFromYoungPeopleWebsite;
	}

	public void setCameFromYoungPeopleWebsite(boolean cameFromYoungPeopleWebsite)
	{
		this.cameFromYoungPeopleWebsite = cameFromYoungPeopleWebsite;
	}

	/**
	 * ���������� ��������, � ������� ���������� ������ ������������ � �������
	 * @return ���� � �������� (URL), �� ������� ������� ������� �� ���������� ��������������
	 */
	public String getStartJobPagePath()
	{
		return startJobPagePath;
	}

	public void setStartJobPagePath(String startJobPagePath)
	{
		this.startJobPagePath = startJobPagePath;
	}

	/**
	 * ���������� ����� ������ ���� API, ������������ �� �������
	 * @return ������ ���� API ������� (null - �� ����������)
	 */
	public VersionNumber getClientMobileAPIVersion()
	{
		return clientMobileAPIVersion;
	}

	public void setClientMobileAPIVersion(VersionNumber clientMobileAPIVersion)
	{
		this.clientMobileAPIVersion = clientMobileAPIVersion;
	}

	/**
	 * ������������� ������������� ����������� ������� ����� � ����������� �������� ��� ������ ���������
	 * @return
	 */
	public boolean isPlatformPasswordConfirm()
	{
		return platformPasswordConfirm;
	}

	public void setPlatformPasswordConfirm(boolean platformPasswordConfirm)
	{
		this.platformPasswordConfirm = platformPasswordConfirm;
	}

	/**
	 * ���������� ������ ���������, ����������� � �������� ��������������
	 * @return ������ ���������, ������� ���� ���������� ������������
	 * �� ��������� �������� ��������������
	 */
	public Collection<String> getMessages()
	{
		return Collections.unmodifiableSet(messages);
	}

	public void putMessage(String message)
	{
		synchronized (MESSAGES_LOCKER)
		{
			messages.add(message);
		}
	}

	/**
	 * @return ��������� ��������� � ������������� ������� �������
	 */
	public Collection<String> getInactiveESMessage()
	{
		return Collections.unmodifiableSet(inactiveESMessage);
	}

	/**
	 * �������� ��������� � ������������� ������� �������
	 * @param message ���������
	 */
	public void putInactiveESMessage(String message)
	{
		synchronized (INACTIVE_ES_MESSAGE_LOCKER)
		{
			inactiveESMessage.add(message);
		}
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

	public LoginType getLoginType()
	{
		return loginType;
	}

	public void setLoginType(LoginType loginType)
	{
		this.loginType = loginType;
	}

	/**
	 * @return ��� ����� ���� ���������� ���������� (������/�����������)
	 */
	public MobileAppScheme getMobileAppScheme()
	{
		return mobileAppScheme;
	}

	/**
	 * ���������� ��� ����� ���� ���������� ����������
	 * @param mobileAppScheme ��� ����� ���� ���������� ����������
	 */
	public void setMobileAppScheme(MobileAppScheme mobileAppScheme)
	{
		this.mobileAppScheme = mobileAppScheme;
	}

	/**
	 * @return ���������� � ������� ����������� ��������.
	 */
	public RegistrationStatus getRegistrationStatus()
	{
		return registrationStatus;
	}

	/**
	 * @param registrationStatus ������ ����������� �������.
	 */
	public void setRegistrationStatus(RegistrationStatus registrationStatus)
	{
		this.registrationStatus = registrationStatus;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public String getDeviceInfo()
	{
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo)
	{
		this.deviceInfo = deviceInfo;
	}

	public boolean isCheckedCEDBO()
	{
		return checkedCEDBO;
	}

	public void setCheckedCEDBO(boolean checkedCEDBO)
	{
		this.checkedCEDBO = checkedCEDBO;
	}

	/** id ������� ������� � ���. ��� ������� �������� ����� ���������.
	 *	������������ ������ � ����������� ������ �������� ��� ������������� ��� � ���� � ��� ��������� �����
	 * @return id ������� ������� � ���
	 */
	public Long getCsaProfileId()
	{
		return csaProfileId;
	}

	/**
	 * ���������� ������������� ������� ������� � ���
	 * @param csaProfileId ������������� �������
	 */
	public void setCsaProfileId(Long csaProfileId)
	{
		this.csaProfileId = csaProfileId;
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

	public Calendar getLastLoginDate()
	{
		return lastLoginDate;
	}

	public void setLastLoginDate(Calendar lastLoginDate)
	{
		this.lastLoginDate = lastLoginDate;
	}

	public boolean isNeedReloadProducts()
	{
		return needReloadProducts;
	}

	public void setNeedReloadProducts(boolean needReloadProducts)
	{
		this.needReloadProducts = needReloadProducts;
	}

	public String getBrowserInfo()
	{
		return browserInfo;
	}

	public void setBrowserInfo(String browserInfo)
	{
		this.browserInfo = browserInfo;
	}

	public SecurityType getSecurityType()
	{
		return securityType;
	}

	public void setSecurityType(SecurityType securityType)
	{
		this.securityType = securityType;
	}

	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}

	public AuthorizedZoneType getAuthorizedZoneType()
	{
		return authorizedZoneType;
	}

	public void setAuthorizedZoneType(AuthorizedZoneType authorizedZoneType)
	{
		this.authorizedZoneType = authorizedZoneType;
	}

	public GuestClaimType getGuestClaimType()
	{
		return guestClaimType;
	}

	public void setGuestClaimType(GuestClaimType guestClaimType)
	{
		this.guestClaimType = guestClaimType;
	}
}
