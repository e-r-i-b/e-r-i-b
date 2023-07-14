package com.rssl.phizic.web.client.dictionaries;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.dictionaries.receivers.ConfirmPaymentReceiverOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.security.SecurityMessages;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Krenev
 * @ created 15.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmPaymentReceiverAction extends OperationalActionBase
{
	protected static final String FORWARD_START = "Start";
	protected static final String FORWARD_SUCCESS = "Success";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirm", "confirm");
		map.put("button.preConfirm", "preConfirm");
		return map;
	}

	protected ConfirmPaymentReceiverOperation createOperation() throws BusinessException, BusinessLogicException
	{
		return createOperation(ConfirmPaymentReceiverOperation.class); 
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                           HttpServletResponse response)
			throws Exception
	{
		ConfirmPaymentReceiverForm frm = (ConfirmPaymentReceiverForm) form;

		ConfirmPaymentReceiverOperation operation = createOperation();
		operation.initialize(frm.getId());
		ConfirmationManager.sendRequest(operation);

		saveOperation(request, operation);
		frm.setReceiver(operation.getConfirmableObject());

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                             HttpServletResponse response) throws Exception
	{
		ConfirmPaymentReceiverForm frm = (ConfirmPaymentReceiverForm) form;

		ConfirmPaymentReceiverOperation operation = getOperation(request);

		operation.initialize(frm.getId());

		List<String> errors = validateConfirmResponse(operation, request);

		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);
			return start(mapping, form, request, response);
		}
		try
		{
			operation.confirm();
			resetOperation(request);
		}
		catch (SecurityLogicException e)
		{
			saveError(request, e);
			return start(mapping, form, request, response);
		}

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward preConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmPaymentReceiverForm frm = (ConfirmPaymentReceiverForm) form;

		ConfirmPaymentReceiverOperation operation = getOperation(request);
		operation.initialize(frm.getId());
		frm.setReceiver(operation.getConfirmableObject());
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();

		CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
		callBackHandler.setConfirmableObject(operation.getConfirmableObject());
		callBackHandler.setLogin(login);
		try
		{
			PreConfirmObject preConfirmObject = operation.preConfirm(callBackHandler);
			saveMessages(request, ConfirmHelper.getPreConfirmMsg(preConfirmObject) );
		}
		catch (SecurityLogicException e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(SecurityMessages.translateException(e), false));
			saveErrors(request, errors);
		}

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

		return mapping.findForward(FORWARD_START);
	}

	protected List<String> validateConfirmResponse(ConfirmPaymentReceiverOperation operation, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));

		if ((errors != null) && (!errors.isEmpty()))
		{
			return errors;
		}
		try
		{
			operation.validateConfirm();
		}
		catch (SecurityLogicException e)
		{
			List<String> err =  new ArrayList<String>();
			err.add(e.getMessage());
			return err;
		}

		return null;
	}
}
