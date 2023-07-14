package com.rssl.phizic.operations.ext.sbrf.reports.it;

import com.rssl.phizic.operations.ext.sbrf.reports.ReportEditOperation;
import com.rssl.phizic.business.ext.sbrf.reports.it.ITReport;

/**
 * @author Mescheryakova
 * @ created 24.08.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class ITReportEditOperation extends ReportEditOperation
{
	public ITReport getReportClass()
	{
		return new ITReport();
	}
}
