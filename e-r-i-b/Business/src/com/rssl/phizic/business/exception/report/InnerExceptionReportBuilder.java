package com.rssl.phizic.business.exception.report;

import com.rssl.phizic.logging.exceptions.ExceptionEntry;
import com.rssl.phizic.logging.exceptions.report.InnerExceptionReportRecord;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 23.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * билдер отчета по внутренним ошибкам системы
 */

public class InnerExceptionReportBuilder extends ReportBuilderBase<InnerExceptionReportRecord, ExceptionEntry>
{
	private static final ExceptionStatisticService service = new ExceptionStatisticService();

	private static final String OPERATION_TYPE_COLUMN_NAME = "“ип операции";

	/**
	 * конструктор
	 * @param date мес€ц за который будет строитьс€ отчет
	 * @throws IOException
	 */
	public InnerExceptionReportBuilder(Calendar date) throws IOException
	{
		super(date);
	}

	protected List<InnerExceptionReportRecord> getData(Calendar date) throws Exception
	{
		return service.getInnerExceptionRecords(date);
	}

	protected void addExceptionAdditionalHeader() throws IOException
	{
		addCell(OPERATION_TYPE_COLUMN_NAME);
	}

	protected void addExceptionAdditionalInfo(ExceptionEntry entry) throws IOException
	{
		addCell(entry.getOperation());
	}
}
