package com.rssl.phizic.web.atm.payments.forms;

import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.web.actions.payments.forms.ConfirmDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 01.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmPaymentATMAction extends ConfirmDocumentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("edit",    "edit");
		map.put("confirm", "confirm");
		return map;
	}

	protected String buildFormHtml(ConfirmFormPaymentOperation operation, ActionForm form) throws BusinessException
	{
		return operation.buildATMXml(getTransformInfo(), getFormInfo(form));
	}

	protected TransformInfo getTransformInfo()
	{
		return new TransformInfo("view", "atm");
	}

	protected FormInfo getFormInfo(ActionForm form) throws BusinessException
	{
		//достаем из сессии Set названий изменившихс€ полей и сразу же очищаем
		Set<String> changedFields = HttpSessionUtils.removeSessionAttribute(currentRequest(), SESSION_CHANGED_FIELDS_KEY);
		return new FormInfo(changedFields);
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmPaymentByFormForm    form      = (ConfirmPaymentByFormForm) frm;
		ConfirmFormPaymentOperation operation = getOperation(request, false);

		/*
		 * ≈сли нет активной операции
		 */
		if (operation == null)
		{
			operation = getConfirmOperation(request, form);
			ConfirmationManager.sendRequest(operation);
			saveOperation(request, operation);
		}

		return super.edit( mapping,  frm,  request,  response);
	}

	protected void saveConfirmErrors(List<String> errors, HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		saveErrors(request, errors);
	}

	protected void clearConfirmErrors(HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		clearErrors(request); 
	}

	protected void saveConfirmMessages(List<String> messages, HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		ActionMessages errors  = new ActionMessages();
		for(String message : messages)
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( message , false));
		saveMessages(request, errors);
	}

	protected void addConfirmMessage(String message, HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message,false));
		addMessages(request, messages);
	}
}
