package com.rssl.phizic.rsa.notifications;

import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.rsa.exceptions.AppendNotificationException;
import com.rssl.phizic.rsa.integration.jms.FraudMonitoringJMSNotificationSender;
import com.rssl.phizic.rsa.notifications.enumeration.FraudNotificationState;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Сервис для работы с оповещениями
 *
 * @author khudyakov
 * @ created 16.06.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudNotificationService extends DatabaseServiceBase
{
	private static final FraudNotificationService INSTANCE = new FraudNotificationService();

	/**
	 * @return INSTANCE
	 */
	public static FraudNotificationService getInstance()
	{
		return INSTANCE;
	}

	private FraudNotificationService(){};

	/**
	 * Добавить оповещение
	 * @param notification оповещение
	 */
	public void save(FraudNotification notification) throws AppendNotificationException
	{
		try
		{
			super.add(notification, null);
		}
		catch (Exception e)
		{
			throw new AppendNotificationException(e);
		}
	}

	/**
	 * Добавить оповещение
	 * @param notification оповещение
	 * @param clientTransactionId идентификатор клиентской транзакции
	 */
	public void send(FraudNotification notification, String clientTransactionId) throws AppendNotificationException
	{
		try
		{
			FraudMonitoringJMSNotificationSender.sendToQueue(notification.getRequestBody(), clientTransactionId);
		}
		catch (Exception e)
		{
			throw new AppendNotificationException(e);
		}
	}

	/**
	 * Получить пачку идентификатор для фрод-оповещений
	 * @param limit - размер пачки
	 * @param maxLimit - максимальный лимит, по которому производится вложенная выборка, до рандомизации порядка
	 * @return
	 * @throws Exception
	 */
	public List<Long> getNotificationPackIds(final int limit, final int maxLimit) throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
		{
			public List<Long> run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.phizic.rsa.notifications.getPackIds")
						.setParameter("limit", limit)
						.setParameter("max_limit", maxLimit)
						.list();
			}
		});
	}

	/**
	 * Получить фрод-оповещение по идентификатору
	 * @param id - оповещение
	 * @return
	 * @throws Exception
	 */
	public FraudNotification getNotification(final long id) throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<FraudNotification>()
		{
			public FraudNotification run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizic.rsa.notifications.getNotification");
				query.setParameter("id", id);
				return (FraudNotification) query.uniqueResult();
			}
		});
	}

	/**
	 * Обновить статус оповещения
	 * @param id - идентификатор оповещения
	 * @param state - статус
	 * @throws Exception
	 */
	public void updateState(final long id, final FraudNotificationState state) throws Exception
	{
		ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.rsa.notifications.updateState");
		query.setParameter("id", id);
		query.setParameter("state", state.name());
		query.executeUpdate();
	}


}
