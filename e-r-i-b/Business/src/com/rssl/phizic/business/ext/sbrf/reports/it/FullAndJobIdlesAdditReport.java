package com.rssl.phizic.business.ext.sbrf.reports.it;

import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * �������� ��� ������������ �������� ������� ������� � ����� �����������
 * �� ������ ������ ����������� ������ ����������� (MonitoringSystemAvailableJob)
 * @author gladishev
 * @ created 11.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class FullAndJobIdlesAdditReport extends SystemIdleAdditReport
{
	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getFullAndJobIdleTime";
	}

	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		Calendar endDate = DateHelper.getCurrentDate();
		endDate.setTime(report.getEndDate().getTime());
		endDate.add(Calendar.DATE, 1); // +1 ����
		params.put("startDate",  report.getStartDate());
		params.put("endDate",    endDate);
		return params;
	}
}
