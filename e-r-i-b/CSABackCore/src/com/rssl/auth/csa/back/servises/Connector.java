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
	 * ����� ���������������������� ������ ��� ����������.
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
	 * @return ������������ �� ���������
	 */
	public boolean isBlocked()
	{
		return state == ConnectorState.BLOCKED && (blockedUntil == null || getCurrentDate().before(blockedUntil));
	}

	/**
	 * @return ������ �� ���������
	 */
	public boolean isClosed()
	{
		return state == ConnectorState.CLOSED;
	}

	/**
	 * @return ��������� �� ���������
	 */
	public abstract boolean isMigrated();

	/**
	 * 4hibernate
	 */
	public void setType(ConnectorType type)
	{}

	/**
	 * ��������� "�������� �� ������ � ����������".
	 * � ���� ������������� ���������� ��������� ���������� � ��.
	 * @param password ������.
	 * @return ���������� � ������������
	 * @throws Exception ����������� ������
	 * @throws com.rssl.auth.csa.back.exceptions.AuthenticationFailedException � ������ ������������� ������.
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
					throw new ConnectorNotFoundException("��������� " + guid + " ������");
				}
				if (isBlocked())
				{
					throw new AuthenticationFailedException(Connector.this);
				}
				//���� ��� ������������, �� ���������� ��� ����������� - ���������� ��.
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
					log.trace("��������� ������� ������������� ��� ���������� " + getGuid());
					authErrors++;
					if (authErrors >= ConfigFactory.getConfig(Config.class).getMaxAuthenticationFailed())
					{
						state = ConnectorState.BLOCKED;
						Calendar startBlockDate = getCurrentDate();
						blockedUntil = DateHelper.addSeconds(startBlockDate, ConfigFactory.getConfig(Config.class).getAuthenticationFailedBlockingTimeOut());
						blockReason = "��������� ���������� ��������� ������� ��������������";
						authErrors = 0;
						log.trace("��������� ���������� ��������� ������� ������������� ��� ���������� " + getGuid()+". ��������� ����� ������������.");
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
	 * ��������� ������ �� ������������ ����������
	 * @param password ������
	 * @return ���������� � ������������ � ������ ������� ��������. null ���� ������ �� ��������
	 */
	protected abstract CSAUserInfo checkPassword(String password) throws Exception;

	/**
	 * ������������� ����� ������ � ���������� ��� ����������.
	 * ����� ����������� ������ ���������� �� ������� �������.
	 * @throws Exception
	 */
	public abstract void generatePassword(Set<String> excludedPhones) throws Exception;

	/**
	 * @return ���� �������� ������ ��� null, ���� ��������� ���������� ������� �������������� � � ���� �� ��������� ������.
	 */
	public abstract Calendar getPasswordCreationDate() throws Exception;

	/**
	 * ������������ ��������� � CSAUserInfo
	 * @return ���������� � ������������.
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
	 * ������� ���������.
	 * ������ � ����������� ����������� ��� �������� ������.
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
					log.info("��������� ������ " + activeSesion.getGuid());
					try
					{
						activeSesion.close();
					}
					catch (Exception e)
					{
						log.error("������ ��� �������� ������ " + activeSesion.getGuid(), e);
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
	 * @return ����������� �� �����
	 */
	public Restriction<String> getLoginRestriction()
	{
		return LoginSecurityRestriction.getInstance(this);
	}

	/**
	 * ���������� ������� ������������
	 * @return ������� ������������
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
	 * �������� ���������� � ����� � ���������� ������� �� src
	 * @param src ���������, ���������� ���� � �����, ������� ��������� ����������.
	 */
	public void changeCardInfo(Connector src) throws Exception
	{
		if (src == null)
		{
			throw new IllegalArgumentException("��������� �� ����� ���� null");
		}
		setCardNumber(src.getCardNumber());
		setCbCode(src.getCbCode());
		setUserId(src.getUserId());
		save();
	}

	/**
	 * ����� ��������� �� GUID
	 * @param guid ���������� guid
	 * @return ��������� ��������� ��� null.
	 */
	public static Connector findByGUID(String guid) throws Exception
	{
		return findByGUID(guid, null);
	}

	/**
	 * ����� ��������� �� GUID
	 * @param guid ���������� guid
	 * @param lockMode ����� ����������. ���� �� ����� - ��� ����������
	 * @return ��������� ��������� ��� null.
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
	 * ����� ��������� �� ���������� �����
	 * @param connectorId ������������� ����������
	 * @param lockMode ����� ����, null ���� ��� �� �����
	 * @return ���������
	 * @throws Exception
	 */
	public static Connector findById(Long connectorId, LockMode lockMode) throws Exception
	{
		return findById(Connector.class, connectorId, lockMode, getInstanceName());
	}

	/**
	 * ����� ��������� �� ������
	 * @param alias ���������� �����
	 * @return ��������� ��������� ��� null.
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
	 * ����� ��������� �� ������ �������� ���������� ����������� ������������.
	 * @param alias ���������� �����
	 * @param profileId ������������ ������� ������� ����� ��������� �� ������
	 * @return ��������� ��������� ��� null.
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
	 * ����� ��������� ����������� �� ������ iPas
	 * @param userId ����� Ipas
	 * @return ������� ����������� ��� ������ ������.
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
	 * ����� ��������� ����������� �� ������ �����
	 * @param cardNumber ����� �����
	 * @return ������� ����������� ��� ������ ������.
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
	 * �������� ���������� ���������� ������������� � �������� ��������.
	 * @param profileId ������������� �������
	 * @param type ��� ����������
	 * @param deviceId ������������� ����������
	 * @param states ������� �������
	 * @return ����������
	 */
	public static int getCount(final Long profileId, final ConnectorType type, final String deviceId, final ConnectorState... states) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
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
	 * ������������� ���������� �� ������� oldProfile � ������� actualProfile.
	 * @param oldProfile - �������, �� �������� ������������ ����������
	 * @param actualProfile - �������, � �������� ����������������� ����������
	 * @return ���������� ��������������� �����������. 0 - ���� �� ���� ��������� �� ������������
	 */
	public static Integer changeProfile(final Profile oldProfile, final Profile actualProfile) throws Exception
	{
		if (oldProfile == null)
		{
			throw new IllegalArgumentException("������ ������� �� ����� ���� null");
		}
		if (actualProfile == null)
		{
			throw new IllegalArgumentException("���������� ������� �� ����� ���� null");
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
	 * ����� ��� ���������� �� deviceId. ������ ������ ����������, ������ ������� �� "CLOSED"
	 * @param deviceId - ���������� ������������� ����������
	 * @return ������ �����������
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
	 * ����� ��� ���������� �� deviceId � deviceInfo. ������ ������ ����������, ������ ������� �� "CLOSED"
	 * @param deviceId - ������������� ������� �� ������� ����������
	 * @param deviceInfo - ������������� ���������, � ������ ������� �������� ������ �� �������
	 * @return ������ �����������
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
	 * ����� ��� ���������� �� ������� ��� ����� ���� ������� �� ��������� �������������� �������.
	 * @param profileId  - ������������� ������� �������
	 * @return  ������ �����������
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
	 * ����� ��������� �� ������ ��������
	 * @param phoneNumber ����� ��������
	 * @return ���������
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
	 * ����� ��������� �� ������ ��������
	 * @param phoneNumber ����� ��������
	 * @return ���������
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
	 * ���������� ���� ���������� ����������� ������� ������� ������������ �������
	 * @param profileId ������������� �������
	 * @param securityType ������� ������������
	 * @param connectorType ��� ����������
	 * @return ����� ����������� �����������
	 * @throws Exception
	 */
	public static int setSecurityTypeToNotClosed(final Long profileId, final SecurityType securityType, final ConnectorType connectorType) throws Exception
	{
		if (profileId == null)
		{
			throw  new IllegalArgumentException("������������� ������� �� ����� ���� null");
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
	 * �������� ������ ���������� ����������� �������
	 * @param profileId ������������ �������
	 * @return ������ ����������� ��� ������ ������
	 * @throws Exception
	 */
	public static List<? extends Connector> findNotClosedByProfileID(Long profileId) throws Exception
	{
		return findNotClosedByProfileID(profileId, Connector.class);
	}

	/**
	 * �������� ������ ���������� ����������� ������� ��������� ���� ����������
	 * @param profileId ������������ �������
	 * @param connectorClass ��� ����������
	 * @return ������ ����������� ��� ������ ������
	 * @throws Exception
	 */
	public static <E extends Connector> List<E> findNotClosedByProfileID(Long profileId, Class<E> connectorClass) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(connectorClass)
				.add(Expression.eq("profile.id", profileId))
				.add(Expression.ne("state", ConnectorState.CLOSED));
		return find(criteria, null);
	}

	/**
	 * ��������� ������������� ���������� ����������� ������� ��� ����� ���������� CSA � Terminal
	 * @param profileId ������������ �������
	 * @return ������ ����������� ��� ������ ������
	 * @throws Exception
	 */
	public static boolean isExistNotClosedTerminalAndCSAByProfileID(final Long profileId) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
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
	 * �������� ������ ���������� ����������� ������� ��� ����� ���������� CSA � Terminal
	 * @param cardNumber ������������ �������
	 * @return id ������� ��� ������� ���������� ����������
	 * @throws Exception
	 */
	public static Long findNotClosedTerminalAndCSAByCardNumber(final String cardNumber) throws Exception
	{
		if (StringHelper.isEmpty(cardNumber))
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
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
	 * �������������� ��� ���������� ��� �������
	 * @param profile �������
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
