package com.rssl.phizic.business.monitoring.usercounterreport;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author mihaylov
 * @ created 17.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class UserCounterReportService
{
	private static SimpleService service = new SimpleService();

	public UserCounterReport addOrUpdate(UserCounterReport report) throws BusinessException
	{
		return service.addOrUpdate(report);
	}

	public UserCounterReport findReport(String applicationName, String module, Long TB) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(UserCounterReport.class);
		criteria.add(Expression.eq("applicationName",applicationName));
		criteria.add(Expression.eq("module",module));
		if(TB != null)
			criteria.add(Expression.eq("TB",TB));
		else
			criteria.add(Expression.isNull("TB"));
		return service.findSingle(criteria);
	}
}
