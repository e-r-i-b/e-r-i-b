package com.rssl.phizic.web.client.basket.invoiceSubscription.async;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.documents.exceptions.NotOwnDocumentException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.basket.invoiceSubscription.*;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.DocumentActionBase;
import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rssl.phizic.business.limits.OperationType.IMPOSSIBLE_PERFORM_OPERATION;

/**
 * @author osminin
 * @ created 28.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Асинхронное создание заявки на операцию по подписке
 */
public class AsyncCreateInvoiceSubscriptionClaimAction extends DocumentActionBase
{
	private static final String MAKE_AUTO_INVOCE_SUBSCRIPTION_SERVICE = "MakeAutoInvoiceSubscriptionService";
	private static final String FORWARD_SUCCESS         = "Success";
	private static final String FORWARD_FAIL_UPDATE     = "FailUpdate";
	private static final String FORWARD_SUCCESS_UPDATE  = "SuccessUpdate";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirmSMS",    "changeToSMS");
		map.put("button.dispatch",      "confirm");
		map.put("button.update",        "update");
		map.put("button.remove",        "remove");
		map.put("button.removeGeneratedSub", "removeGeneratedSub");
		map.put("button.recoverAutoSub", "recoverAutoSub");
		return map;
	}

	@Override
	protected boolean isAjax()
	{
		return true;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//Метод старт не используется при создании заявок по подпискам
		return null;
	}

	/**
	 * Выполнить необходимые действия для подтверждения операции по СМС
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward changeToSMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;

			//Сначала создаем и сохраняем документ
			EditDocumentOperation editOperation = createEditDocumentOperation(frm);
			editOperation.save();

			//Подготавливаем операцию подтверждения
			ConfirmFormPaymentOperation confirmOperation = createConfirmOperation(editOperation.getDocument());
			confirmOperation.setUserStrategyType(ConfirmStrategyType.sms);

			//отправляем запрос на подтверждение
			ConfirmationManager.sendRequest(confirmOperation);
			confirmOperation.getRequest().setPreConfirm(true);
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
		//Если пришли ошибки, отображаем их пользователю
		if (!errors.isEmpty())
		{
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), errors);
			confirmRequest.setPreConfirm(false);
			updateForm(operation, frm);
			return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
		}
		//иначе подтверждаем операцию
		return doConfirm(operation, frm);
	}

	/**
	 * Обновить ошибочную подписку(по сути отправить повторно запрос)
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
		UpdateInvoiceSubscriptionOperation operation = createOperation(UpdateInvoiceSubscriptionOperation.class, "PaymentBasketManagment");
		try
		{
			operation.initialize(frm.getId());
			operation.update();
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward(FORWARD_FAIL_UPDATE);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, "Операция временно недоступна.");
			return mapping.findForward(FORWARD_FAIL_UPDATE);
		}

		return mapping.findForward(FORWARD_SUCCESS_UPDATE);
	}

	/**
	 * Удалить ошибочную подписку
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncCreateInvoiceSubscriptionClaimForm frm = (AsyncCreateInvoiceSubscriptionClaimForm) form;
		RemoveInvoiceSubscriptionOperation operation = createOperation(RemoveInvoiceSubscriptionOperation.class, "PaymentBasketManagment");
		try
		{
			operation.initialize(frm.getId());
			operation.remove();
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward(FORWARD_FAIL_UPDATE);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, "Операция временно недоступна.");
			return mapping.findForward(FORWARD_FAIL_UPDATE);
		}

		frm.setRefresh(true);
		return mapping.findForward(FORWARD_SUCCESS_UPDATE);
	}

	private ActionForward doConfirm(ConfirmFormPaymentOperation operation, ConfirmPaymentByFormForm form) throws BusinessException
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
		updateForm(operation, form);
		return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
	}

	private void updateForm(ConfirmFormPaymentOperation operation, ConfirmPaymentByFormForm form) throws BusinessException
	{
		BusinessDocument document = operation.getDocument();

		form.setDocument(document);
		form.setMetadata(operation.getMetadata());
		form.setConfirmStrategy(operation.getConfirmStrategy());
		form.setAnotherStrategyAvailable(operation.isAnotherStrategy());
		// для создания автоподписки отображаем полную форму
		if(isMakeAutoInvoiceSub(document))
			form.setHtml(operation.buildFormHtml(new TransformInfo("view", "html"), getFormInfo(form)));
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
			return createOperation(ConfirmAutoInvoiceSubscriptionOperation.class, MAKE_AUTO_INVOCE_SUBSCRIPTION_SERVICE);
		}

		return createOperation(ConfirmInvoiceSubscriptionClaimOperation.class, source.getMetadata().getName());
	}

	private EditDocumentOperation createEditDocumentOperation(ConfirmPaymentByFormForm form) throws BusinessException, BusinessLogicException
	{
		if(MAKE_AUTO_INVOCE_SUBSCRIPTION_SERVICE.equals(form.getFormName()))
		{
			MakeAutoInvoiceSubscriptionOperation operation = createOperation(MakeAutoInvoiceSubscriptionOperation.class, MAKE_AUTO_INVOCE_SUBSCRIPTION_SERVICE);
			operation.initialize(form.getId());
			return operation;
		}
		else
		{
			CreateInvoiceSubscriptionClaimOperation operation = createOperation(CreateInvoiceSubscriptionClaimOperation.class, form.getFormName());
			operation.initialize(form.getId(), form.getFormName());
			return operation;
		}
	}

	private FormInfo getFormInfo(ActionForm form) throws BusinessException
	{
		String webRoot = WebContext.getCurrentRequest().getContextPath();
		String resourceRoot = currentServletContext().getInitParameter("resourcesRealPath");

		return new FormInfo(webRoot, resourceRoot, getSkinUrl(form), isAjax());
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

	public ActionForward removeGeneratedSub(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncCreateInvoiceSubscriptionClaimForm frm = (AsyncCreateInvoiceSubscriptionClaimForm) form;

		try
		{
			CloseInvoiceSubscriptionOperation operation =
					createOperation(CloseInvoiceSubscriptionOperation.class, FormConstants.CLOSE_INVOICE_SUBSCRIPTION_CLAIM);
			operation.initializeForGenerated(frm.getId());
			operation.remove();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}

		// на клиенте не обрабатываем
		return null;
	}

	public ActionForward recoverAutoSub(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncCreateInvoiceSubscriptionClaimForm frm = (AsyncCreateInvoiceSubscriptionClaimForm) form;

		try
		{
			CloseInvoiceSubscriptionOperation operation =
					createOperation("RemoveGeneratedInvoiceSubscriptionOperation", FormConstants.CLOSE_INVOICE_SUBSCRIPTION_CLAIM);
			operation.initializeForRecoverAutoSub(frm.getId());
			operation.remove();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}

		return null;
	}
}
