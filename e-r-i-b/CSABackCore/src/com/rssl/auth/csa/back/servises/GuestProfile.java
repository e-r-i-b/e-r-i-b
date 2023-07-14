package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.servises.restrictions.security.CSAPasswordSecurityRestriction;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.*;
import org.hibernate.Session;
import org.hibernate.criterion.*;

import java.util.Calendar;

/**
 * @author niculichev
 * @ created 19.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestProfile extends ActiveRecord
{
	private Long id;
	private String phone;
	private Long code;
	private Calendar blockedUntil;
	private long authErrors = 0;
	private String firstname;
	private String patrname;
	private String surname;
	private Calendar birthdate;
	private String passport;
	private String tb;
	private boolean block; // флажок для рантайма, указываюий на заблокированность профиля
	private String login; // значение логина

	public GuestProfile()
	{
	}

	public GuestProfile(String phone, Long code)
	{
		this.phone = phone;
		this.code = code;
	}

	public GuestProfile(String phone, Long code, String firstname, String patrname, String surname, Calendar birthdate, String passport, String tb)
	{
		this.phone = phone;
		this.code = code;
		this.firstname = firstname;
		this.patrname = patrname;
		this.surname = surname;
		this.birthdate = birthdate;
		this.passport = passport;
		this.tb = tb;
	}

	public void updateByTemplate(GuestProfile template) throws Exception
	{
		this.code = template.getCode();
		this.firstname = template.getFirstname();
		this.patrname = template.getPatrname();
		this.surname = template.getSurname();
		this.birthdate = template.getBirthdate();
		this.passport = template.getPassport();
		this.tb = template.getTb();
		save();
	}


	public void changePassword(String passwordValue) throws Exception
	{
		CSAPasswordSecurityRestriction.getInstance(this).check(passwordValue);
		setPassword(passwordValue);
	}


	/**
	 * Установить пароль для профиля
	 * @param passwordValue значение пароля
	 * @return изменен ли пароль
	 * @throws Exception
	 */
	protected boolean setPassword(final String passwordValue) throws Exception
	{
		return executeAtomic(new HibernateAction<Boolean>()
		{
			public Boolean run(org.hibernate.Session session) throws Exception
			{
				session.lock(GuestProfile.this, LockMode.UPGRADE_NOWAIT);
				boolean isChanged = false;

				GuestPassword prevPassword = GuestPassword.findActiveByProfileId(GuestProfile.this.getId());
				if (prevPassword != null)
				{
					isChanged = !prevPassword.check(passwordValue);
					prevPassword.delete();
				}

				GuestPassword.create(passwordValue, GuestProfile.this.getId());
				return isChanged;
			}
		});
	}

	/**
	 * Создать гостеовой профиль с сохранением в базе
	 * @param phone номер телефона
	 * @return сохраненный гостевой профиль
	 * @throws Exception
	 */
	public static GuestProfile createProfile(String phone) throws Exception
	{
		GuestProfile profile = new GuestProfile();
		profile.setPhone(phone);
		profile.save();

		return profile;
	}

	/**
	 * Найти гостевой профиль по номеру телефона
	 * @param phone номер телефона
	 * @return профиль
	 * @throws Exception
	 */
	public static GuestProfile findByPhone(final String phone) throws Exception
	{
		return executeAtomic(new HibernateAction<GuestProfile>()
		{
			public GuestProfile run(Session session) throws Exception
			{
				Criteria criteria = session.createCriteria(GuestProfile.class);
				criteria.add(Restrictions.eq("phone", phone));
				return (GuestProfile) criteria.uniqueResult();
			}
		});
	}

	public static GuestProfile findByPhoneWithLock(final String phone) throws Exception
	{
		return executeAtomic(new HibernateAction<GuestProfile>()
		{
			public GuestProfile run(Session session) throws Exception
			{
				Criteria criteria = session.createCriteria(GuestProfile.class);
				criteria.add(Restrictions.eq("phone", phone));
				criteria.setLockMode(LockMode.UPGRADE_NOWAIT);
				return (GuestProfile) criteria.uniqueResult();
			}
		});
	}

	/**
	 * Найти гостевой профиль по логину
	 * @param login логин
	 * @return гостевой профиль
	 * @throws Exception
	 */
	public static GuestProfile findByLogin(final String login) throws Exception
	{
		return executeAtomic(new HibernateAction<GuestProfile>()
		{
			public GuestProfile run(org.hibernate.Session session) throws Exception
			{
				return (GuestProfile) session.getNamedQuery("com.rssl.auth.csa.back.servises.GuestProfile.getByLogin")
						.setParameter("login", login)
						.uniqueResult();
			}
		});
	}

	/**
	 * Получить идентификатор гостевого профиля по номеру телефона
	 * @param phone номер телефона
	 * @return first - id гостевого профиля, second - code гостевого профиля
	 * @throws Exception
	 */
	public static Pair<Long, Long> getIdAndCodeByPhone(final String phone) throws Exception
	{
		return executeAtomic(new HibernateAction<Pair<Long, Long>>()
		{
			public Pair<Long, Long> run(Session session) throws Exception
			{
				Criteria criteria = session.createCriteria(GuestProfile.class);
				criteria.add(Restrictions.eq("phone", phone));

				ProjectionList projection = Projections.projectionList();
				projection.add(Property.forName("id"));
				projection.add(Property.forName("code"));
				criteria.setProjection(projection);

				Object[] fields = (Object[])criteria.uniqueResult();
				return new Pair<Long, Long>((Long)fields[0], (Long)fields[1]);
			}
		});
	}

	/**
	 * Найти профиль по идентификатору
	 * @param profileId идентифкатор
	 * @param lockMode режим блокировки
	 * @return найденный профиль или null
	 * @throws Exception
	 */
	public static GuestProfile findById(Long profileId, LockMode lockMode) throws Exception
	{
		return findById(GuestProfile.class, profileId, lockMode, getInstanceName());
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public Long getCode()
	{
		return code;
	}

	public void setCode(Long code)
	{
		this.code = code;
	}

	public Calendar getBlockedUntil()
	{
		return blockedUntil;
	}

	public void setBlockedUntil(Calendar blockedUntil)
	{
		this.blockedUntil = blockedUntil;
	}

	public long getAuthErrors()
	{
		return authErrors;
	}

	public void setAuthErrors(long authErrors)
	{
		this.authErrors = authErrors;
	}

	public void incrementAuthErrors()
	{
		this.authErrors++;
	}

	public void clearAuthErrors()
	{
		this.authErrors = 0;
	}

	public boolean checkBlock()
	{
		return getBlockedUntil() != null && getCurrentDate().before(getBlockedUntil());
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

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setBlock(boolean block)
	{
		this.block = block;
	}

	public boolean isBlock()
	{
		return block;
	}

	/**
	 * Сгенерировать гостевой код
	 * @return гостевой код
	 * @throws Exception
	 */
	public static long generateCode() throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Long>()
		{
			public Long run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.GuestProfile.generateCode");
				return (Long) query.uniqueResult();
			}
		});
	}

	public String getLogin() throws Exception
	{
		if(login == null)
			login = Login.getLoginByGuestId(getId());

		return login;
	}

	/**
	 * Установить значение логина профилю
	 * @param login значение логина
	 * @throws Exception
	 * @deprecated логин хранится только в рантайме, маппинга никакого нет
	 */
	@Deprecated
	public void setLogin(String login) throws Exception
	{
		this.login = login;
	}
}
