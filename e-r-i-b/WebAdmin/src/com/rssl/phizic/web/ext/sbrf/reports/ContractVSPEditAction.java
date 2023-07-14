package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.operations.ext.sbrf.reports.ReportEditOperation;
import com.rssl.phizic.operations.ext.sbrf.reports.CountContractVSPEditOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;

/**
 * @author Mescheryakova
 * @ created 17.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class ContractVSPEditAction extends ReportsEditAction
{
  public ReportEditOperation getReportOperation() throws Exception
  {
	    return createOperation(CountContractVSPEditOperation.class);
  }

	/**
	 * Вернуть форму редактирования.
	 * @param frm struts-форма
	 * @return форма редактирования
	 */
	protected Form getEditForm(EditFormBase frm)
	{
		return LastYearReportForm.EDIT_FORM;
	}	
}
