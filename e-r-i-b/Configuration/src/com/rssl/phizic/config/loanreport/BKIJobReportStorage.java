package com.rssl.phizic.config.loanreport;

import static com.rssl.phizic.config.loanreport.CreditBureauConstants.*;

/**
 * @author Erkin
 * @ created 18.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class BKIJobReportStorage
{
	/**
	 * Загрузить отчёт
	 * @return предыдущий отчёт
	 */
	public BKIJobReport loadReport()
	{
		PropertyReader reader = new PropertyReader();
		BKIJobReport report = new BKIJobReport();
		report.lastStartTime    = reader.readOptionalCalendar(BKI_JOB_LAST_START_TIME);
		report.lastPeriodBegin  = reader.readOptionalCalendar(BKI_JOB_LAST_START_DAY);
		report.lastPeriodEnd    = reader.readOptionalCalendar(BKI_JOB_LAST_END_DAY);
		return report;
	}

	/**
	 * Сохранить отчёт
	 * @param report - отчёт (never null)
	 */
	public void saveReport(BKIJobReport report)
	{
		PropertyWriter writer = new PropertyWriter();
		writer.writeOptionalCalendar(BKI_JOB_LAST_START_TIME, report.lastStartTime);
		writer.writeOptionalCalendar(BKI_JOB_LAST_START_DAY, report.lastPeriodBegin);
		writer.writeOptionalCalendar(BKI_JOB_LAST_END_DAY, report.lastPeriodEnd);
		writer.flush();
	}
}
