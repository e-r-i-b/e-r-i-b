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
 * ������ ������ � ������������ � ����� � �������������� IP
 */

public class UnusualIPNotificationService
{
	private static final SimpleService service = new SimpleService();

	/**
	 * �������� �������� ������ ����������
	 * @param startDate ����������� ���� ����������
	 * @param maxAttemptsCount ������������ ����� ��������� ����������
	 * @return ��������
	 */
	public UnusualIPNotificationIterator getNotificationsIterator(Calendar startDate, long maxAttemptsCount)
	{
		return new UnusualIPNotificationIterator(startDate, maxAttemptsCount);
	}

	/**
	 * �������� ������ ����������
	 * @param startDate ����������� ���� ����������
	 * @param startId ����������� ������������� ����������
	 * @param maxAttemptsCount ������������ ����� ��������� ����������
	 * @param batchSize ���������� �������
	 * @return ������ ����������
	 *
	 *
        ������� ������: I_UNUSUAL_IP_NOTIFICATIONS
        �������� �������: access("DATE_CREATED">=TO_TIMESTAMP(:D))
        ��������������: batchSize (����� � ���� 1000)
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
	 * �������� ����������
	 * @param notification ����������� ����������
	 * @throws BusinessException
	 */
	void update(UnusualIPNotification notification) throws BusinessException
	{
		service.update(notification);
	}

	/**
	 * ������� ����������
	 * @param notificationId ������������� ��������� ����������
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
			throw new BusinessException("������ �������� ���������� ������� � ����� � �������������� IP.", e);
		}
	}
}
