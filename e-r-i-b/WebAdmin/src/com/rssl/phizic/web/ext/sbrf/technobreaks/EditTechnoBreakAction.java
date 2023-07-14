package com.rssl.phizic.web.ext.sbrf.technobreaks;

import com.rssl.common.forms.Form;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizgate.ext.sbrf.technobreaks.PeriodicType;
import com.rssl.phizic.business.ext.sbrf.technobreaks.TechnoBreakHelper;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.technobreaks.EditTechnoBreakOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author niculichev
 * @ created 10.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditTechnoBreakAction extends EditActionBase
{

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditTechnoBreakOperation operation = createOperation("EditTechnoBreakOperation");
		if(frm.getId() == null || frm.getId() == 0L)
			operation.initializeNew();
		else
			operation.initialize(frm.getId());

		return operation;
	}


	protected Form getEditForm(EditFormBase frm)
	{
		return EditTechnoBreakForm.EDIT_FORM;
	}


	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		TechnoBreak technoBreak = (TechnoBreak) entity;

		technoBreak.setPeriodic(PeriodicType.valueOf((String) data.get(Constants.PERIODIC)));

		Calendar fromDate = createCalendar((Date) data.get(Constants.FROM_DATE), (Date) data.get(Constants.FROM_TIME));
		Calendar toDate = createCalendar((Date) data.get(Constants.TO_DATE), (Date) data.get(Constants.TO_TIME));

		technoBreak.setFromDate(fromDate);
		technoBreak.setToDate(toDate);

		boolean isDefaultMessage = (Boolean) data.get(Constants.IS_DEFAULT_MESSAGE);
		technoBreak.setMessage(isDefaultMessage ? getDefaultMessage(toDate) : (String) data.get(Constants.MESSAGE));
		technoBreak.setDefaultMessage(isDefaultMessage);
		technoBreak.setAllowOfflinePayments((Boolean) data.get(Constants.ALLOW_OFFLINE_PAYMENTS));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		TechnoBreak technoBreak = (TechnoBreak) entity;

		frm.setField(Constants.PERIODIC,technoBreak.getPeriodic());

		frm.setField(Constants.FROM_DATE, technoBreak.getFromDate().getTime());
		frm.setField(Constants.FROM_TIME, technoBreak.getFromDate().getTime());

		String adapter = technoBreak.getAdapterUUID();
		frm.setField(Constants.ADAPTER_UUID, adapter);
		frm.setField(Constants.ADAPTER_NAME, TechnoBreakHelper.getExternalSystemName(adapter));

		Calendar toDate = technoBreak.getToDate();
		frm.setField(Constants.TO_DATE, toDate == null ? null : toDate.getTime());
		frm.setField(Constants.TO_TIME, toDate == null ? null : toDate.getTime());
		frm.setField(Constants.IS_DEFAULT_MESSAGE, technoBreak.isDefaultMessage());

		if(!technoBreak.isDefaultMessage())
			frm.setField(Constants.MESSAGE, technoBreak.getMessage());

		frm.setField(Constants.ALLOW_OFFLINE_PAYMENTS, technoBreak.isAllowOfflinePayments());
	}


	private Calendar createCalendar(Date date, Date time) throws BusinessException
	{
		Calendar dateCalendar = DateHelper.toCalendar(date);
		Calendar timeCalendar = DateHelper.toCalendar(time);
		dateCalendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
		dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
		dateCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
		return dateCalendar;
	}

	private String getDefaultMessage(Calendar toDate)
	{
		String arg0 = DateHelper.toStringTime(toDate.getTime());
		return  StrutsUtils.getMessage("label.message.default", "technobreaksBundle", arg0);  //getResourceMessage("technobreaksBundle", );
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						getResourceMessage("technobreaksBundle", "save.success.message"), false), null);
		return new ActionForward(getCurrentMapping().findForward(FORWARD_START).getPath()+"?id="+frm.getId());
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditTechnoBreakOperation operation = (EditTechnoBreakOperation) editOperation;
		operation.setExternalSystem((String) validationResult.get(Constants.ADAPTER_UUID));
	}
}
