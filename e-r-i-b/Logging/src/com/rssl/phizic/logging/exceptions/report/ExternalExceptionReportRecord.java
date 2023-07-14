package com.rssl.phizic.logging.exceptions.report;

import com.rssl.phizic.logging.exceptions.ExternalExceptionEntry;

/**
 * @author akrenev
 * @ created 22.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * описание внешней ошибки для отчета
 */

public class ExternalExceptionReportRecord extends ExceptionReportRecordBase<ExternalExceptionEntry>
{
	private ExternalExceptionEntry entry;

	public ExternalExceptionEntry getEntry()
	{
		return entry;
	}
}
