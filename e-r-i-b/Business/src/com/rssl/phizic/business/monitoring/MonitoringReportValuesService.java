package com.rssl.phizic.business.monitoring;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author mihaylov
 * @ created 28.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class MonitoringReportValuesService
{

	/**
	 * ¬ыполнение запроса дл€ мониторинга
	 * @param report - параметр мониторинга
	 * @param TB - номер тербанка
	 * @return
	 * @throws BusinessException
	 */
	public Long executeMonitoringReport(final String report, final String TB) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
				{
					public Long run(Session session) throws Exception
					{
						Query query = session.createSQLQuery("select "+report+" from "+ report + " where TB = "+ TB);
						Object result = query.uniqueResult();
						if(result == null)
							return 0L;
						return Long.valueOf(result.toString());
					}
				});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
