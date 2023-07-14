package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.operations.ext.sbrf.reports.ReportEditOperation;
import com.rssl.phizic.operations.ext.sbrf.reports.CountOperationsTBEditOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;

/**
 * @author Mescheryakova
 * @ created 10.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class CountOperationsTBEditAction extends ReportsEditAction
{
    public ReportEditOperation getReportOperation() throws Exception
    {
	    return createOperation(CountOperationsTBEditOperation.class);
    }

	protected Form getEditForm(EditFormBase frm)
	{
		return ReportPeriodEditForm.EDIT_FORM;
	}
}
