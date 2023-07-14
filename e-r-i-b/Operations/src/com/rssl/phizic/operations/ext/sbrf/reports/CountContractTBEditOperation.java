package com.rssl.phizic.operations.ext.sbrf.reports;

import com.rssl.phizic.business.ext.sbrf.reports.TypeReport;
import com.rssl.phizic.operations.ext.sbrf.reports.BusinessReportEditOperation;

/**
 * @author Mescheryakova
 * @ created 23.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class CountContractTBEditOperation  extends BusinessReportEditOperation
{
	public char getTypeReport()
	{
		return TypeReport.CONTRACT_TB;
	}

}
