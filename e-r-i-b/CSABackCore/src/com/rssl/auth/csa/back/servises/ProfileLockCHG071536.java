package com.rssl.auth.csa.back.servises;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 24.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Блокировка профиля клиента для запроса CHG071536
 * По сути та же блокировка, что и ProfileLock, но на неё никак не смотрим в механизме входа.
 * Создана специально для 13.03 так как боимся что-то отломать.
 */
public class ProfileLockCHG071536 extends ActiveRecord
{
	private Long id;
	private Long profileId;
	private Calendar from;
	private Calendar to;
	private String reason;
	private String lockerFIO;
	private Calendar creationDate;

	/**
	 * Дефолтный конструктор для hibernate
	 */
	public ProfileLockCHG071536()
	{
	}

	/**
	 * Конструктор
	 * @param profileId - идентификатор профиля
	 * @param from - дата начала блокировки
	 * @param to - дата окончания блокировки
	 * @param reason - причина блокировки
	 * @param lockerFIO - ФИО сотрудника, заблокировавшего профиль
	 */
	public ProfileLockCHG071536(Long profileId, Calendar from, Calendar to, String reason, String lockerFIO)
	{
		this.profileId = profileId;
		this.from = from;
		this.to = to;
		this.reason = reason;
		this.lockerFIO = lockerFIO;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	public Calendar getFrom()
	{
		return from;
	}

	public void setFrom(Calendar from)
	{
		this.from = from;
	}

	public Calendar getTo()
	{
		return to;
	}

	public void setTo(Calendar to)
	{
		this.to = to;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}

	public String getLockerFIO()
	{
		return lockerFIO;
	}

	public void setLockerFIO(String lockerFIO)
	{
		this.lockerFIO = lockerFIO;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * Создать блокировку для профиля
	 * @param profile профиль, для которого требуется создание блокировки
	 * @param from таймстамп начала блокировки
	 * @param to таймстамп окончания блокировки(отсутсвует для бессрочных)
	 * @param reason причина блокировки
	 * @param lockerFIO ФИО сотрудника, блокируюющего пользователя
	 * @return запись о блокировке
	 * @throws Exception
	 */
	public static ProfileLockCHG071536 create(final Profile profile, final Calendar from, final Calendar to, final String reason, final String lockerFIO) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		return executeAtomic(new HibernateAction<ProfileLockCHG071536>()
		{
			public ProfileLockCHG071536 run(org.hibernate.Session session) throws Exception
			{
				ProfileLockCHG071536 lock = new ProfileLockCHG071536(profile.getId(),from,to,reason,lockerFIO);
				lock.save();
				return lock;
			}
		});
	}

	/**
	 /**
	 * Снять все блокировки с профиля.
	 * Под снятием блокировки понимается простановка даты окончания дествия блокировки текущим таймстампом.
	 *
	 * @param profile профиль для которого требуется нять блокировки
	 * @return количество обновленных записей
	 * @throws Exception
	 */
	public static Integer unlock(final Profile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(org.hibernate.Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileLockCHG071536.unlock")
						.setParameter("profile_id", profile.getId())
						.executeUpdate();
			}
		});
	}
}
