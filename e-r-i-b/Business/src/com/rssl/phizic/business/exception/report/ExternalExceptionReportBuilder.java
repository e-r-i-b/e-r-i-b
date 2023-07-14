package com.rssl.phizic.business.exception.report;

import com.rssl.phizic.business.exception.ExceptionEntryHelper;
import com.rssl.phizic.logging.exceptions.ExternalExceptionEntry;
import com.rssl.phizic.logging.exceptions.report.ExternalExceptionReportRecord;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 23.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * билдер отчета по внешним ошибкам системы
 */

public class ExternalExceptionReportBuilder extends ReportBuilderBase<ExternalExceptionReportRecord, ExternalExceptionEntry>
{
	private static final ExceptionStatisticService service = new ExceptionStatisticService();

	private static final String REQUEST_TYPE_COLUMN_NAME = "“ип запроса";
	private static final String SYSTEM_COLUMN_NAME = "—истема";

	/**
	 * конструктор
	 * @param date мес€ц за который будет строитьс€ отчет
	 * @throws IOException
	 */
	public ExternalExceptionReportBuilder(Calendar date) throws IOException
	{
		super(date);
	}

	protected List<ExternalExceptionReportRecord> getData(Calendar date) throws Exception
	{
		return service.getExternalExceptionRecords(date);
	}

	protected void addExceptionAdditionalHeader() throws IOException
	{
		addCell(REQUEST_TYPE_COLUMN_NAME);
		addCell(SYSTEM_COLUMN_NAME);
	}

	protected void addExceptionAdditionalInfo(ExternalExceptionEntry entry) throws IOException
	{
		addCell(entry.getOperation());
		addCell(ExceptionEntryHelper.getSystemName(entry.getSystem()));
	}
}
