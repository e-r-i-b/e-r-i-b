package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 08.08.2013
 * @ $Author$
 * @ $Revision$
 * История изменений идентификационных данных пользователя
 * История хранит предыдущие идентифицирующие пользователя данные с ссылкой на профиль, содержащий актуальные данные
 * и датой устаревания этих данных (дата помещения идентификационных данных пользователя в архив).
 * История не содержит актуальных данных пользователя. Актуальные данные содержатся в профиле.
 */

public class ProfileHistory extends ActiveRecord
{
	private long id;
	private long profileId;
	private String firstname;
	private String surname;
	private String patrname;
	private Calendar birthdate;
	private String passport;
	private Calendar expireDate;
	private String tb;

	/**
	 * @return идентфификатор записи в истории
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id идентфификатор записи в истории
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return идентифкатор профиля, содержащего актуальне идентифицирующие данные пользователя
	 */
	public long getProfileId()
	{
		return profileId;
	}

	/**
	 * @param profileId идентифкатор профиля, содержащего актуальне идентифицирующие данные пользователя
	 */
	public void setProfileId(long profileId)
	{
		this.profileId = profileId;
	}

	/**
	 * @return имя пользователя
	 */
	public String getFirstname()
	{
		return firstname;
	}

	/**
	 * @param firstname имя пользователя
	 */
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	/**
	 * @return фамилия пользователя
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * @param surname фамилия пользователя
	 */
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * @return отчество пользователя
	 */
	public String getPatrname()
	{
		return patrname;
	}

	/**
	 * @param patrname отчество пользователя
	 */
	public void setPatrname(String patrname)
	{
		this.patrname = patrname;
	}

	/**
	 * @return  ДР пользователя
	 */
	public Calendar getBirthdate()
	{
		return birthdate;
	}

	/**
	 * @param birthdate ДР пользователя
	 */
	public void setBirthdate(Calendar birthdate)
	{
		this.birthdate = birthdate;
	}

	/**
	 * @return паспорт WAY пользователя
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * @param passport паспорт WAY пользователя
	 */
	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	/**
	 * @return тербанк пользователя
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @param tb тербанк пользователя
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * Получить дату окончания действия идентификационных данных пользователя
	 * @return дата окончания действия идентификационных данных пользователя
	 */
	public Calendar getExpireDate()
	{
		return expireDate;
	}

