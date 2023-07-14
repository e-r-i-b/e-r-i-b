package com.rssl.phizic.web.client.userprofile;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.logging.confirm.OperationConfirmLogWriter;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.operations.messaging.EditUserContactDataOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import org.apache.struts.action.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 27.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditEmailAction  extends AsyncOperationalActionBase
{
	private static final String CONFIRM_FORWARD = "ConfirmForm";
	private static final String SHOW_PROFILE_FORWARD = "ShowProfile";
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirmSMS", "changeToSMS");
		map.put("button.confirm",    "confirm");
		map.put("button.save",       "save");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditEmailForm frm = (EditEmailForm)form;
		EditUserContactDataOperation contactDataOperation = createOperation(EditUserContactDataOperation.class);
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		contactDataOperation.initialize(personData.getLogin());

		frm.setField("email", contactDataOperation.getEmailAddress());
		MailFormat mailFormat = contactDataOperation.getMailFormat();
		if (mailFormat == null)
		{
			frm.setField("mailFormat", MailFormat.PLAIN_TEXT.name());
		} else
		{
			frm.setField("mailFormat", mailFormat);
		}
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward changeToSMS(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditUserContactDataOperation operation = getOperation(request);
		EditEmailForm form = (EditEmailForm) frm;
		operation.setUserStrategyType(ConfirmStrategyType.sms);

		ConfirmationManager.sendRequest(operation);
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		operation.getRequest().setPreConfirm(true);
		CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
		callBackHandler.setConfirmableObject(operation.getConfirmableObject());
		callBackHandler.setLogin(login);
		callBackHandler.setAdditionalCheck();
		try
		{
			PreConfirmObject preConfirmObject = operation.preConfirm(callBackHandler);
			ConfirmRequest confirmRequest = operation.getRequest();
			confirmRequest.addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityLogicException e)
		{
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(e.getMessage()));
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, errors);
		}
		return forwardShow(mapping, operation, form, request);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditUserContactDataOperation operation = getOperation(request);
		ConfirmRequest confirmRequest = operation.getRequest();
		ConfirmHelper.clearConfirmErrors(confirmRequest);
		try
		{
			EditEmailForm form = (EditEmailForm) frm;
			form.setConfirmStrategy(operation.getConfirmStrategy());

			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
			if (errors.isEmpty())
				return doConfirm(mapping, operation, form, request);

			ConfirmHelper.saveConfirmErrors(confirmRequest, errors);
			return mapping.findForward(CONFIRM_FORWARD);
		}
		catch (TemporalBusinessException ignore)
		{
			String exceptionMessage = getResourceMessage("paymentsBundle", "message.operation.not.available");
			ConfirmHelper.saveConfirmErrors(confirmRequest, Collections.singletonList(exceptionMessage));
			return mapping.findForward(FORWARD_SHOW_FORM);
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditUserContactDataOperation operation = createOperation(EditUserContactDataOperation.class);
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		operation.initialize(personData.getLogin());
		EditEmailForm form = (EditEmailForm) frm;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(form.getFields()), EditEmailForm.FORM);
		if (!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return mapping.findForward(FORWARD_START);
		}
		Map<String, Object> result = processor.getResult();
		operation.setEmailAddress((String)result.get("email"));
		operation.setMailFormat((MailFormat) result.get("mailFormat"));

		if (!operation.isEmailChanged())
		{
			operation.save();
			return mapping.findForward(SHOW_PROFILE_FORWARD);
		}

		saveOperation(request, operation);
		operation.resetConfirmStrategy();
		ConfirmationManager.sendRequest(operation);
		form.setField("confirmableObject", operation.getConfirmableObject());
		return mapping.findForward(FORWARD_START);
	}

	private ActionForward forwardShow(ActionMapping mapping, EditUserContactDataOperation operation, EditEmailForm form, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		form.setConfirmStrategy(operation.getConfirmStrategy());

		form.setConfirmableObject(operation.getConfirmableObject());

		form.setField("email", operation.getEmailAddress());
		MailFormat mailFormat = operation.getMailFormat();
		if (mailFormat == null)
		{
			form.setField("mailFormat", MailFormat.PLAIN_TEXT.name());
		} else
		{
			form.setField("mailFormat", mailFormat);
		}

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return mapping.findForward(CONFIRM_FORWARD);
	}

	private ActionForward doConfirm(ActionMapping mapping, EditUserContactDataOperation operation, EditEmailForm form, HttpServletRequest request) throws Exception
	{
		try
		{
			operation.confirm();
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
			return mapping.findForward(SHOW_PROFILE_FORWARD);
		}
		catch (SecurityLogicException e)
		{
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(e.getMessage()));

			return forwardShow(mapping, operation, form, request);
		}
	}

}
