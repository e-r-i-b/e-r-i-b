package com.rssl.phizic.logging.exceptions.report;

import com.rssl.phizic.logging.exceptions.ExceptionCounter;
import com.rssl.phizic.logging.exceptions.ExceptionEntry;

import java.util.List;

/**
 * @author akrenev
 * @ created 22.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * базовая сущность описания ошибки для отчета
 */

public abstract class ExceptionReportRecordBase<E extends ExceptionEntry>
{
	private String hash;
	private List<ExceptionCounter> counters;

	/**
	 * @return описание ошибки
	 */
	public abstract E getEntry();

	/**
	 * @return распределение ошибок по дням
	 */
	public List<ExceptionCounter> getCounters()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return counters;
	}
}
