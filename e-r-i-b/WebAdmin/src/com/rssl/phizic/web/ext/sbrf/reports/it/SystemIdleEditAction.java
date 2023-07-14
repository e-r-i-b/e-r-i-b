package com.rssl.phizic.web.ext.sbrf.reports.it;

import com.rssl.phizic.operations.ext.sbrf.reports.ReportEditOperation;
import com.rssl.phizic.operations.ext.sbrf.reports.it.SystemIdleEditOperation;
import com.rssl.phizic.web.ext.sbrf.reports.ReportsEditAction;
import com.rssl.phizic.web.ext.sbrf.reports.ReportPeriodEditForm;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gladishev
 * @ created 27.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class SystemIdleEditAction extends ReportsEditAction
{
	public ReportEditOperation getReportOperation() throws Exception
	{
		return createOperation(SystemIdleEditOperation.class);
	}

	/**
	 * для данного отчета нет выбора департаментов, поэтому возвращает всегда false
	 * @return false - всегда
	 */
	protected boolean checkSelectedIds(List<String> selectedIds)
	{
		return false;
	}

	protected List<ReportPeriodType> getReportPeriodTypes()
	{
		List<ReportPeriodType> result = new ArrayList<ReportPeriodType>();
		result.add(ReportPeriodType.week);
		result.add(ReportPeriodType.month);
		return result;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ReportPeriodEditForm.EDIT_FORM;
	}
}
