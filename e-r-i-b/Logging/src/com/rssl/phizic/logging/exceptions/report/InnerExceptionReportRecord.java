package com.rssl.phizic.logging.exceptions.report;

import com.rssl.phizic.logging.exceptions.ExceptionEntry;
import com.rssl.phizic.logging.exceptions.InternalExceptionEntry;

/**
 * @author akrenev
 * @ created 22.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * описание внутренней ошибки для отчета
 */

public class InnerExceptionReportRecord extends ExceptionReportRecordBase<ExceptionEntry>
{
	private InternalExceptionEntry entry;

	public ExceptionEntry getEntry()
	{
		return entry;
	}
}
