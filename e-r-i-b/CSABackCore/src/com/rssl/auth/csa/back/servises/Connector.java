 package com.rssl.auth.csa.back.servises;

 import com.rssl.auth.csa.back.CSAUserInfo;
 import com.rssl.auth.csa.back.Config;
 import com.rssl.auth.csa.back.exceptions.AuthenticationFailedException;
import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
 import com.rssl.auth.csa.back.servises.connectors.SocialAPIConnector;
 import com.rssl.auth.csa.back.servises.restrictions.Restriction;
import com.rssl.auth.csa.back.servises.restrictions.security.LoginSecurityRestriction;
import com.rssl.phizic.common.types.registration.RegistrationType;
 import com.rssl.phizic.common.types.security.SecurityType;
 import com.rssl.phizic.config.ConfigFactory;
 import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.DateHelper;
 import com.rssl.phizic.utils.StringHelper;
 import org.hibernate.LockMode;
import org.hibernate.Query;
 import org.hibernate.criterion.DetachedCriteria;
 import org.hibernate.criterion.Expression;
 import com.rssl.phizic.common.types.client.LoginType;

import java.util.Calendar;
import java.util.List;
 import java.util.Set;

 /**
 * @author krenev
 * @ created 24.08.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class Connector extends ActiveRecord
{
	private String guid;
	private String deviceId;
	private Calendar blockedUntil;
	private Calendar creationDate = Calendar.getInstance();
	private ConnectorState state;
	private String blockReason;
	private long authErrors = 0;
	private String userId;
	private String login;
	private Profile profile;
	private String cbCode;
	private String cardNumber;
	private Long id;
	private String deviceInfo;
	private String deviceState;
	private Calendar currentSessionDate;
	private String currentSessionId;
	private RegistrationType registrationType;
	private boolean pushSupported;
	private String phoneNumber;
	private String version;
	private String securityToken;
	private SecurityType securityType;
	private LoginType registrationLoginType;

	public Connector()
	{
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getGuid()
	{
		return guid;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	public Calendar getBlockedUntil()
	{
		return blockedUntil;
	}

	public Profile getProfile()
	{
		return profile;
	}

	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}

	public void setBlockedUntil(Calendar blockedUntil)
	{
		this.blockedUntil = blockedUntil;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public ConnectorState getState()
	{
		return state;
	}

	public void setState(ConnectorState state)
	{
		this.state = state;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getLogin() throws Exception
	{
		if(login == null)
			login = Login.getLoginByConnectorId(getId());

		return login;
	}

	/**
	 * Сброс проинициализирвоанного логина для коннектора.
	 */
	public void clearStoredLogin()
	{
		login = null;
	}

	public String getCbCode()
	{
		return cbCode;
	}

	public void setCbCode(String cbCode)
	{
		this.cbCode = cbCode;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getBlockReason()
	{
		return blockReason;
	}

	public void setBlockReason(String blockReason)
	{
		this.blockReason = blockReason;
	}

	public long getAuthErrors()
	{
		return authErrors;
	}

	public void setAuthErrors(long authErrors)
	{
		this.authErrors = authErrors;
	}

	public void setDeviceState(String deviceState)
	{
		this.deviceState = deviceState;
	}

	public String getDeviceState()
	{
		return deviceState;
	}

	public Calendar getCurrentSessionDate()
	{
		return currentSessionDate;
	}

	public void setCurrentSessionDate(Calendar currentSessionDate)
	{
		this.currentSessionDate = currentSessionDate;
	}

	public void setCurrentSessionId(String currentSessionID)
	{
		this.currentSessionId = currentSessionID;
	}

	public String getCurrentSessionId()
	{
		return currentSessionId;
	}

	public String getDeviceInfo()
	{
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo)
	{
		this.deviceInfo = deviceInfo;
	}

	public RegistrationType getRegistrationType()
	{
		return registrationType;
	}

	public void setRegistrationType(RegistrationType registrationType)
	{
		this.registrationType = registrationType;
	}

	public boolean getPushSupported()
	{
		return pushSupported;
	}

	public void setPushSupported(boolean pushSupported)
	{
		this.pushSupported = pushSupported;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getSecurityToken()
	{
		return securityToken;
	}

	public void setSecurityToken(String securityToken)
	{
		this.securityToken = securityToken;
	}

	public SecurityType getSecurityType()
	{
		return securityType;
	}

	public void setSecurityType(SecurityType securityType)
	{
		this.securityType = securityType;
	}

	public LoginType getRegistrationLoginType()
	{
		return registrationLoginType;
	}

	public void setRegistrationLoginType(LoginType registrationLoginType)
	{
		this.registrationLoginType = registrationLoginType;
	}

	public abstract ConnectorType getType();

	/**
	 * @return заблокирован ли коннектор
	 */
	public boolean isBlocked()
	{
		return state == ConnectorState.BLOCKED && (blockedUntil == null || getCurrentDate().before(blockedUntil));
	}

	/**
	 * @return закрыт ли коннектор
	 */
	public boolean isClosed()
	{
		return state == ConnectorState.CLOSED;
	}

	/**
	 * @return мигриован ли коннектор
	 */
	public abstract boolean isMigrated();

	/**
	 * 4hibernate
	 */
	public void setType(ConnectorType type)
	{}

	/**
	 * Проверить "подходит ли пароль к коннектору".
	 * В ходе аутентифкации изменяется состояние коннектора в БД.
	 * @param password пароль.
	 * @return информация о пользователе
	 * @throws Exception внутреннние ошибки
	 * @throws com.rssl.auth.csa.back.exceptions.AuthenticationFailedException в случае некорректного пароля.
	 */
	public final CSAUserInfo authenticate(final String password) throws Exception
	{
		final CSAUserInfo result = executeIsolated(new HibernateAction<CSAUserInfo>()
		{
			public CSAUserInfo run(org.hibernate.Session session) throws Exception
			{
				session.refresh(Connector.this, LockMode.UPGRADE_NOWAIT);
				if (isClosed())
				{
					throw new ConnectorNotFoundException("Коннектор " + guid + " закрыт");
				}
				if (isBlocked())
				{
					throw new AuthenticationFailedException(Connector.this);
				}
				//если был заблокирован, то блокировка уже неактуальна - сбрасываем ее.
				state = ConnectorState.ACTIVE;
				blockedUntil = null;
				blockReason = null;

				CSAUserInfo userInfo = checkPassword(password);
				if (userInfo != null)
				{
					authErrors = 0;
				}
				else
				{
					log.trace("Неудачная попытка аутентифкации для коннектора " + getGuid());
					authErrors++;
					if (authErrors >= ConfigFactory.getConfig(Config.class).getMaxAuthenticationFailed())
					{
						state = ConnectorState.BLOCKED;
						Calendar startBlockDate = getCurrentDate();
						blockedUntil = DateHelper.addSeconds(startBlockDate, ConfigFactory.getConfig(Config.class).getAuthenticationFailedBlockingTimeOut());
						blockReason = "Превышено количество неудачных попыток аутентификации";
						authErrors = 0;
						log.trace("Превышено количество неудачных попыток аутентифкации для коннектора " + getGuid()+". Коннектор будет заблокирован.");
						session.save(new ProfileLockCHG071536(profile.getId(), startBlockDate, blockedUntil, blockReason, "SYSTEM"));
					}
				}
				session.update(Connector.this);
				return userInfo;
			}
		});
		if (result == null)
		{
			throw new AuthenticationFailedException(this);
		}
		return result;
	}

	/**
	 * проверить пароль на соответствие коннектору
	 * @param password пароль
	 * @return информация о пользователе в случае удачной проверки. null если пароль не подходит
	 */
	protected abstract CSAUserInfo checkPassword(String password) throws Exception;

	/**
	 * Сгенерировать новый пароль и установить для коннектора.
	 * Новый сгенеренный пароль высылается на телефон клиента.
	 * @throws Exception
	 */
	public abstract void generatePassword(Set<String> excludedPhones) throws Exception;

	/**
	 * @return дата создания пароля или null, если коннектор использует внешнюю аутентияикацию и к нему не привязаны пароли.
	 */
	public abstract Calendar getPasswordCreationDate() throws Exception;

	/**
	 * Адаптировать коннектор к CSAUserInfo
	 * @return информация о пользователе.
	 */
	public CSAUserInfo asUserInfo()
	{
		CSAUserInfo result = new CSAUserInfo(CSAUserInfo.Source.CSA);
		Profile profile = getProfile();
		result.setFirstname(profile.getFirstname());
		result.setPatrname(profile.getPatrname());
		result.setSurname(profile.getSurname());
		result.setBirthdate(profile.getBirthdate());
		result.setPassport(profile.getPassport());
		result.setCbCode(getCbCode());
		result.setUserId(getUserId());
		result.setCardNumber(getCardNumber());
		return result;
	}

	/**
	 * Закрыть коннектор.
	 * Вместе с коннектором закрываются все активные сесиии.
	 * @throws Exception
	 */
	public void close() throws Exception
	{
		executeAtomic(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				List<Session> sessions = Session.findActiveByConnector(Connector.this, ConfigFactory.getConfig(Config.class).getSessionTimeout());
				for (Session activeSesion : sessions)
				{
					log.info("Закрываем сессию " + activeSesion.getGuid());
					try
					{
						activeSesion.close();
					}
					catch (Exception e)
					{
						log.error("Ошибка при закрытии сессии " + activeSesion.getGuid(), e);
					}
				}
				state = ConnectorState.CLOSED;
				save();
				return null;
			}
		});
	}

	/**
	 *
	 * @return огранияение на логин
	 */
	public Restriction<String> getLoginRestriction()
	{
		return LoginSecurityRestriction.getInstance(this);
	}

	/**
	 * Рассчитать уровень безопасности
	 * @return уровень безопасности
	 * @throws Exception
	 */
	public SecurityType calcSecurityType() throws Exception
	{
		if (securityType != null)
		{
			SecurityTypeHelper.logSecurityType(getLogSecurityTypeMessage());
		}
		return securityType;
	}

	protected String getLogSecurityTypeMessage()
	{
		return securityType.getDescription();
	}

	/**
	 * Обновить информацию о карте в коннекторе даннами из src
	 * @param src коннектор, содержащий инфу о карте, которую требуется копировать.
	 */
	public void changeCardInfo(Connector src) throws Exception
	{
		if (src == null)
		{
			throw new IllegalArgumentException("Коннектор не может быть null");
		}
		setCardNumber(src.getCardNumber());
		setCbCode(src.getCbCode());
		setUserId(src.getUserId());
		save();
	}

	/**
	 * Найти коннектор по GUID
	 * @param guid собственно guid
	 * @return найденный коннектор или null.
	 */
	public static Connector findByGUID(String guid) throws Exception
	{
		return findByGUID(guid, null);
	}

	/**
	 * Найти коннектор по GUID
	 * @param guid собственно guid
	 * @param lockMode режим блокировки. если не задан - без блокировки
	 * @return найденный коннектор или null.
	 */
	public static Connector findByGUID(final String guid, final LockMode lockMode) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.getConnectorByGUID")
						.setParameter("guid", guid);
				if (lockMode != null)
				{
					query.setLockMode("connector", lockMode);
				}
				return (Connector) query.uniqueResult();
			}
		});
	}

	/**
	 * Найти коннектор по первичному ключу
	 * @param connectorId идентификатор коннектора
	 * @param lockMode режим лока, null если лок не нужет
	 * @return коннектор
	 * @throws Exception
	 */
	public static Connector findById(Long connectorId, LockMode lockMode) throws Exception
	{
		return findById(Connector.class, connectorId, lockMode, getInstanceName());
	}

	/**
	 * Найти коннектор по алиасу
	 * @param alias собственно алиас
	 * @return найденный коннектор или null.
	 */
	public static Connector findByAlias(final String alias) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				return (Connector) session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.getByAlias")
						.setParameter("login", alias)
						.uniqueResult();
			}
		});
	}

	/**
	 * Найти коннектор по алиасу исключив коннекторы конкретного пользователя.
	 * @param alias собственно алиас
	 * @param profileId идентифкатор профиля который нужно исключить из поиска
	 * @return найденный коннектор или null.
	 */
	public static Connector findByAlias(final String alias, final Long profileId) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				return (Connector) session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.getByAliasExtended")
						.setParameter("login", alias)
						.setParameter("profileId", profileId)
						.uniqueResult();
			}
		});
	}

	/**
	 * Найти множество коннекторов по логину iPas
	 * @param userId логин Ipas
	 * @return списаок коннекторов или пустой список.
	 */
	public static List<Connector> findByUserId(final String userId) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<Connector>>()
		{
			public List<Connector> run(org.hibernate.Session session) throws Exception
			{
				return (List<Connector>) session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.getByUserId")
						.setParameter("user_id", userId)
						.list();
			}
		});
	}

	/**
	 * Найти множество коннекторов по номеру карты
	 * @param cardNumber номер карты
	 * @return списаок коннекторов или пустой список.
	 */
	public static List<Connector> findByCardNumber(final String cardNumber) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<Connector>>()
		{
			public List<Connector> run(org.hibernate.Session session) throws Exception
			{
				return (List<Connector>) session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.getByCardNumber")
						.setParameter("card_number", cardNumber)
						.list();
			}
		});
	}

	/**
	 * Получить количество коннеторов пользователей в заданных статусах.
	 * @param profileId идентификатор профиля
	 * @param type тип коннектора
	 * @param deviceId идентификатор устройства
	 * @param states статусы профиля
	 * @return количество
	 */
	public static int getCount(final Long profileId, final ConnectorType type, final String deviceId, final ConnectorState... states) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(org.hibernate.Session session) throws Exception
			{
				return (Integer) session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.getCount")
						.setParameter("profile_id", profileId)
						.setParameter("type", type)
						.setParameterList("states", states)
						.setParameter("deviceId", deviceId)
						.uniqueResult();
			}
		});
	}

	/**
	 * Перепривязать коннекторы от профиля oldProfile к профилю actualProfile.
	 * @param oldProfile - профиль, от которого отвязываются коннекторы
	 * @param actualProfile - профиль, к которому перепривязывается коннекторы
	 * @return Количество перепривязанных коннеткоров. 0 - если ни один коннектор не перепривязан
	 */
	public static Integer changeProfile(final Profile oldProfile, final Profile actualProfile) throws Exception
	{
		if (oldProfile == null)
		{
			throw new IllegalArgumentException("Старый профиль не может быть null");
		}
		if (actualProfile == null)
		{
			throw new IllegalArgumentException("Актуальный профиль не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(org.hibernate.Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.changeProfile")
						.setParameter("old_profile", oldProfile)
						.setParameter("new_profile", actualProfile)
						.executeUpdate();
			}
		});
	}

	/**
	 * Найти все коннекторы по deviceId. Ищутся только коннекторы, статус которых не "CLOSED"
	 * @param deviceId - уникальный идентификатор устройства
	 * @return список коннекторов
	 * @throws Exception
	 */
	public static List<Connector> findByDeviceId(final String deviceId) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<Connector>>()
		{
			public List<Connector> run(org.hibernate.Session session) throws Exception
			{
				return (List<Connector>) session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.findByDeviceId")
						.setParameter("deviceId", deviceId)
						.list();
			}
		});
	}

	/**
	 * Найти все коннекторы по deviceId и deviceInfo. Ищутся только коннекторы, статус которых не "CLOSED"
	 * @param deviceId - идентификатор клиента во внешнем приложении
	 * @param deviceInfo - идентификатор платформы, в рамках которой поступил запрос на перевод
	 * @return список коннекторов
	 * @throws Exception
	 */
	public static SocialAPIConnector findByDeviceIdAndInfo(final String deviceId, final String deviceInfo) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<SocialAPIConnector>()
		{
			public SocialAPIConnector run(org.hibernate.Session session) throws Exception
			{
				return (SocialAPIConnector) session.getNamedQuery("com.rssl.auth.csa.back.servises.SocialAPIConnector.findByDeviceIdAndInfo")
						.setParameter("deviceId", deviceId)
						.setParameter("deviceInfo", deviceInfo)
						.uniqueResult();
			}
		});
	}

	/**
	 * Найти все коннекторы по которым был ранее вход клиента по заданному идентификатору профиля.
	 * @param profileId  - идентификатор профиля клиента
	 * @return  список коннекторов
	 */
	public static List<Connector> findExistEnterByProfileId(final Long profileId) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<Connector>>()
		{
			public List<Connector> run(org.hibernate.Session session) throws Exception
			{
				return (List<Connector>) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.Connector.getExistEnterByProfileId")
						.setParameter("profile_id", profileId)
						.list();
			}
		});
	}

	/**
	 * Найти коннектор по номеру телефона
	 * @param phoneNumber номер телефона
	 * @return коннектор
	 * @throws Exception
	 */
	public static Connector findByPhoneNumber(final String phoneNumber) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				return (Connector) session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.findByPhoneNumber")
						.setParameter("phone_number", phoneNumber)
						.uniqueResult();
			}
		});
	}

	/**
	 * Найти коннектор по номеру телефона
	 * @param phoneNumber номер телефона
	 * @return коннектор
	 * @throws Exception
	 */
	public static Long getProfileIdByPhoneNumber(final String phoneNumber) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Long>()
		{
			public Long run(org.hibernate.Session session) throws Exception
			{
				return (Long) session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.getProfileIdByPhoneNumber")
						.setParameter("phone", phoneNumber)
						.uniqueResult();
			}
		});
	}

	/**
	 * Установить всем незакрытым коннекторам профиля уровень безопасности профиля
	 * @param profileId Идентификатор профиля
	 * @param securityType уровень безопасности
	 * @param connectorType тип коннектора
	 * @return число обновленных коннекторов
	 * @throws Exception
	 */
	public static int setSecurityTypeToNotClosed(final Long profileId, final SecurityType securityType, final ConnectorType connectorType) throws Exception
	{
		if (profileId == null)
		{
			throw  new IllegalArgumentException("Идентификатор профиля не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(org.hibernate.Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.setSecurityTypeToNotClosed")
						.setParameter("security_type",  securityType == null ? null : securityType.name())
						.setParameter("profile_id",     profileId)
						.setParameter("type",           connectorType)
						.executeUpdate();
			}
		});
	}

	/**
	 * Получить список незакрытых коннекторов профиля
	 * @param profileId идентифкатор профиля
	 * @return список коннекторов или пустой список
	 * @throws Exception
	 */
	public static List<? extends Connector> findNotClosedByProfileID(Long profileId) throws Exception
	{
		return findNotClosedByProfileID(profileId, Connector.class);
	}

	/**
	 * Получить список незакрытых коннекторов профиля заданного типа коннектора
	 * @param profileId идентифкатор профиля
	 * @param connectorClass тип коннектора
	 * @return список коннекторов или пустой список
	 * @throws Exception
	 */
	public static <E extends Connector> List<E> findNotClosedByProfileID(Long profileId, Class<E> connectorClass) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(connectorClass)
				.add(Expression.eq("profile.id", profileId))
				.add(Expression.ne("state", ConnectorState.CLOSED));
		return find(criteria, null);
	}

	/**
	 * Проверить существование незакрытых коннекторов профиля для типов коннектора CSA и Terminal
	 * @param profileId идентифкатор профиля
	 * @return список коннекторов или пустой список
	 * @throws Exception
	 */
	public static boolean isExistNotClosedTerminalAndCSAByProfileID(final Long profileId) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Boolean>()
		{
			public Boolean run(org.hibernate.Session session) throws Exception
			{
				return !session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.Connector.isExistNotClosedTerminalAndCSAByProfileID")
						.setParameter("profileId", profileId)
						.setMaxResults(1)
						.list().isEmpty();
			}
		});
	}


	/**
	 * Получить список незакрытых коннекторов профиля для типов коннектора CSA и Terminal
	 * @param cardNumber идентифкатор профиля
	 * @return id профиля для первого найденного коннектора
	 * @throws Exception
	 */
	public static Long findNotClosedTerminalAndCSAByCardNumber(final String cardNumber) throws Exception
	{
		if (StringHelper.isEmpty(cardNumber))
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");
		}

		return getHibernateExecutor().execute(new HibernateAction<Long>()
		{
			public Long run(org.hibernate.Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.Connector.findProfileIdNotClosedTerminalAndCSAByCardNumber");
				query.setParameter("cardNumber", cardNumber);
				query.setMaxResults(1);
				return (Long)query.uniqueResult();
			}
		});
	}

	/**
	 * разблокировать все коннекторы для профиля
	 * @param profile профиль
	 * @throws Exception
	 */
	public static void unlockAll(final Profile profile)  throws Exception
	{
		executeAtomic(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.unlockForProfile")
						.setParameter("profile", profile)
						.executeUpdate();
				return null;
			}
		});
	}
}
