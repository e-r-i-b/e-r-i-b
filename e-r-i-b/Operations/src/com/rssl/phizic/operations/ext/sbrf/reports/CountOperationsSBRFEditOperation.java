package com.rssl.phizic.operations.ext.sbrf.reports;

import com.rssl.phizic.business.ext.sbrf.reports.TypeReport;
import com.rssl.phizic.operations.ext.sbrf.reports.BusinessReportEditOperation;

/**
 * @author Mescheryakova
 * @ created 10.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class CountOperationsSBRFEditOperation extends BusinessReportEditOperation
{
	public char getTypeReport()
	{
		return TypeReport.OPERATIONS_SBRF;
	}
}