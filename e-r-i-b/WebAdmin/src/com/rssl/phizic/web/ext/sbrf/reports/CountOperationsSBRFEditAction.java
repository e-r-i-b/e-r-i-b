package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.operations.ext.sbrf.reports.ReportEditOperation;
import com.rssl.phizic.operations.ext.sbrf.reports.CountOperationsSBRFEditOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;

import java.util.List;

/**
 * @author Mescheryakova
 * @ created 10.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class CountOperationsSBRFEditAction extends ReportsEditAction
{
    public ReportEditOperation getReportOperation() throws Exception
    {
	    return createOperation(CountOperationsSBRFEditOperation.class);
    }

	protected Form getEditForm(EditFormBase frm)
	{
		return ReportPeriodEditForm.EDIT_FORM;
	}	

	/**
	 * для данного отчета нет выбора департаментов, поэтому возвращает всегда false
	 * @return false - всегда
	 */
	protected boolean checkSelectedIds(List<String> checkSelectedIds)
	{
		return false;
	}
}
