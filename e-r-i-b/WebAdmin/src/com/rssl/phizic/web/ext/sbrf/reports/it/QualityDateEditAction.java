package com.rssl.phizic.web.ext.sbrf.reports.it;

import com.rssl.phizic.web.ext.sbrf.reports.ReportsEditAction;
import com.rssl.phizic.web.ext.sbrf.reports.ReportPeriodEditForm;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.operations.ext.sbrf.reports.ReportEditOperation;
import com.rssl.phizic.operations.ext.sbrf.reports.it.QualityDateEditOperation;
import com.rssl.common.forms.Form;

/**
 * @author Mescheryakova
 * @ created 24.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class QualityDateEditAction extends ReportsEditAction
{
    public ReportEditOperation getReportOperation() throws Exception
    {
	    return createOperation(QualityDateEditOperation.class);
    }

	protected Form getEditForm(EditFormBase frm)
	{
		return ReportPeriodEditForm.EDIT_FORM;
	}	
}