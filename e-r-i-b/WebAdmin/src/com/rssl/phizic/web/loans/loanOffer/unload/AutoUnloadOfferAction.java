package com.rssl.phizic.web.loans.loanOffer.unload;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.loanOffer.*;
import com.rssl.phizic.operations.virtualcards.unload.UnloadVirtualCardClaimOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * User: Moshenko
 * Date: 22.06.2011
 * Time: 16:03:11
 */
public class AutoUnloadOfferAction extends EditActionBase
{
	private static final String LOAN_PRODUCT = "loanProduct";
	private static final String LOAN_CARD_PRODUCT = "loanCardProduct";
	private static final String LOAN_OFFER = "loanOffer";
	private static final String LOAN_CARD_OFFER = "loanCardOffer";
	private static final String VIRTUAL_CARD = "virtualCard";

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		SetupAutoUnloadOfferProductOperation operation = createOperation(SetupAutoUnloadOfferProductOperation.class);
		operation.initialize();
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return AutoUnloadOfferForm.createForm();
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		//получаем данные из текущих джобов
		//и заполняем ими форму
		Map<String, ScheduleData> dataMap = (Map)entity;
		
		setOneFormData(frm, dataMap, UnloadLoanProductOperation.class.getName(), LOAN_PRODUCT);
		setOneFormData(frm, dataMap, UnloadLoanOfferOperation.class.getName(), LOAN_OFFER);
		setOneFormData(frm, dataMap, UnloadCardProductOperation.class.getName(), LOAN_CARD_PRODUCT);
		setOneFormData(frm, dataMap, UnloadCardOfferOperation.class.getName() , LOAN_CARD_OFFER);
		setOneFormData(frm, dataMap, UnloadVirtualCardClaimOperation.class.getName(), VIRTUAL_CARD);
	}

	private void setOneFormData(EditFormBase frm, Map<String, ScheduleData> dataMap, String key, String id)
	{
		ScheduleData scheduleData = dataMap.get(key);
		frm.setField(id + "Hour", scheduleData.getHour());
		frm.setField(id + "Day", scheduleData.getDay());
		frm.setField(id + "DayTime", scheduleData.getTime());
		frm.setField(id + "Folder", scheduleData.getDir());
		frm.setField(id + "Enabled", scheduleData.isEnabled());
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception //2
	{
		SetupAutoUnloadOfferProductOperation operation = (SetupAutoUnloadOfferProductOperation) editOperation;
		operation.initializeNew();
		operation.setData(validationResult);
	}

	 protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		try
		{
			//Фиксируем данные, введенные пользователе
			addLogParameters(frm);
			operation.save();
		}
		catch (UnloadTaskStateException e)
		{/*задание в процессе выполнения, надо подождать, иначе не правильно поставим статус*/
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage(e.getMessage(),false));
			saveErrors(currentRequest(), messages);
		}
		return createSaveActionForward(operation, frm);
	}
}
