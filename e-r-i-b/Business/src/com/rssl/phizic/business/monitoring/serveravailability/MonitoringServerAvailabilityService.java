package com.rssl.phizic.business.monitoring.serveravailability;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * ������ ��� ������ � ��������� ������� ����������� ��
 * @author gladishev
 * @ created 23.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class MonitoringServerAvailabilityService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ��������/�������� ������ � ������� ��
	 * @param idleTime - �������� ServerIdleTime
	 * @return ������ � ������� ��
	 */
	public ServerIdleTime addOrUpdateServerIdleTime(ServerIdleTime idleTime) throws BusinessException
	{
		return simpleService.addOrUpdate(idleTime);
	}

	/**
	 * ��������/�������� ������ � ������� ������ ��
	 * @param workTime - �������� ServerCommonWorkTime
	 * @return ServerCommonWorkTime
	 */
	public ServerCommonWorkTime addOrUpdateServerCommonWorkTime(ServerCommonWorkTime workTime) throws BusinessException
	{
		return simpleService.addOrUpdate(workTime);
	}

	/**
	 * ���������� �������� ����� ����� ������ ��
	 * @param serverId - id ��
	 * @return ServerCommonWorkTime
	 */
	public ServerCommonWorkTime getServerAvailableTime(String serverId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ServerCommonWorkTime.class);
		criteria.add(Expression.eq("serverId", serverId));
		List<ServerCommonWorkTime> results = simpleService.find(criteria);
		return results.size() == 1 ? results.get(0) : null;
	}

	/**
	 * @return ���������� ��
	 */
	public int getAppServerCount() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.monitoring.serveravailability.getCountServers");
					return Integer.decode(query.uniqueResult().toString());
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
