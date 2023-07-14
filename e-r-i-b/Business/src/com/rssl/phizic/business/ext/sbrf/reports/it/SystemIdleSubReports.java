package com.rssl.phizic.business.ext.sbrf.reports.it;

import com.rssl.phizic.business.ext.sbrf.reports.SubReports;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAdditionalInfo;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.config.build.MonitoringConfig;
import com.rssl.phizic.config.ConfigFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author gladishev
 * @ created 12.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class SystemIdleSubReports extends SubReports
{
	private static final int WEEK_IN_MILLIS = 7*24*3600*1000;

	public List<ReportAdditionalInfo> getSubReportsList(ReportAbstract report)
	{
		List<ReportAdditionalInfo> subReports = new ArrayList<ReportAdditionalInfo>();
		subReports.add(new FullAndJobIdlesAdditReport());

		Calendar startDate = report.getStartDate();
		Calendar endDate = DateHelper.getCurrentDate();
		endDate.setTime(report.getEndDate().getTime());
		endDate.add(Calendar.DATE, 1); // учитываем в отчете результаты последнего дня периода

		long reportPeriodLength = DateHelper.diff(endDate, startDate);

		//разбиваем процедуру нахождения периодов простоя системы на части
		MonitoringConfig monitoringConfig = ConfigFactory.getConfig(MonitoringConfig.class);
		int count = reportPeriodLength == WEEK_IN_MILLIS ? monitoringConfig.getSysIdleReportPeriodsCountByWeek()
															:monitoringConfig.getSysIdleReportPeriodsCountByMonth();
		long suPeriodLength = reportPeriodLength/count;
		long currentStartDateInMillis = startDate.getTimeInMillis();

		for (int i=0; i<count; i++)
		{
			UserOperationsIdlesAdditReport rep = new UserOperationsIdlesAdditReport();
			Calendar currentStartDate = Calendar.getInstance();
			currentStartDate.setTimeInMillis(currentStartDateInMillis);
			rep.setStartDate(currentStartDate);

			currentStartDateInMillis += suPeriodLength;
			Calendar currentEndDate = Calendar.getInstance();
			currentEndDate.setTimeInMillis(currentStartDateInMillis);
			rep.setEndDate(currentEndDate);

			subReports.add(rep);
		}

		//отчет объединающий все подотчеты в один
		subReports.add(new SystemIdleReport());

		return subReports;
	}
}
