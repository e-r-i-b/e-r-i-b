package com.rssl.auth.csa.back.servises;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.*;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 13.08.2013
 * @ $Author$
 * @ $Revision$
 * Записль о блокировке пользователя
 */

public class ProfileLock extends ActiveRecord
{

	private Long id;
	private Long profileId;
	private Calendar from;
	private Calendar to;
	private String reason;
	private String lockerFIO;
	private Calendar creationDate;

	/**
	 * @return идентификатор записи
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор записи
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @param profileId идентификатор профиля
	 */
	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	/**
	 * @return идентификатор профиля
	 */
	public Long getProfileId()
	{
		return profileId;
	}

	/**
	 * @param from дата начала действия блокировки
	 */
	public void setFrom(Calendar from)
	{
		this.from = from;
	}

	/**
	 * @return дата начала действия блокировки
	 */
	public Calendar getFrom()
	{
		return from;
	}

	/**
	 * @param to дата окончания действия блокировки(null для бессрочных)
	 */
	public void setTo(Calendar to)
	{
		this.to = to;
	}

	/**
	 * @return дата окончания действия блокировки(null для бессрочных)
	 */
	public Calendar getTo()
	{
		return to;
	}

	/**
	 * @param reason причина блокировки
	 */
	public void setReason(String reason)
	{
		this.reason = reason;
	}

	/**
	 * @return причина блокировки
	 */
	public String getReason()
	{
		return reason;
	}

	/**
	 * @param blockerFIO ФИО сотрудника, блокируюющего пользователя
	 */
	public void setLockerFIO(String blockerFIO)
	{
		this.lockerFIO = blockerFIO;
	}

	/**
	 * @return ФИО сотрудника, блокируюющего пользователя
	 */
	public String getLockerFIO()
	{
		return lockerFIO;
	}

	/**
	 * @return дата создания блокировки
	 */
	Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate дата создания блокировки
	 */
	void setCreationDate(Calendar creationDate)
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
	public static ProfileLock create(final Profile profile, final Calendar from, final Calendar to, final String reason, final String lockerFIO) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		return executeAtomic(new HibernateAction<ProfileLock>()
		{
			public ProfileLock run(Session session) throws Exception
			{
				session.lock(profile, LockMode.UPGRADE_NOWAIT);
				ProfileLock block = new ProfileLock();
				block.setProfileId(profile.getId());
				block.setFrom(from);
				block.setTo(to);
				block.setReason(reason);
				block.setLockerFIO(lockerFIO);

				block.save();
				return block;
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
	public static Integer unblock(final Profile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileLock.unblock")
						.setParameter("profile_id", profile.getId())
						.executeUpdate();
			}
		});
	}

	/**
	 * Получить список активных на текущий момент блокировок профиля пользователя
	 *
	 * @param profile профиль пользователя
	 * @return список аутивных блокировк или пустой
	 */
	public static List<ProfileLock> findActiveByProfile(final Profile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<ProfileLock>>()
		{
			public List<ProfileLock> run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileLock.findActiveByProfile")
						.setParameter("profile_id", profile.getId())
						.list();
			}
		});
	}
	/**
	 * Перепривязать блокировки от профиля oldProfile к профилю actualProfile.
	 * @param oldProfile - профиль, от которого отвязываются блокировки
	 * @param actualProfile - профиль, к которому перепривязывается блокировки
	 * @return Количество перепривязанных блокировок. 0 - если ни одна блокировка не перепривязан
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
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.ProfileLock.changeProfile")
						.setParameter("old_profile", oldProfile.getId())
						.setParameter("new_profile", actualProfile.getId())
						.executeUpdate();
			}
		});
	}

}
