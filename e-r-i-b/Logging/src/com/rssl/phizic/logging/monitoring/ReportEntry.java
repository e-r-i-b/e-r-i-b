package com.rssl.phizic.logging.monitoring;

/**
 * Запись отчета.
 *
 * @author bogdanov
 * @ created 30.03.15
 * @ $Author$
 * @ $Revision$
 */

public interface ReportEntry
{
	/**
	 * @return название записи.
	 */
	String getName();

	/**
	 * @param name название записи.
	 */
	void setName(String name);
}
