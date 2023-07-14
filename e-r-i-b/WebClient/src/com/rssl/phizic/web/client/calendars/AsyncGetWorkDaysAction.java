package com.rssl.phizic.web.client.calendars;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.calendar.WorkDay;
import com.rssl.phizic.operations.calendar.AsyncGetWorkDaysOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшен дл€ получение списка рабочих дней
 * @author basharin
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */

public class AsyncGetWorkDaysAction extends OperationalActionBase
{
	@Override
	protected boolean isAjax()
	{
		return true;
	}


	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncGetWorkDaysForm frm = (AsyncGetWorkDaysForm) form;
		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(mapValuesSource, AsyncGetWorkDaysForm.filetrForm);
		if (formProcessor.process())
		{
			Map<String, Object> result = formProcessor.getResult();
			AsyncGetWorkDaysOperation operation = createOperation(AsyncGetWorkDaysOperation.class);
			operation.initialize((Date) result.get(AsyncGetWorkDaysForm.FROM_DATE_FIELD), (Date) result.get(AsyncGetWorkDaysForm.TO_DATE_FIELD), (String) result.get(AsyncGetWorkDaysForm.TB_FIELD));
			List<WorkDay> listDays = operation.getWorkDays();
			JsonObject jsonObject = new JsonObject();
			JsonArray weekendDays = new JsonArray();
			JsonArray workDays = new JsonArray();
			for (WorkDay day : listDays)
			{
				if (day.getIsWorkDay())
					workDays.add(new JsonPrimitive(DateHelper.getDateFormat(day.getDate().getTime())));
				else
					weekendDays.add(new JsonPrimitive(DateHelper.getDateFormat(day.getDate().getTime())));
			}
			jsonObject.add("weekendDays", weekendDays);
			jsonObject.add("workDays", workDays);
			frm.setJsonDays(jsonObject.toString());
		}

		return mapping.findForward(FORWARD_START);
	}
}
