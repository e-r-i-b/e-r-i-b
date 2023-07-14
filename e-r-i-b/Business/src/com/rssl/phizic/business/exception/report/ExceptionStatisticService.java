package com.rssl.phizic.business.exception.report;

import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Session;
import com.rssl.phizic.logging.exceptions.report.ExceptionReportRecordBase;
import com.rssl.phizic.logging.exceptions.report.ExternalExceptionReportRecord;
import com.rssl.phizic.logging.exceptions.report.InnerExceptionReportRecord;

import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 22.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * сервис получени€ полной инфы по исключени€м
 */

public class ExceptionStatisticService
{
	/**
	 * @param month мес€ц за который нужно вытаскивать инфу
	 * @return список из полной информации по внутреннему исключению
	 * @throws Exception
	 */
	public List<InnerExceptionReportRecord> getInnerExceptionRecords(Calendar month) throws Exception
	{
		return getRecords(month, InnerExceptionReportRecord.class);
	}

	/**
	 * @param month мес€ц за который нужно вытаскивать инфу
	 * @return список из полной информации по внешнему исключению
	 * @throws Exception
	 */
	public List<ExternalExceptionReportRecord> getExternalExceptionRecords(Calendar month) throws Exception
	{
		return getRecords(month, ExternalExceptionReportRecord.class);
	}

	private <R extends ExceptionReportRecordBase> List<R> getRecords(final Calendar month, final Class<R> recordClass) throws Exception
	{
		return HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateAction<List<R>>()
		{
			public List<R> run(Session session) throws Exception
			{
				Calendar startPeriod = DateHelper.getFirstDayOfMonth(month);
				Calendar endPeriod = DateHelper.add(startPeriod, new DateSpan(0,1,0));
				session.enableFilter("dateExceptionReportRecordFilter").setParameter("startPeriod", startPeriod).setParameter("endPeriod", endPeriod);
				//noinspection unchecked
				return (List<R>) session.createCriteria(recordClass).list();
			}
		});
	}
}
