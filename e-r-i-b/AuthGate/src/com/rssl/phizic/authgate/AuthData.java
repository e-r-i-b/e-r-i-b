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
 * Класс с данными аутентификации, при аутентификации через ЦСА.
 * @author egorova
 * @ created 20.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class AuthData
{
	private String SID;                                 //Уникальные идентификатор пользовательской сессии в ЦСА.
	private String ASPKey;
	private boolean isMoveSession;                      //Признак переброса сессии.
	private String userId;                              //Логин пользователя (то, что ввел пользователь).
	private String userAlias;                           //Логин/алиас пользователя.
	private boolean MB;                                 //Признак подключения к мобильному банку.
	private String CB_CODE;                             //Департамент, к которому привязана карта, по которой вошел клиент
	private String PAN;                                 //Номер карты, по которой вошел клиент
	private String lastName;                            //Фамилия пользователя.
	private String firstName;                           //Имя пользователя.
	private String middleName;                          //Отчество пользователя.
	private String document;                            //Номер (серия и номер) документа удостоверяющего личность.
	private String series;                              //Серия документа удостоверяющего личность.
	private String birthDate;                           //Дата рождения.
	private boolean cameFromYoungWebsite = false;       //Признак того, что пользователь пришёл с молодёжного сайта
	private String documentType;                        //Тип документа удостоверяющего личность.
	private Calendar lastLoginDate;                     //Дата последнего входа.
	private LoginType loginType;                        //Тип логина (обычный вход, IPAS, mAPI).
	private boolean expiredPassword = false;            //Признак устаревания пароля
	private String csaGuid;                             //guid коннектора в новой ЦСА
	private CSAType csaType;                            //тип используемой ЦСА
	private MobileAppScheme mobileAppScheme;            //схема мобильного приложения
	private RegistrationStatus registrationStatus;      //статус регистрации клиента
	private RegistrationType registrationType;          //тип регистрации клиента
	private String deviceId;                            //идентификатор устройства
	private String deviceInfo;                          //тип устройства
	private String mobileAppVersion;                    //версия мобильного приложения
	private AuthorizedZoneType authorizedZoneType;      //Тип зоны входа пользователя
	private SecurityType securityType;                  //уровень безопасности
	private MigrationState migrationState;              //состояние миграции данных из резервного(резервных) в основной блок
	private ProfileType profileType;                    //Тип профиля
	private Long csaProfileId;                          //Id профиля CSA. Используется только для функционала определения регионов.
														// Нельзя использовать для идентификации пользователя, т.к. может измениться при слиянии профилей
	private boolean mobileCheckout;
	private String browserInfo;
	// данные в случае гостевого входа
	private String guestPhone;
	private Long guestCode;
	private Long guestProfileId;

	private TreeMap<String, String> rsaData = new TreeMap<String, String>();//данные об устройстве пользователя для отправки в систему RSA

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
	 * @return идентификатор гостевого профиля
	 */
	public Long getGuestProfileId()
	{
		return guestProfileId;
	}

	/**
	 * @param guestProfileId идентификатор гостевого профиля
	 */
	public void setGuestProfileId(Long guestProfileId)
	{
		this.guestProfileId = guestProfileId;
	}

	/**
	 * @param mobileCheckout - Признак того, что была оплата товара, пользователь создан для mobileCheckout
	 */
	public void setMobileCheckout(boolean mobileCheckout)
	{
		this.mobileCheckout = mobileCheckout;
	}

	/**
	 * @return - Признак того, что была оплата товара, пользователь создан для mobileCheckout
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
