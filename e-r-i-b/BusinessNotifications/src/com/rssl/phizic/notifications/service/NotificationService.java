package com.rssl.phizic.notifications.service;

import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.notifications.BusinessNotificationException;
import com.rssl.phizic.notifications.Notification;
import com.rssl.phizic.notifications.impl.PaymentExecuteNotification;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * @author eMakarov
 * @ created 28.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class NotificationService extends DatabaseServiceBase
{

	/**
	 * Сохранить оповещение.
	 * @param notification оповещение
	 * @throws BusinessNotificationException
	 */
	public void save(final Notification notification) throws BusinessNotificationException
	{
		try
		{
			add(notification,  null);
		}
		catch (Exception e)
		{
			throw new BusinessNotificationException(e);
		}
	}

	/**
	 * Возвращает случайно выбранный логин пользователя из таблицы оповещений
	 * @param startDate - дата, с которой начинается выборка записей
	 * @return id логина пользователя
	 */
	public List<Long> getLogins4Notification(final Calendar startDate) throws BusinessNotificationException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
			{
				public List<Long> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.notifications.service.NotificationService.getNotifiedLogins");
					query.setParameter("startDate", startDate);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessNotificationException(e);
		}
	}

	/**
	 * Удалить оповещения.
	 * @param notifications оповещения
	 */
	public void delete(List<? extends Notification> notifications) throws Exception
	{
		deleteAll(notifications, null);
	}

	/**
	 * Удаление всех оповещений об исполнении операций для клиента
	 * @param loginId - id логина клиента
	 */
	public void deletePaymentNotifications4Login(final Long loginId) throws BusinessNotificationException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.notifications.service.NotificationService.deletePaymentNotifications4Login");
					query.setParameter("loginId", loginId);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessNotificationException(e);
		}
	}

	/**
	 * Возвращает список оповещений об исполнении операций для клиента
	 * @param loginId - id логина клиента
	 * @param startDate - дата, с которой начинается выборка записей
	 * @return
	 */
	public List<PaymentExecuteNotification> findPaymentNotifications(final Long loginId, final Calendar startDate) throws BusinessNotificationException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<PaymentExecuteNotification>>()
			{
				public List<PaymentExecuteNotification> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.notifications.service.NotificationService.findPaymentNotifications");
					query.setParameter("loginId", loginId);
					query.setParameter("startDate", startDate);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessNotificationException(e);
		}
	}
}
