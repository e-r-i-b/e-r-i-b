package com.rssl.phizic.operations.ext.sbrf.reports;

import com.rssl.phizic.business.ext.sbrf.reports.BusinessReport;
import com.rssl.phizic.operations.ext.sbrf.reports.ReportEditOperation;

/**
 * @author Mescheryakova
 * @ created 24.08.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class BusinessReportEditOperation extends ReportEditOperation
{
	public BusinessReport getReportClass()
	{
		return new BusinessReport();
	}
}
