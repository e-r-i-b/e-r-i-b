package com.rssl.phizic.web.ext.sbrf.reports.it;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ext.sbrf.reports.ReportEditOperation;
import com.rssl.phizic.operations.ext.sbrf.reports.it.ProactiveEditOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.ext.sbrf.reports.ReportPeriodEditForm;
import com.rssl.phizic.web.ext.sbrf.reports.ReportsEditAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class ProactiveEditAction  extends ReportsEditAction
{
	 public ReportEditOperation getReportOperation() throws Exception
    {
	    return createOperation(ProactiveEditOperation.class);
    }

	protected Form getEditForm(EditFormBase frm)
	{
		return ReportPeriodEditForm.EDIT_FORM;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ReportPeriodEditForm frm = (ReportPeriodEditForm) form;
		List<String> parseSelectedIds = parseSelectedIds(frm);
		ReportEditOperation operation = getReportOperation();

		// Узнаем минимально и максимально возможные даты для получения отчетов
		Calendar[] minMax = operation.getPeriodUserLog();
		frm.setField("minDate", String.format("%1$td.%1$tm.%1$tY", minMax[0]));
		frm.setField("maxDate", String.format("%1$td.%1$tm.%1$tY", minMax[1]));

		//Фиксируем данные, введенные пользователе
		addLogParameters(frm);

		FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm, operation);
		if (processor.process() )
		{
			try
			{
				Map<String, Object> result = processor.getResult();
				operation.initialize(DateHelper.toCalendar((Date) result.get("startDate")), DateHelper.toCalendar((Date) result.get("endDate")),  parseSelectedIds);
				operation.save();
			}
			catch(BusinessLogicException e)
			{
				return errorForward(request, frm, operation, e.getMessage());
			}
			return mapping.findForward(FORWARD_SUCCESS);
		}
		else
		{
			saveErrors(request, processor.getErrors());
			return createSaveActionForward(operation, frm);
		}
	}

}
