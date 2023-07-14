package com.rssl.phizic.business.ext.sbrf.reports.it;

import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;

import java.util.HashMap;
import java.util.Map;

/**
 * Отчет о недоступности системы
 * @author gladishev
 * @ created 01.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class SystemIdleReport extends SystemIdleAdditReport
{
	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getSystemIdleTime";
	}

	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportId",  report.getId());
		return params;
	}
}
