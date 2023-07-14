package com.rssl.phizic.operations.ext.sbrf.reports.it;

import com.rssl.phizic.business.ext.sbrf.reports.TypeReport;

/**
 * @author lukina
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class ProactiveEditOperation extends ITReportEditOperation
{
	public char getTypeReport()
	{
		return TypeReport.PROACTIVE_MONITORING;
	}
}
