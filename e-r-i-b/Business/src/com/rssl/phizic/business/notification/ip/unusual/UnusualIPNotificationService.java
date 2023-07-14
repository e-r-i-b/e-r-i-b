package com.rssl.phizic.business.notification.ip.unusual;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с оповещениями о входе с нестандартного IP
 */

public class UnusualIPNotificationService
{
	private static final SimpleService service = new SimpleService();

	/**
	 * получить итератор списка оповещений
	 * @param startDate минимальная дата оповещения
	 * @param maxAttemptsCount максимальное число обработок оповещения
	 * @return итератор
	 */
	public UnusualIPNotificationIterator getNotificationsIterator(Calendar startDate, long maxAttemptsCount)
	{
		return new UnusualIPNotificationIterator(startDate, maxAttemptsCount);
	}

	/**
	 * получить список оповещений
	 * @param startDate минимальная дата оповещения
	 * @param startId минимальный идентификатор оповещения
	 * @param maxAttemptsCount максимальное число обработок оповещения
	 * @param batchSize количество записей
	 * @return список оповещений
	 *
	 *
        Опорный объект: I_UNUSUAL_IP_NOTIFICATIONS
        Предикат доступа: access("DATE_CREATED">=TO_TIMESTAMP(:D))
        Кардинальность: batchSize (задан в коде 1000)
	 */
	List<UnusualIPNotification> getNotifications(Calendar startDate, Long startId, long maxAttemptsCount, int batchSize) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(UnusualIPNotification.class);
		criteria.add(Expression.ge("dateCreated", startDate));
		criteria.add(Expression.gt("id", startId));
		criteria.add(Expression.lt("attemptsCount", maxAttemptsCount));
		criteria.addOrder(Order.asc("dateCreated"));
		return service.find(criteria, batchSize);
	}

	/**
	 * обновить оповещение
	 * @param notification обновляемое оповещение
	 * @throws BusinessException
	 */
	void update(UnusualIPNotification notification) throws BusinessException
	{
		service.update(notification);
	}

	/**
	 * удалить оповещение
	 * @param notificationId идентификатор удаляемое оповещение
	 * @throws BusinessException
	 */
	void remove(final Long notificationId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.notification.ip.unusual.UnusualIPNotification.removeById");
					query.setParameter("id", notificationId);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка удаления оповещения клиента о входе с нестандартного IP.", e);
		}
	}
}