	/**
	 * Установить дату окончания действия идентификационных данных пользователя
	 * @param expireDate дата окончания действия  идентификационных данных пользователя
	 */
	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}


	/**
	 * Создать для профиля запись в истории
	 * @param profile проифиль
	 * @return созданная в истори запись
	 */
	public static ProfileHistory create(Profile profile) throws Exception
	{
		ProfileHistory historyRecord = createInternal(profile);
		historyRecord.save();
		return  historyRecord;

	}

	/**
	 * Создание истории профиля без помещения в базу.
	 * @param profile профиль.
	 * @return история профиля.
	 */
	private static ProfileHistory createInternal(Profile profile)
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не должен быть null");
		}

		ProfileHistory historyRecord = new ProfileHistory();
		historyRecord.setProfileId(profile.getId());
		historyRecord.setFirstname(profile.getFirstname());
		historyRecord.setPatrname(profile.getPatrname());
		historyRecord.setSurname(profile.getSurname());
		historyRecord.setBirthdate(profile.getBirthdate());
		historyRecord.setPassport(profile.getPassport());
		historyRecord.setTb(profile.getTb());

		return historyRecord;
	}

	private static ProfileHistory createInternal(CSAUserInfo userInfo, Long profileId)
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("Информация о пользователе не может быть null");
		}
		if (profileId == null)
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");
		}
		ProfileHistory historyRecord = new ProfileHistory();
		historyRecord.setProfileId(profileId);
		historyRecord.setFirstname(userInfo.getFirstname());
		historyRecord.setSurname(userInfo.getSurname());
		historyRecord.setPatrname(userInfo.getPatrname());
		historyRecord.setBirthdate(userInfo.getBirthdate());
		historyRecord.setPassport(userInfo.getPassport());
		historyRecord.setTb(Utils.getTBByCbCode(userInfo.getCbCode()));

		return historyRecord;
	}

	/**
	 * Перепривязать историю изменений профилей от профиля oldProfile к профилю actualProfile.
	 * @param oldProfile - профиль, от которого отвязывается история
	 * @param actualProfile - профиль, к которому перепривязывается история
	 * @return Количество перепривязанных записей. 0 - если ни один запись не перепривязан
	 */
	public static Integer changeProfile(final Profile oldProfile, final Profile actualProfile) throws Exception
	{
		if (oldProfile == null)
		{
			throw new IllegalArgumentException("Старый профиль не может быть null");
		}
		if (actualProfile == null)
		{
			throw new IllegalArgumentException("Новый профиль не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileHistory.changeProfile")
						.setParameter("old_profile", oldProfile.getId())
						.setParameter("new_profile", actualProfile.getId())
						.executeUpdate();
			}
		});
	}

	/**
	 * Возвращает полную историю изменений профиля (с текущим состоянием).
	 * @param userInfo текущий профиль.
	 * @return история изменений.
	 * @throws Exception
	 */
	public static List<ProfileHistory> getFullHistoryFor(final CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("UserInfo не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<ProfileHistory>>()
		{
			public List<ProfileHistory> run(Session session) throws Exception
			{
				List<ProfileHistory> history = getHistoryFor(userInfo);

				if (CollectionUtils.isEmpty(history))
				{
					Profile profile = Profile.getByUserInfo(userInfo, true);

					if (profile != null)
					{
						history.add(ProfileHistory.createInternal(userInfo, profile.getId()));
					}
				}
				else
				{
					history.add(ProfileHistory.createInternal(userInfo, history.get(0).getProfileId()));
				}

				return history;
			}
		});
	}

	/**
	 * Получить только историю изменений профиля без текущего состояния
	 * @param userInfo информация о пользователе
	 * @return история изменений
	 * @throws Exception
	 */
	public static List<ProfileHistory> getHistoryFor(final CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("Информация о пользователе не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<ProfileHistory>>()
		{
			public List<ProfileHistory> run(Session session) throws Exception
			{
				return (List<ProfileHistory>) session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileHistory.getHistoryByUserInfo")
						.setParameter("firstname", userInfo.getFirstname())
						.setParameter("patrname", userInfo.getPatrname())
						.setParameter("surname", userInfo.getSurname())
						.setParameter("passport", userInfo.getPassport())
						.setParameter("birthdate", userInfo.getBirthdate())
						.setParameter("tb", Utils.getTBByCbCode(userInfo.getCbCode()))
						.list();
			}
		});
	}

	/**
	 * Найти историю для профиля
	 * @param profile - профиль для которого необходимо найти историю
	 * @return история
	 * @throws Exception
	 */
	public static List<ProfileHistory> findHistoryForProfile(final Profile profile) throws Exception
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ProfileHistory.class);
		criteria.add(Expression.eq("profileId",profile.getId()));
		return find(criteria, LockMode.NONE);
	}

	/**
	 * Найти запись в истории по шаблону профиля.
	 * @param profile - шаблон профиля
	 * @return Запись из истории. Если по указаному шаблону будет найдено больше, чем одна история, то возвращаем 1-ю попавшуюся.
	 * @throws Exception
	 */
	public static ProfileHistory findHistoryByProfileTemplate(final Profile profile) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<ProfileHistory>()
				{
					public ProfileHistory run(org.hibernate.Session session) throws Exception
					{
						List<ProfileHistory> profileHistoryList = (List<ProfileHistory>)session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileHistory.find")
								.setParameter("firstname", profile.getFirstname())
								.setParameter("patrname", profile.getPatrname())
								.setParameter("surname", profile.getSurname())
								.setParameter("passport", profile.getPassport())
								.setParameter("birthdate", profile.getBirthdate())
								.setParameter("tb", profile.getTb())
								.list();
						return profileHistoryList.size() > 0 ? profileHistoryList.get(0) : null;
					}
				});
	}
}
