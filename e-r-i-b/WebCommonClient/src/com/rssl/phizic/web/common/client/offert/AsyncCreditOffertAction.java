package com.rssl.phizic.web.common.client.offert;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.loans.offert.AcceptCreditOffertOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Moshenko
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 */
public class AsyncCreditOffertAction extends OperationalActionBase
{
	private static final String NEXT_STAGE_KEY = "next";
	protected static final String FORWARD_SUCCESS = "Success";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.refuseMoney","refuseMoney");
		map.put("button.preConfirmSMS","preGetMoney");
		map.put("button.getOfferMoney","getMoney");
		return map;
	}

	protected boolean isAjax()
	{
		return true;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			response.getWriter().write(NEXT_STAGE_KEY);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}

		return null;
	}

	private ActionForward preConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AcceptCreditOffertOperation operation = (AcceptCreditOffertOperation)getOperation(request);
		CreditOffertForm frm   = (CreditOffertForm) form;
		operation.updateOffer(frm.getOfferId());
		operation.setUserStrategyType(ConfirmStrategyType.sms);
		frm.setConfirmableObject(operation.getConfirmableObject());
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		ConfirmationManager.sendRequest(operation);
		saveOperation(request,operation);
		try
		{
			PreConfirmObject preConfirmObject = operation.preConfirm(createCallBackHandler(login, operation.getConfirmableObject()));
			operation.getRequest().setPreConfirm(true);
			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, errors);
		}
		catch (SecurityLogicException e)
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
			operation.getRequest().setPreConfirm(true);
		}
		return mapping.findForward(FORWARD_SHOW);

	}

	public ActionForward getMoney(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return confirmEnter(mapping, form, request, response);
	}

	public ActionForward preGetMoney(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return preConfirm(mapping, form, request, response);
	}

	public ActionForward refuseMoney(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
        CreditOffertForm frm   = (CreditOffertForm) form;
        AcceptCreditOffertOperation operation = createOperation("AcceptCreditOffertOperation","ExtendedLoanClaim");

        addLogParameters(new BeanLogParemetersReader("Отказ от оферты", frm));

        operation.refuse(frm.getAppNum(), frm.getClaimId());

		return null;
	}

	private ActionForward confirmEnter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreditOffertForm frm = (CreditOffertForm) form;
		ConfirmableOperationBase operation = getOperation(request);

		List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
		if (!errors.isEmpty() )
		{
			operation.getRequest().setErrorMessage(errors.get(0));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_SHOW);
		}
		try
		{
			operation.confirm();
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(operation.getSuccessfulMessage(), false), null);
			request.getSession().setAttribute("offerAccepted","true");
			return null;
		}
		catch (SecurityLogicException e) // ошибка подтверждения. не закрываем окно подтверждения. даем возможность ввести смс еще раз.
		{
			operation.getRequest().setErrorMessage(e.getMessage());
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (BusinessLogicException e) // ошибка подтверждения. выходим из окна подтверждения. выводим сообщение
		{
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false), null);
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (BusinessException e) //упал сервис. выходим из окна подтверждения. выводим сообщение
		{
			saveSessionError(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Сервис временно недоступен, попробуйте позже",false), null);
			frm.setConfirmableObject(operation.getConfirmableObject());
			return null;
		}
	}

	protected CallBackHandler createCallBackHandler(Login login, ConfirmableObject object)
	{
		CallBackHandler callBackHandler;
		callBackHandler = new CallBackHandlerSmsImpl();
		callBackHandler.setLogin(login);
		callBackHandler.setConfirmableObject(object);
		callBackHandler.setOperationType(OperationType.EDIT_USER_SETTINGS);
		callBackHandler.setAdditionalCheck();

		return callBackHandler;
	}
}
