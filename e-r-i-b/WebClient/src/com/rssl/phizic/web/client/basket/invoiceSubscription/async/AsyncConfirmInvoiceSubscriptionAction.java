package com.rssl.phizic.web.client.basket.invoiceSubscription.async;

import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.basket.invoiceSubscription.ConfirmInvoiceSubscriptionOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн подтверждения подписки на инвойсы
 * @author niculichev
 * @ created 21.10.14
 * @ $Author$
 * @ $Revision$
 */
public class AsyncConfirmInvoiceSubscriptionAction extends OperationalActionBase
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private static final String FORWARD_SUCCESS         = "Success";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirmSMS",    "changeToSMS");
		map.put("button.dispatch",      "confirm");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return null;
	}

	public ActionForward changeToSMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncConfirmInvoiceSubscriptionForm frm = (AsyncConfirmInvoiceSubscriptionForm) form;

		ConfirmInvoiceSubscriptionOperation confirmOperation = createConfirmOperation(frm.getId());
		confirmOperation.setUserStrategyType(ConfirmStrategyType.sms);

		//отправляем запрос на подтверждение
		ConfirmationManager.sendRequest(confirmOperation);
		preSMSConfirm(confirmOperation);
		confirmOperation.getRequest().setPreConfirm(true);

		saveOperation(request, confirmOperation);
		frm.setSubscription((InvoiceSubscription) confirmOperation.getConfirmableObject());
		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	private void preSMSConfirm(ConfirmInvoiceSubscriptionOperation operation) throws BusinessLogicException, BusinessException
	{
		ConfirmRequest confirmRequest = operation.getRequest();

		try
		{
			CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
			callBackHandler.setConfirmableObject(operation.getConfirmableObject());
			callBackHandler.setLogin(PersonContext.getPersonDataProvider().getPersonData().getLogin());
			saveMessage(currentRequest(), ConfirmHelper.getPreConfirmString(operation.preConfirm(callBackHandler)));

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(currentRequest(), errors);
		}
		catch (SecurityLogicException e)
		{
			confirmRequest.setErrorMessage(SecurityMessages.translateException(e));
		}
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmInvoiceSubscriptionOperation operation = getOperation(request);
		ConfirmRequest confirmRequest = operation.getRequest();
		ConfirmHelper.clearConfirmErrors(confirmRequest);

		List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
		if(CollectionUtils.isNotEmpty(errors))
		{
			ConfirmHelper.saveConfirmErrors(confirmRequest, errors);
			return mapping.findForward(FORWARD_SHOW_FORM);
		}

		try
		{
			operation.confirm();
			addLogParameters(new BeanLogParemetersReader("Подтверждаемая сущность", operation.getConfirmableObject()));
			resetOperation(request);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, StrutsUtils.getMessage("error.errorHeader", "paymentsBundle"));
		}

		return mapping.findForward(FORWARD_SUCCESS);
	}


	private ConfirmInvoiceSubscriptionOperation createConfirmOperation(Long invoiceId) throws BusinessException, BusinessLogicException
	{
		ConfirmInvoiceSubscriptionOperation operation = createOperation(ConfirmInvoiceSubscriptionOperation.class, "PaymentBasketManagment");
		operation.initialize(invoiceId);
		return operation;
	}
}
