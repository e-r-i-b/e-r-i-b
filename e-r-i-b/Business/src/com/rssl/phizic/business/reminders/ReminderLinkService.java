package com.rssl.phizic.business.reminders;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;

import java.util.Collection;
import java.util.List;

/**
 * @author osminin
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с линками напоминаний
 */
public class ReminderLinkService
{
	private static final SimpleService simpleService = new SimpleService();

	public void addOrUpdate(ReminderLink reminderLink) throws BusinessException
	{
		simpleService.addOrUpdate(reminderLink);
	}

	/**
	 * Найти линк напоминания по логину и идетификатору
	 * @param loginId логин клиента
	 * @param reminderId идентификатор напоминаия
	 * @return линк
	 * @throws BusinessException
	 */
	public ReminderLink getByLoginAndId(final Long loginId, final Long reminderId) throws BusinessException
	{
		if (loginId == null)
		{
			throw new IllegalArgumentException("Логин клиента не может быть null");
		}
		if (reminderId == null)
		{
			throw new IllegalArgumentException("Идентификатор напоминания не может быть null");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ReminderLink>()
			{
				public ReminderLink run(Session session) throws Exception
				{
					return (ReminderLink) session.getNamedQuery("com.rssl.phizic.business.reminders.ReminderLink.findByLoginAndId")
							.setParameter("login_id", loginId)
							.setParameter("reminder_id", reminderId)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить линки напоминаний по логину
	 * @param loginId идентификатор логина
	 * @return список линков
	 * @throws BusinessException
	 */
	public List<ReminderLink> getByLogin(final Long loginId) throws BusinessException
	{
		if (loginId == null)
		{
			throw new IllegalArgumentException("Логин клиента не может быть null");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ReminderLink>>()
			{
				public List<ReminderLink> run(Session session) throws Exception
				{
					return (List<ReminderLink>) session.getNamedQuery("com.rssl.phizic.business.reminders.ReminderLink.findByLogin")
							.setParameter("login_id", loginId)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удалить напоминание по логину и идентификатору шаблона
	 * @param reminderId идентификатор шаблона с напоминанием
	 * @param loginId идентификатор логина
	 * @throws BusinessException
	 */
	public void deleteById(final Long reminderId, final Long loginId) throws BusinessException
	{
		if (loginId == null)
		{
			throw new IllegalArgumentException("Логин клиента не может быть null");
		}
		if (reminderId == null)
		{
			throw new IllegalArgumentException("Идентификатор напоминания не может быть null");
		}

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizic.business.reminders.ReminderLink.deleteByLoginAndId")
							.setParameter("login_id", loginId)
							.setParameter("reminder_id", reminderId)
							.executeUpdate();

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
