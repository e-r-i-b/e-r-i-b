package com.rssl.phizic.web.calendar;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.calendar.WorkCalendar;
import com.rssl.phizic.business.calendar.WorkDay;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.calendar.EditCalendarOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gainanov
 * @ created 26.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditCalendarAction extends EditActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		return map;
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditCalendarOperation operation = createOperation(EditCalendarOperation.class);
		Long id = frm.getId();
		if (id != null && id != 0)
		{
			operation.inintialize(id);
			return operation;
		}
		operation.initializeNew();
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditCalendarForm.EDIT_CALENDAR_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		WorkCalendar calendar = (WorkCalendar) entity;
		calendar.setName((String)data.get("name"));
		calendar.setTb((String)data.get("departmentId"));
	}

	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{
		EditCalendarForm frm = (EditCalendarForm) form;
		WorkCalendar caledar = (WorkCalendar) entity;

		frm.setField("name", caledar.getName());
		frm.setField("departmentId", caledar.getTb());
	}

    protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		EditCalendarForm frm = (EditCalendarForm) form;
		EditCalendarOperation op = (EditCalendarOperation) operation;

		frm.setField("region", op.getRegion());

		StringBuffer holidays =  new StringBuffer("");
		for (WorkDay day : op.getHolidays())
		{
			String workday = String.format("%1$te.%1$tm.%1$tY", day.getDate());
			if (workday.substring(0, workday.indexOf(".")).length() == 1)
				workday = "0" + workday;
			holidays.append(workday).append(";");
		}
		frm.setHolidays(holidays.toString());

		StringBuffer workdays = new StringBuffer("");
		for (WorkDay day : op.getWorkdays())
		{
			String workday = String.format("%1$te.%1$tm.%1$tY", day.getDate());
			if (workday.substring(0, workday.indexOf(".")).length() == 1)
				workday = "0" + workday;
			workdays.append(workday).append(";");
		}
		frm.setWorkdays(workdays.toString());

		frm.setCaAdmin(op.isCAAdmin());
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase form) throws Exception
	{
		EditCalendarOperation op = (EditCalendarOperation) operation;
		EditCalendarForm frm = (EditCalendarForm) form;
		op.setHolidays(frm.getHolidays());
		op.setWorkdays(frm.getWorkdays());		
		return super.doSave(operation, mapping, frm);		
	}
}
