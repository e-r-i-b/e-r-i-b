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
	private Person              person; // найденная персона
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
	private String              csaGuid;                                    //guid коннектор ЦСА, под которым зашел клиент
	private CSAType             csaType;                                    //тип используемой ЦСА при входе
	private LoginType           loginType;                                  //тип логина
	private RegistrationStatus  registrationStatus;                         //статус регистрации клиента

	private VersionNumber       clientMobileAPIVersion = null;
	//Для мобильной и социальной платформ необходимо подтверждение одноразовым паролем
	private boolean             platformPasswordConfirm;
	private final Object        MESSAGES_LOCKER = new Object();
	private final Set<String>   messages = new LinkedHashSet<String>();
	private final Object        INACTIVE_ES_MESSAGE_LOCKER = new Object();
	private final Set<String>   inactiveESMessage = new LinkedHashSet<String>();
	private MobileAppScheme     mobileAppScheme;
	private String              deviceId;                                   //идентификатор устройства
	private String deviceInfo;                                              //тип устройства
	private boolean checkedCEDBO = false;
	private MigrationState      migrationState;                             //состояние миграции данных из резервного(резервных) в основной блок
	private ProfileType         profileType;                                //Тип профиля
	private Calendar            lastLoginDate;                              //Дата последнего входа по текущему логину
	private boolean             needReloadProducts = true;                  //признак необходимости обновления продуктов при входе клиента
	private String              browserInfo;
	private SecurityType        securityType;
	private AuthorizedZoneType authorizedZoneType;
	private GuestClaimType      guestClaimType;


	/**
	 * Возвращает контекст аутентификации текущей сессии
	 * @return текущий контекст
	 */
	public static AuthenticationContext getContext()
	{
		Store store = StoreManager.getCurrentStore();
		if (store == null)
			throw new IllegalStateException("Нет сессии");
		return (AuthenticationContext) store.restore(AUTHENTICATION_CONTEXT_KEY);
	}

	/**
	 * Устанавливает контекст аутентификации в текущую сессию
	 * @param context - контекст аутентификации
	 */
	public static void setContext(AuthenticationContext context)
	{
		Store store = StoreManager.getCurrentStore();
		if (store == null)
			throw new IllegalStateException("Нет сессии");
		store.save(AUTHENTICATION_CONTEXT_KEY, context);
	}

	/**
	 * Удаляет контекст аутентификации из текущей сессии
	 */
	public static void removeContext()
	{
		Store store = StoreManager.getCurrentStore();
		if (store == null)
			throw new IllegalStateException("Нет сессии");
		store.remove(AUTHENTICATION_CONTEXT_KEY);
	}

	/**
	 * Возвращает параметр аутентификации
	 * @param key - ключ параметра
	 * @return значение параметра
	 */
	public String getAuthenticationParameter(String key)
	{
		return authenticationParameters.get(key);
	}

	/**
	 * @return параметры аутентификации
	 */
	public Map<String, String> getAuthenticationParameters()
	{
		return authenticationParameters;
	}

	/**
	 * Очистить параметры аутентификации
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
	 * Задаем параметр аунтентификации
	 * или обновляем его если он уже есть
	 * @param name  ключ параметра
	 * @param parameter значение параметра
	 */
	public void addOrUpdateAuthenticationParameter(String name,String parameter)
	{
		authenticationParameters.put(name,parameter);
	}

	/*признака подключения к мобильному банку, полученного от запроса к Way4*/
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
	 * @return логин пользователя, по которому был осуществлен вход
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
	 * @return алиас/логин пользователя.
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
	 * @return Запрос кода территориального банка, полученного от запроса к Way4
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
	 * @return номер карты пользователя, для которой получен статический пароль
	 */
	public String getPAN()
	{
		return PAN;
	}

	/**
	 * @param PAN - номер карты пользователя, для которой получен статический пароль
	 */
	public void setPAN(String PAN)
	{
		this.PAN = PAN;
	}

	/**
	 * првозвращает признак того, что клиент бл перемещен в ИКФЛ из другой системы
	 * Нужно для ЦСА. Клиент перемещается к нам из банковских карт.
	 * В случае если значение true, доп. авторизация смс-паролем не запрашивается.
	 * @return значение прризнака переноса клиента из другой подсистемы АС СБОЛ
	 */
	public boolean isMoveSession()
	{
		return isMoveSession;
	}

	/**
	 * установка признака переноса клиента из другой подсистемы
	 * @param moveSession признак переноса.
	 */
	public void setMoveSession(boolean moveSession)
	{
		isMoveSession = moveSession;
	}

	/**
	 *
	 * @return номер пароля из карты кючей
	 */
	public Integer getPasswordNumber()
	{
		return passwordNumber;
	}

	/**
	 *
	 * @param passwordNumber - номер пароля
	 */
	public void setPasswordNumber(Integer passwordNumber)
	{
		this.passwordNumber = passwordNumber;
	}

	/**
	 * @param policy политика
	 */
	public AuthenticationContext(AccessPolicy policy)
	{
		this.policy = policy;
	}

	/**
	 * @return логин
	 */
	public CommonLogin getLogin()
	{
		return login;
	}

	/**
	 * @param login логин
	 */
	public void setLogin(CommonLogin login)
	{
		this.login = login;
	}


	/**
	 * @return режим аутентификации
	 */
	public AccessPolicy getPolicy()
	{
		return policy;
	}

	/**
	 * @return режим работы пользователя
	 */
	public UserVisitingMode getVisitingMode()
	{
		return visitingMode;
	}

	/**
	 * @param visitingMode режим работы пользователя
	 */
	public void setVisitingMode(UserVisitingMode visitingMode)
	{
		this.visitingMode = visitingMode;
	}

	/**
	 * @return случайная строка для ЭЦП
	 */
	public String getRandomString()
	{
		return randomString;
	}

	/**
	 * @param randomString случайная строка для ЭЦП
	 */
	public void setRandomString(String randomString)
	{
		this.randomString = randomString;
	}

	/**
	 * @return сертификат для подписи
	 */
	public Certificate getCertificate()
	{
		return certificate;
	}

	/**
	 * @param certificate сертификат для подписи
	 */
	public void setCertificate(Certificate certificate)
	{
		this.certificate = certificate;
	}

	/**
	 * @return настройки политики безопасности
	 */
	public Properties getPolicyProperties()
	{
		return policyProperties;
	}

	/**
	 * @param policyProperties настройки политики безопасности
	 */
	public void setPolicyProperties(Properties policyProperties)
	{
		this.policyProperties = policyProperties;
	}

	/**
	 * получение идентификатора сессии с ЦСА. (аналог сессии клиента в ИКФЛ)
	 * @return SID
	 */
	public String getCSA_SID()
	{
		return CSA_SID;
	}

	/**
	 * запомнить идентификатор сессии с ЦСА
	 * @param CSA_SID - идентифкатор сессии
	 */
	public void setCSA_SID(String CSA_SID)
	{
		this.CSA_SID = CSA_SID;
	}

	/**
 	 * @return аутентифицировалась новая или обновленная (СБОЛ - УДБО) сущность
	 */
	public boolean isUpdatedEntity()
	{
		return updatedEntity;
	}

	/**
	 * Задать что, аутенцифицировалась новая или обновленная (СБОЛ - УДБО) сущность
	 * @param updatedEntity - новая или обновленная сущность. По умолчанию нет - false.
	 */
	public void setUpdatedEntity(boolean updatedEntity)
	{
		this.updatedEntity = updatedEntity;
	}

	/**
	 *
	 * @return Список идентификаторов пользователей, найденных по данным аутентификации
	 */
	public List<Long> getPersonIds()
	{
		return Collections.unmodifiableList(personIds);
	}

	/**
	 *
	 * @param personIds идентификаторы пользователей, найденных по данным аутентификации
	 */
	public void setPersonIds(List<Long> personIds)
	{
		this.personIds = personIds;
	}

	/**
	 * Фамилия клиента
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
	 * Имя клиента
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
	 * Отчество клиента
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
	 * Номер документа клиента одной строкой
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
	 * Дата рождения
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
	 * @return флажок "пришёл с молодёжного сайта"
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
	 * Возвращает страницу, с которой начинается работа пользователя в системе
	 * @return путь к странице (URL), на которую следует перейти по завершению аутентификации
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
	 * Возвращает номер версии ЕРИБ API, используемой на клиенте
	 * @return версия ЕРИБ API клиента (null - не определена)
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
	 * Необходимость подтверждения одноразовым паролем входа и совершаемых операций для данной платформы
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
	 * Возвращает список сообщений, накопленных в процессе аутентификации
	 * @return список сообщений, которые надо отобразить пользователю
	 * по окончанию процесса аутентификации
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
	 * @return коллекция сообщений о недоступности внешней системы
	 */
	public Collection<String> getInactiveESMessage()
	{
		return Collections.unmodifiableSet(inactiveESMessage);
	}

	/**
	 * добавить сообщение о недоступности внешней системы
	 * @param message сообщение
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
	 * @return тип схемы прав мобильного приложения (полная/облегченная)
	 */
	public MobileAppScheme getMobileAppScheme()
	{
		return mobileAppScheme;
	}

	/**
	 * установить тип схемы прав мобильного приложения
	 * @param mobileAppScheme тип схемы прав мобильного приложения
	 */
	public void setMobileAppScheme(MobileAppScheme mobileAppScheme)
	{
		this.mobileAppScheme = mobileAppScheme;
	}

	/**
	 * @return информация о статусе регистрации клиентаа.
	 */
	public RegistrationStatus getRegistrationStatus()
	{
		return registrationStatus;
	}

	/**
	 * @param registrationStatus статус регистрации клиента.
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

	/** id профиля клиента в ЦСА. при слиянии профилей может смениться.
	 *	используется только в функционале выбора регионов для синхронизации ЦСА и ЕРИБ и для гостевого входа
	 * @return id профиля клиента в ЦСА
	 */
	public Long getCsaProfileId()
	{
		return csaProfileId;
	}

	/**
	 * Установить идентификатор профиля клиента в цса
	 * @param csaProfileId идентивикатор профиля
	 */
	public void setCsaProfileId(Long csaProfileId)
	{
		this.csaProfileId = csaProfileId;
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
