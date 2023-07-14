package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.operations.ext.sbrf.reports.ReportEditOperation;
import com.rssl.phizic.operations.ext.sbrf.reports.ActiveUsersTBEditOperation;

/**
 * @author Mescheryakova
 * @ created 10.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ActiveUsersTBEditAction extends ReportsEditAction
{
    public ReportEditOperation getReportOperation() throws Exception
    {
	    return createOperation(ActiveUsersTBEditOperation.class);
    }
}
