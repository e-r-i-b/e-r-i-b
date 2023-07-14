package com.rssl.phizic.web.common.mobile.basket;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.documents.exceptions.NotOwnDocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.limits.BusinessDocumentLimitException;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.RestrictionType;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.access.NullConfirmStrategySource;
import com.rssl.phizic.operations.basket.invoiceSubscription.ConfirmAutoInvoiceSubscriptionOperation;
import com.rssl.phizic.operations.basket.invoiceSubscription.ConfirmInvoiceSubscriptionClaimOperation;
import com.rssl.phizic.operations.basket.invoiceSubscription.MakeAutoInvoiceSubscriptionOperation;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.DocumentActionBase;
import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rssl.phizic.business.limits.OperationType.IMPOSSIBLE_PERFORM_OPERATION;

/**
 * @author Balovtsev
 * @since 27.10.14.
 */
public class MapiCreateInvoiceSubscriptionClaimAction extends DocumentActionBase
{
	private static final String MAKE_AUTO_INVOICE_SUBSCRIPTION_SERVICE = "MakeAutoInvoiceSubscriptionService";
	private static final String FORWARD_SUCCESS = "Success";
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("init",    "init");
		map.put("confirm", "confirm");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return null;
	}

	@Override
	protected boolean isAjax()
	{
		return false;
	}

	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;

			//Сначала создаем и сохраняем документ
			EditDocumentOperation editOperation = createEditDocumentOperation(frm);
			editOperation.save();

			ConfirmFormPaymentOperation confirmOperation = createConfirmOperation(editOperation.getDocument());
			confirmOperation.setStrategy(new NullConfirmStrategySource());

			ConfirmationManager.sendRequest(confirmOperation);
			confirmOperation.getRequest().setPreConfirm(false);
			saveOperation(request, confirmOperation);

			preConfirm(confirmOperation);
			updateForm(confirmOperation, frm);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}

		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	/**
	 * Подтвердить операцию
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
		ConfirmFormPaymentOperation operation = getOperation(request);
		ConfirmRequest confirmRequest = operation.getRequest();
		ConfirmHelper.clearConfirmErrors(confirmRequest);

		List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
		if (!errors.isEmpty())
		{
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), errors);
			confirmRequest.setPreConfirm(false);
			updateForm(operation, frm);
			return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
		}

		return doConfirm(operation);
	}

	private ActionForward doConfirm(ConfirmFormPaymentOperation operation) throws BusinessException
	{
		try
		{
			operation.confirm();
			resetOperation(currentRequest());
			saveStateMachineEventMessages(currentRequest(), operation, false);
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

			return getCurrentMapping().findForward(FORWARD_SUCCESS);
		}
		catch (SecurityLogicException e)
		{
			saveError(currentRequest(), e);
			saveStateMachineEventMessages(currentRequest(), operation, false);
		}
		catch (BusinessDocumentLimitException e)
		{
			saveError(currentRequest(), getLimitMessage(e));
			saveStateMachineEventMessages(currentRequest(), operation, false);
			operation.getRequest().setPreConfirm(false);
		}
		catch (BusinessLogicException e)
		{
			saveError(currentRequest(), e);
			saveStateMachineEventMessages(currentRequest(), operation, false);
			operation.getRequest().setPreConfirm(false);
		}

		return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
	}

	private void updateForm(ConfirmFormPaymentOperation operation, ConfirmPaymentByFormForm form) throws BusinessException
	{
		BusinessDocument document = operation.getDocument();

		form.setDocument(document);
		form.setMetadata(operation.getMetadata());
		form.setConfirmStrategy(operation.getConfirmStrategy());
		form.setAnotherStrategyAvailable(operation.isAnotherStrategy());
	}

	private void preConfirm(ConfirmFormPaymentOperation operation) throws BusinessLogicException, BusinessException
	{
		ConfirmRequest confirmRequest = operation.getRequest();

		try
		{
			CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
			callBackHandler.setConfirmableObject(operation.getConfirmableObject());
			callBackHandler.setLogin(PersonContext.getPersonDataProvider().getPersonData().getLogin());

			confirmRequest.addMessage(ConfirmHelper.getPreConfirmString(operation.preConfirm(callBackHandler)));

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

	private ConfirmFormPaymentOperation createConfirmOperation(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		try
		{
			ExistingSource source = new ExistingSource(document, new IsOwnDocumentValidator());
			ConfirmFormPaymentOperation operation = createOperation(source);
			operation.initialize(source);
			return operation;
		}
		catch (NotOwnDocumentException e)
		{
			throw new AccessException("У данного пользователя нет прав на просмотр платежа с id=" + document.getId(), e);
		}
	}

	private ConfirmFormPaymentOperation createOperation(ExistingSource source) throws BusinessException
	{
		if(isMakeAutoInvoiceSub(source.getDocument()))
		{
			return createOperation(ConfirmAutoInvoiceSubscriptionOperation.class, MAKE_AUTO_INVOICE_SUBSCRIPTION_SERVICE);
		}

		return createOperation(ConfirmInvoiceSubscriptionClaimOperation.class, source.getMetadata().getName());
	}

	private EditDocumentOperation createEditDocumentOperation(ConfirmPaymentByFormForm form) throws BusinessException, BusinessLogicException
	{
		MakeAutoInvoiceSubscriptionOperation operation = createOperation(MakeAutoInvoiceSubscriptionOperation.class, MAKE_AUTO_INVOICE_SUBSCRIPTION_SERVICE);
		operation.initialize(form.getId());
		return operation;
	}

	private boolean isMakeAutoInvoiceSub(BusinessDocument document)
	{
		return document.getFormName().equals(FormConstants.SERVICE_PAYMENT_FORM) && document.isLongOffer();
	}

	private String getLimitMessage(BusinessDocumentLimitException e)
	{
		String errorMessage = e.getMessage();
		if(StringHelper.isNotEmpty(errorMessage))
			return errorMessage;

		Limit limit = e.getLimit();
		RestrictionType restrictionType = limit.getRestrictionType();

		if(limit.getOperationType() == IMPOSSIBLE_PERFORM_OPERATION && RestrictionType.AMOUNT_IN_DAY != restrictionType)
			return getResourceMessage("paymentsBundle", "payment.limit.impossible.perform.confirm." + restrictionType.name());

		return getResourceMessage("paymentsBundle", "payment.limit.exceeded.general.confirm.default");
	}
}
