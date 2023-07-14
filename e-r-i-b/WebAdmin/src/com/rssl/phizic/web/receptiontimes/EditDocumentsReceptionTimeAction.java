package com.rssl.phizic.web.receptiontimes;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.GateCharacteristicsHelper;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.receptiontimes.ReceptionTime;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.receptiontimes.EditDocumentsReceptionTimeOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Pakhomova
 * @created: 18.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditDocumentsReceptionTimeAction extends EditActionBase
{
	protected EditEntityOperation createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditDocumentsReceptionTimeOperation operation = createOperation("ViewDocumentsReceptionTimeOperation");
		operation.initialize(frm.getId());
		return operation;
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditDocumentsReceptionTimeOperation operation = createOperation(EditDocumentsReceptionTimeOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ((EditDocumentsReceptionTimeForm) frm).createForm();
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditDocumentsReceptionTimeForm form = (EditDocumentsReceptionTimeForm)frm;
		EditDocumentsReceptionTimeOperation editOperation = (EditDocumentsReceptionTimeOperation) operation;
		List<ReceptionTime> receptionTimes = editOperation.getEntity();
		Map<String, String> payments = new HashMap<String, String>();

		for (ReceptionTime receptionTime : receptionTimes)
		{
			String paymentName = receptionTime.getPaymentType();
			String paymentTypeDescription = receptionTime.getPaymentTypeDescription();

			payments.put(paymentName, paymentTypeDescription);

			String paymentStartTimeFieldName  = paymentName + EditDocumentsReceptionTimeForm.TIME_START_FIELD;
			String paymentEndTimeFieldName    = paymentName + EditDocumentsReceptionTimeForm.TIME_END_FIELD;
			String paymentCalendarIdFieldName = paymentName + EditDocumentsReceptionTimeForm.CALENDAR_ID_FIELD;

			form.setField(paymentName + EditDocumentsReceptionTimeForm.USE_PARENT_SETTINGS_FIELD, receptionTime.isUseParentSettings());

			//получаем время приема документов вышестоящего независимо от галки
			// на случай, если пользователь поставит галку, отображаем время вышестоящего
			ReceptionTime parentReceptionTime = editOperation.getParentRecepionTime(paymentName);

			if(receptionTime.isUseParentSettings())
				receptionTime = parentReceptionTime;

			form.setTimeField(paymentStartTimeFieldName, receptionTime.getReceptionTimeStart());
			form.setTimeField(paymentEndTimeFieldName,   receptionTime.getReceptionTimeEnd());
			form.setField(paymentCalendarIdFieldName,    getCalendarId(receptionTime));

			if(parentReceptionTime != null)
			{
				// установка значений вышестоящего подразделения
				form.setTimeField(EditDocumentsReceptionTimeForm.PARENT + paymentStartTimeFieldName, parentReceptionTime.getReceptionTimeStart());
				form.setTimeField(EditDocumentsReceptionTimeForm.PARENT + paymentEndTimeFieldName,   parentReceptionTime.getReceptionTimeEnd());
				form.setField(EditDocumentsReceptionTimeForm.PARENT + paymentCalendarIdFieldName,    getCalendarId(parentReceptionTime));
			}
		}

		form.setHaveParentDepartment(!DepartmentService.isTB(editOperation.getDepartment()));
		form.setPayments(payments);

		//если подразделение привязано к реальному офису в какой либо внешней системе
		if (editOperation.getDepartment().getSynchKey() != null)
		{
			boolean useOuterCalendar = GateCharacteristicsHelper.isCalendarAvaliable(editOperation.getDepartment());
			form.setUseRetailCalendar(useOuterCalendar);

			//если не используем внешние календари (из шлюза)
			if (!useOuterCalendar)
				form.setCalendars(editOperation.getCalendars());
		}
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditDocumentsReceptionTimeOperation operation = (EditDocumentsReceptionTimeOperation) editOperation;
		List<ReceptionTime> receptionTimes = operation.getEntity();

		for (ReceptionTime receptionTime : receptionTimes)
		{
			Boolean useParentSettings = (Boolean) validationResult.get(receptionTime.getPaymentType() + EditDocumentsReceptionTimeForm.USE_PARENT_SETTINGS_FIELD);
			Time timeStart  = (Time) validationResult.get(receptionTime.getPaymentType() + EditDocumentsReceptionTimeForm.TIME_START_FIELD);
			Time timeEnd    = (Time) validationResult.get(receptionTime.getPaymentType() + EditDocumentsReceptionTimeForm.TIME_END_FIELD);
			Long calendarId = (Long) validationResult.get(receptionTime.getPaymentType() + EditDocumentsReceptionTimeForm.CALENDAR_ID_FIELD);

			receptionTime.setUseParentSettings(useParentSettings);
			receptionTime.setReceptionTimeStart(timeStart);
			receptionTime.setReceptionTimeEnd(timeEnd);
			receptionTime.setCalendar(operation.getCalendarById(calendarId));
			receptionTime.setDepartmentId(operation.getDepartment().getId());
		}
	}

	//без операции сделать ничего не можем
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{}

	//без операции сделать ничего не можем
	protected void updateEntity(Object entity, Map<String, Object> data)  throws BusinessException
	{}

	private Long getCalendarId(ReceptionTime receptionTime)
	{
		if (receptionTime == null || receptionTime.getCalendar() == null)
			return null;

		return receptionTime.getCalendar().getId();
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		EditDocumentsReceptionTimeOperation op = (EditDocumentsReceptionTimeOperation) operation;
		return new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath()+ "?id="+op.getDepartment().getId(), true);
	}
}
