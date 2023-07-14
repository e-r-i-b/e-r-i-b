package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.integration.mobilebank.MobileBankService;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.connectors.MAPIConnector;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.LockMode;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class Profile extends ActiveRecord
{
	private Long id;
	private String firstname;
	private String patrname;
	private String surname;
	private Calendar birthdate;
	private String passport;
	private Password password;
	private String tb;
	private boolean incognito;
	private String agreementNumber;
	private CreationType creationType;
	private String phone;
	private SecurityType securityType;

	public Profile()
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

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getPatrname()
	{
		return patrname;
	}

	public void setPatrname(String patrname)
	{
		this.patrname = patrname;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * @return ��������������� ��� (��� ������ �������� � �� �����)
	 */
	public String getNormalizedFIO()
	{
		return normalizeFIO(getSurname(), getFirstname(), getPatrname());
	}

	public Calendar getBirthdate()
	{
		return birthdate;
	}

	public void setBirthdate(Calendar birthdate)
	{
		this.birthdate = birthdate;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	/**
	 * @return ��������������� �������(��� �������� � �� �����)
	 */
	public String getNormalizedPassport()
	{
		return normalizePassport(getPassport());
	}

	public Password getMapiPassword()
	{
		return password;
	}

	public void setMapiPassword(Password password)
	{
		this.password = password;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public boolean isIncognito()
	{
		return incognito;
	}

	public void setIncognito(boolean incognito)
	{
		this.incognito = incognito;
	}

	/**
	 * @return ����� ��������
	 */
	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	/**
	 * ������ ����� ��������
	 * @param agreementNumber ����� ��������
	 */
	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	/**
	 * @return ��� ��������
	 */
	public CreationType getCreationType()
	{
		return creationType;
	}

	/**
	 * ������ ��� ��������
	 * @param creationType ��� ��������
	 */
	public void setCreationType(CreationType creationType)
	{
		this.creationType = creationType;
	}

	/**
	 * @return ����� ��������
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * ������ ����� ��������
	 * @param phone ����� ��������
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * ������� ������� �� ���������� � ������������
	 * ��� �������� ������� ����������� � ��.
	 * @param userInfo ���������� �� ������������
	 * @return ���������� �������
	 * @throws Exception
	 */
	public static Profile create(CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������������ �� ������ ���� null");
		}
		return create(userInfo, SecurityTypeHelper.calcSecurityType(userInfo));
	}

	/**
	 * ������� ������� �� ���������� � ������������
	 * ��� �������� ������� ����������� � ��.
	 * @param userInfo ���������� �� ������������
	 * @param securityType ������� ������������
	 * @return ���������� �������
	 * @throws Exception
	 */
	public static Profile create(CSAUserInfo userInfo, SecurityType securityType) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������������ �� ������ ���� null");
		}

		return create(userInfo.getFirstname(), userInfo.getPatrname(), userInfo.getSurname(), userInfo.getBirthdate(), userInfo.getPassport(), Utils.getCutTBByCBCode(userInfo.getCbCode()), securityType);
	}

	/**
	 * ������� ������� �� ���������� � ������������
	 * ��� �������� ������� ����������� � ��.
	 * @param firstname ���
	 * @param patrname ��������
	 * @param surname �������
	 * @param birthdate ���� ��������
	 * @param passport ���
	 * @param tb ��
	 * @param securityType ������� ������������
	 * @return ���������� �������
	 * @throws Exception
	 */
	public static Profile create(String firstname, String patrname, String surname, Calendar birthdate, String passport, String tb, SecurityType securityType) throws Exception
	{
		Profile profile = new Profile();
		profile.setFirstname(firstname);
		profile.setPatrname(patrname);
		profile.setSurname(surname);
		profile.setBirthdate(birthdate);
		profile.setPassport(passport);
		profile.setTb(Utils.getMainTB(tb));
		profile.setSecurityType(securityType);
		profile.save();
		return profile;
	}

	/**
	 * ������� ������� �������
	 * ���� ������� ���������� ������� ��� ��������� � �������� �������� ����� ������������� � ����.
	 * @param actualProfile �������, � �������� ��������� ������������ ��������� ���������(�������������)
	 */
	public void delete(final Profile actualProfile) throws Exception
	{
		if (actualProfile == null)
		{
			super.delete();
		}
		executeAtomic(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				log.info("�������� ��������� ����� ������� " + getId() + " � ������� " + actualProfile.getId());
				session.lock(Profile.this, LockMode.UPGRADE_NOWAIT);
				session.lock(actualProfile, LockMode.UPGRADE_NOWAIT);

				Profile oldProfile = Profile.this;
				log.trace("�������� � ������� ���������� � ������� " + oldProfile.getId());
				ProfileHistory.create(oldProfile);

				//������������� ������� ������������ �������� ������� ���� ���������� ��� � ���� ����������� �������� �������
				CSAConnector.setSecurityTypeToNotClosed(oldProfile);
				MAPIConnector.setSecurityTypeToNotClosed(oldProfile.getId(), oldProfile.getSecurityType());

				//��������������� � ����������� ������� �������� �� ��������
				Integer count = Connector.changeProfile(oldProfile, actualProfile);
				log.info("� ������� " + actualProfile.getId() + " ������������� " + count + " ����������� ������� " + oldProfile.getId());
				count = Operation.changeProfile(oldProfile, actualProfile);
				log.info("� ������� " + actualProfile.getId() + " ������������� " + count + " ������ ������� " + oldProfile.getId());
				count = ProfileHistory.changeProfile(oldProfile, actualProfile);
				log.info("� ������� " + actualProfile.getId() + " ������������� " + count + " ������� ������� ��������� ������� " + oldProfile.getId());
				count = ProfileLock.changeProfile(oldProfile, actualProfile);
				log.info("� ������� " + actualProfile.getId() + " ������������� " + count + " ������� � ����������� ������� " + oldProfile.getId());
				count = ProfileNode.changeProfile(oldProfile, actualProfile);
				log.info("� ������� " + actualProfile.getId() + " ������������� " + count + " ������� � ����� ������� � ������� " + oldProfile.getId());

				log.trace("������������� ������ ��� ������� " + actualProfile.getId());
				Password mapiPassword1 = oldProfile.getMapiPassword();
				if (mapiPassword1 != null)
				{
					Password mapiPassword2 = actualProfile.getMapiPassword();
					if (mapiPassword2 == null || mapiPassword1.getCreationDate().after(mapiPassword2.getCreationDate()))
					{
						log.info("������ �� ������ � ���������� ���������� � ������� " + oldProfile.getId() + " ���������� ������ ������� " + actualProfile.getId() + ". ��������� ������ �� ������ � ���������� ���������� ��� ������� " + actualProfile.getId());
						actualProfile.setMapiPassword(mapiPassword1);
					}
				}

				log.info("������ ������� " + oldProfile.getId());
				mergeMDMId(actualProfile.getId(), oldProfile.getId());
				delete();
				return null;
			}
		});
	}

	/**
	 * �������� ������� ����������� � ������������
	 * @param userInfo ���������� � ������������
	 */
	public void update(final CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������������ �� ������ ���� null");
		}
		executeAtomic(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				ProfileHistory.create(Profile.this);

				setFirstname(userInfo.getFirstname());
				setPatrname(userInfo.getPatrname());
				setSurname(userInfo.getSurname());
				setBirthdate(userInfo.getBirthdate());
				setPassport(userInfo.getPassport());
				//���� � ��������� ������� �������������� ������� ��=00 ����� �������� �� ��-����
				if (tb.equals("00") && userInfo.getCbCode() != null)
					setTb(Utils.getCutTBByCBCode(userInfo.getCbCode()));
				save();
				return null;
			}
		});
	}

	/**
	 * ������������� ������� ��� �����
	 * @param from ���� ������ ����������
	 * @param to ���� ��������� ����������
	 * @param reason ������� ����������
	 * @param blockerFIO ��� ����������, ������������ ������.
	 */
	public void lock(Calendar from, Calendar to, String reason, String blockerFIO) throws Exception
	{
		ProfileLock.create(this, from, to, reason, blockerFIO);
	}

	/**
	 * �������� ����� � ���������� ����� ������� � ����.
	 * ���������� ������� � ��� �� ����������.
	 * @param from ���� ������ ����������
	 * @param to ���� ��������� ����������
	 * @param reason ������� ����������
	 * @param blockerFIO ��� ����������, ������������ ������.
	 */
	public void lockForCHG071536(Calendar from, Calendar to, String reason, String blockerFIO) throws Exception
	{
		ProfileLockCHG071536.create(this, from, to, reason, blockerFIO);
	}

	/**
	 * ����� ��� ���������� � �������.
	 * ��� ������� ���������� ���������� ����������� ���� ��������� ������� ���������� ������� �����������.
	 */
	public void unlock() throws Exception
	{
		ProfileLock.unblock(this);
	}

	/**
	 * @return �������� �� ������� ������ ���������� ������������ ��� ������ ������
	 */
	public List<ProfileLock> getActiveLocks() throws Exception
	{
		return ProfileLock.findActiveByProfile(this);
	}

	/**
	 * ����� ������� �� ��������������
	 * @param profileId ������������
	 * @param lockMode ����� ����������
	 * @return ��������� ������� ��� null
	 * @throws Exception
	 */
	public static Profile findById(Long profileId, LockMode lockMode) throws Exception
	{
		return findById(Profile.class, profileId, lockMode, getInstanceName());
	}

	private static Profile getProfileByUID(final String firstname, final String patrname, final String surname, final Calendar birthdate, final String passport, final String tb, final boolean withLock) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Profile>()
		{
			public Profile run(org.hibernate.Session session) throws Exception
			{
				return (Profile) session.getNamedQuery(withLock ? "com.rssl.auth.csa.back.servises.Profile.getByUniversalId.withLock" : "com.rssl.auth.csa.back.servises.Profile.getByUniversalId")
						.setParameter("firstname", firstname)
						.setParameter("patrname", patrname)
						.setParameter("surname", surname)
						.setParameter("passport", passport)
						.setParameter("birthdate", birthdate)
						.setParameter("tb", tb)
						.uniqueResult();
			}
		});
	}

	/**
	 * �������� ������������� ������� �� ���������� � ������������
	 * @param userInfo ���������� � ������������
	 * @return id �������
	 * @throws Exception
	 */
	public static Long getProfileIdByUID(final CSAUserInfo userInfo) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Long>()
		{
			public Long run(org.hibernate.Session session) throws Exception
			{
				return (Long) session.getNamedQuery("com.rssl.auth.csa.back.servises.Profile.getProfileIdByUniversalId")
						.setParameter("firstname", userInfo.getFirstname())
						.setParameter("patrname", userInfo.getPatrname())
						.setParameter("surname", userInfo.getPatrname())
						.setParameter("passport", userInfo.getPassport())
						.setParameter("birthdate", userInfo.getBirthdate())
						.setParameter("tb", null)
						.uniqueResult();
			}
		});
	}

	private static List<Profile> getProfilesByUID(final String firstname, final String patrname, final String surname, final Calendar birthdate, final String passport, final boolean withLock) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<Profile>>()
		{
			public List<Profile> run(org.hibernate.Session session) throws Exception
			{
				//noinspection unchecked
				return (List<Profile>) session.getNamedQuery(withLock ? "com.rssl.auth.csa.back.servises.Profile.getByUniversalId.withLock" : "com.rssl.auth.csa.back.servises.Profile.getByUniversalId")
						.setParameter("firstname", firstname)
						.setParameter("patrname", patrname)
						.setParameter("surname", surname)
						.setParameter("passport", passport)
						.setParameter("birthdate", birthdate)
						.setParameter("tb", null)
						.list();
			}
		});
	}

	/**
	 * �������� ������� �� ���������� � ������������
	 * @param userInfo ���������� � �����������
	 * @param withLock ��������� �� ����������
	 * @return �������, �� ����� ���� null.
	 * @throws Exception
	 */
	public static Profile getByUserInfo(final CSAUserInfo userInfo, final boolean withLock) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������������ �� ����� ���� null");
		}

		return getProfileByUID(
				userInfo.getFirstname(),
				userInfo.getPatrname(),
				userInfo.getSurname(),
				userInfo.getBirthdate(),
				userInfo.getPassport(),
				Utils.getMainTBByCBCode(userInfo.getCbCode()),
				withLock);
	}

	/**
	 * �������� ������� �� ���������� � ������������((��� ��)
	 * @param userInfo ���������� � �����������
	 * @param withLock ��������� �� ����������
	 * @return �������, �� ����� ���� null.
	 * @throws Exception
	 */
	public static List<Profile> getByUserInfoNoTb(final CSAUserInfo userInfo, final boolean withLock) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������������ �� ����� ���� null");
		}


		return getProfilesByUID(
				userInfo.getFirstname(),
				userInfo.getPatrname(),
				userInfo.getSurname(),
				userInfo.getBirthdate(),
				userInfo.getPassport(),
				withLock);
	}

	/**
	 * �������� ������� �� �������
	 * @param template ���������� � �����������
	 * @param withLock ��������� �� ����������
	 * @return �������, �� ����� ���� null.
	 * @throws Exception
	 */
	public static Profile getByTemplate(final Profile template, final boolean withLock) throws Exception
	{
		if (template == null)
		{
			throw new IllegalArgumentException("������ ������� �� ����� ���� null");
		}

		return getProfileByUID(
				template.getFirstname(),
				template.getPatrname(),
				template.getSurname(),
				template.getBirthdate(),
				template.getPassport(),
				Utils.getMainTB(template.getTb()),
				withLock);
	}

	/**
	 * ����� ��� �������, ��� ������� ���� ��������� � �������� ������� ipas
	 * @param userId ����� iPas
	 * @return ������ �������� ��� ������ ������
	 * @throws Exception
	 */
	public static List<Profile> findByUserId(final String userId) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<Profile>>()
		{
			public List<Profile> run(org.hibernate.Session session) throws Exception
			{
				return (List<Profile>) session.getNamedQuery("com.rssl.auth.csa.back.servises.Profile.getByUserId")
						.setParameter("user_id", userId)
						.list();
			}
		});
	}

	/**
	 * ����� ��� �������, ��� ������� ���� ��������� � �������� ������
	 * @param cardNumber ����� �����
	 * @return ������ �������� ��� ������ ������
	 * @throws Exception
	 */
	public static Collection<? extends Profile> findByCardNumber(final String cardNumber) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<Profile>>()
		{
			public List<Profile> run(org.hibernate.Session session) throws Exception
			{
				return (List<Profile>) session.getNamedQuery("com.rssl.auth.csa.back.servises.Profile.getByCardNumber")
						.setParameter("card_number", cardNumber)
						.list();
			}
		});
	}

	public static boolean isExistProfile(final String cardNumber, final String userId) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Boolean>()
		{
			public Boolean run(org.hibernate.Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.Profile.isExistProfileByUserInfo")
						.setParameter("card_number", cardNumber)
						.setParameter("user_id", userId)
						.list().isEmpty();
			}
		});
	}




	/**
	 * ������������� �������(�������� ��� �������).
	 * REPLACE(PASSPORT,' ','')
	 * @param passport ��� ������������
	 * @return ��������������� �������.
	 */
	public static String normalizePassport(String passport)
	{
		if (passport == null)
		{
			return null;
		}
		return passport.replace(" ", "");
	}

	/**
	 * ������������� ��� (�������� ������������� ������� � �������� � ����������).
	 * UPPER(TRIM(REGEXP_REPLACE(SUR_NAME||' '||FIRST_NAME||' '||PATR_NAME,'( )+',' ')))
	 * @param surname �������
	 * @param firstname ���
	 * @param patrname ��������
	 * @return ��������������� ���.
	 */
	public static String normalizeFIO(String surname, String firstname, String patrname)
	{
		if (surname == null && firstname == null && patrname == null)
		{
			return null;
		}
		String fio = StringHelper.getEmptyIfNull(surname) + " " + StringHelper.getEmptyIfNull(firstname) + " " + StringHelper.getEmptyIfNull(patrname);

		return fio.trim().replaceAll("( )+", " ").toUpperCase();
	}

	public SecurityType getSecurityType()
	{
		return securityType;
	}

	public void setSecurityType(SecurityType securityType)
	{
		this.securityType = securityType;
	}

	/**
	 * ��������� ������ ������� ���� �������
	 * @param profileTemlate - ������ �������
	 * @return ������ ������� ����
	 * @throws Exception
	 */
	public static List<String> getCardNumberList(final Profile profileTemlate) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<String>>()
			{
				public List<String> run(org.hibernate.Session session) throws Exception
				{
					return (List<String>) session.getNamedQuery("com.rssl.auth.csa.back.servises.Profile.getCardNumberList")
							.setParameter("firstname", profileTemlate.getFirstname())
							.setParameter("patrname", profileTemlate.getPatrname())
							.setParameter("surname", profileTemlate.getSurname())
							.setParameter("passport", profileTemlate.getPassport())
							.setParameter("birthdate", profileTemlate.getBirthdate())
							.setParameter("tb", profileTemlate.getTb())
							.list();
				}
			});
	}

	/**
	 * ���������� ������� ������� �� ���
	 * @param profileId ������������� �������
	 * @param mdmId ������������� � ���
	 * @throws Exception
	 */
	public static void updateClientMDMInfo(final Long profileId, final String mdmId) throws Exception
	{
		ExecutorQuery queryUpdate = new ExecutorQuery(getHibernateExecutor(), "com.rssl.auth.csa.back.servises.Profile.updateClientMDMInfo");
		queryUpdate.setParameter("PROFILE_ID", profileId);
		queryUpdate.setParameter("MDM_ID", mdmId);
		if(queryUpdate.executeUpdate() != 0)
			return;

		ExecutorQuery queryInsert = new ExecutorQuery(getHibernateExecutor(), "com.rssl.auth.csa.back.servises.Profile.insertClientMDMInfo");
		queryInsert.setParameter("PROFILE_ID", profileId);
		queryInsert.setParameter("MDM_ID", mdmId);
		queryInsert.executeUpdate();
	}

	/**
	 * ���������� ������� ������� �� ���
	 * @param actualProfileId ������������� �������
	 * @param oldProfileId ������������� ���������� �������
	 */
	public static void mergeMDMId(Long actualProfileId, Long oldProfileId)
	{
		try
		{
			ExecutorQuery queryUpdate = new ExecutorQuery(getHibernateExecutor(), "com.rssl.auth.csa.back.servises.Profile.mergeMDMId");
			queryUpdate.setParameter("NEW_PROFILE_ID", actualProfileId);
			queryUpdate.setParameter("OLD_PROFILE_ID", oldProfileId);
			queryUpdate.executeUpdate();
		}
		catch (Exception e)
		{
			log.error("�� ������� �������� ������������� ���", e);
		}
	}

	/**
	 * ��������� ��� �� ������� ��� ���������� ��������� �����������
	 * @return ���������� �� ���
	 * @throws Exception
	 */
	public boolean lockForExecuteDocument() throws Exception
	{
		return ProfileNode.lockProfileForExecuteDocument(this);
	}

	/**
	 * ����� ��� � ������� ����� ���������� ��������� �����������
	 * @return ������ �� ���
	 * @throws Exception
	 */
	public boolean unlockForExecuteDocument() throws Exception
	{
		return ProfileNode.unlockProfileForExecuteDocument(this);
	}

}
