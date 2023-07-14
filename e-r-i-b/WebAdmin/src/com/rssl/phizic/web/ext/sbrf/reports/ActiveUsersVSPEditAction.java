package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.operations.ext.sbrf.reports.ReportEditOperation;
import com.rssl.phizic.operations.ext.sbrf.reports.ActiveUsersVSPEditOperation;

/**
 * @author Mescheryakova
 * @ created 10.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ActiveUsersVSPEditAction extends ReportsEditAction
{
    public ReportEditOperation getReportOperation() throws Exception
    {
	    return createOperation(ActiveUsersVSPEditOperation.class);
    }
}
