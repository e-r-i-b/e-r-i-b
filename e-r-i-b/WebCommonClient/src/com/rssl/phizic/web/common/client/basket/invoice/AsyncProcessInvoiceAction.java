package com.rssl.phizic.web.common.client.basket.invoice;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.basket.InvoiceMessage;
import com.rssl.phizic.business.basket.invoice.InvoiceUpdateInfo;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.ClientInvoiceCounterHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.basket.invoice.CreateInvoicePaymentOperation;
import com.rssl.phizic.operations.basket.invoice.UpdateInvoiceOperation;
import com.rssl.phizic.operations.basket.invoiceSubscription.ConfirmInvoiceSubscriptionOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.security.AccessControlException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 23.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Экшен аснихронного выполенения действий над инвойсом
 */
public class AsyncProcessInvoiceAction extends ViewInvoiceAction
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	protected static final String FORWARD_SUCCESS = "Success";
	private static final String FORWARD_FAILED  = "Failed";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.delay", "delay");
		map.put("button.delete", "delete");
		map.put("button.payInvoice", "payInvoice");
		map.put("button.confirm", "confirm");
		return map;
	}

	@Override
	protected boolean isAjax()
	{
		return true;
	}

	/**
	 * Оплатить задолженность
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return переход
	 * @throws Exception
	 */
	public ActionForward payInvoice(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			ViewInvoiceForm frm = (ViewInvoiceForm) form;
			CreateInvoicePaymentOperation operation = createPaymentOperation(frm.getId());

			if(operation.isConfirmSubscription())
			{
				if(BasketHelper.getBasketInteractMode() == BasketPaymentsListenerConfig.WorkingMode.esb)
				{
					FormProcessor<ActionMessages, ?> processor =
							createFormProcessor(createValueSource(frm, request, operation), operation.getMetadata().getForm());

					if(!processor.process())
					{
						saveErrors(request, processor.getErrors());
						return mapping.findForward(FORWARD_FAILED);
					}

					operation.updateDocument(processor.getResult());
				}

				operation.execute();

				saveOperationMessages(request, operation);
				InvoiceMessage.saveMessage(operation.getInvoice(), InvoiceMessage.Type.payment, frm.isAutoCreatedNotConfirm());

				return mapping.findForward(FORWARD_SUCCESS);
			}

			return changeToSMS(mapping, form, request, response);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward(FORWARD_FAILED);
		}
		catch (AccessControlException ignore)
		{
			saveError(request, StrutsUtils.getMessage("error.accessDeny", "paymentsBundle"));
			return mapping.findForward(FORWARD_FAILED);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, StrutsUtils.getMessage("error.errorHeader", "paymentsBundle"));
			return mapping.findForward(FORWARD_FAILED);
		}
	}

	protected FieldValuesSource createValueSource(ViewInvoiceForm form, HttpServletRequest request, CreateInvoicePaymentOperation operation) throws BusinessLogicException
	{
		return new RequestValuesSource(request);
	}

	protected ConfirmStrategyType getConfirmStrategyType()
	{
		return ConfirmStrategyType.sms;
	}

	private ActionForward changeToSMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewInvoiceForm frm = (ViewInvoiceForm) form;

		ConfirmInvoiceSubscriptionOperation confirmOperation = createConfirmOperation(frm.getId());
		ConfirmStrategyType confirmStrategyType = getConfirmStrategyType();
		confirmOperation.setUserStrategyType(confirmStrategyType);

		//отправляем запрос на подтверждение
		ConfirmationManager.sendRequest(confirmOperation);
		if (confirmStrategyType.equals(ConfirmStrategyType.sms))
			preSMSConfirm(confirmOperation);
		confirmOperation.getRequest().setPreConfirm(true);

		saveOperation(request, confirmOperation);
		frm.setNeedConfirm(true);

		return mapping.findForward(FORWARD_FAILED);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewInvoiceForm frm = (ViewInvoiceForm)form;
		ConfirmInvoiceSubscriptionOperation operation = getOperation(request);
		ConfirmRequest confirmRequest = operation.getRequest();
		ConfirmHelper.clearConfirmErrors(confirmRequest);

		List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
		//Если пришли ошибки, отображаем их пользователю
		if(CollectionUtils.isNotEmpty(errors))
		{
			ConfirmHelper.saveConfirmErrors(confirmRequest, errors);
			return mapping.findForward(FORWARD_SHOW_FORM);
		}
		//иначе подтверждаем операцию
		try
		{
			operation.confirm();
			addLogParameters(new BeanLogParemetersReader("Подтверждаемая сущность", operation.getConfirmableObject()));
			resetOperation(request);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward(FORWARD_FAILED);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, StrutsUtils.getMessage("error.errorHeader", "paymentsBundle"));
			return mapping.findForward(FORWARD_FAILED);
		}

		frm.markAutoCreatedNotConfirm();
		return payInvoice(mapping, form, request, response);
	}

	/**
	 * Отложить инвойс.
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return переход
	 * @throws Exception
	 */
	public ActionForward delay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			ViewInvoiceForm frm = (ViewInvoiceForm) form;
			String delayDateString = frm.getChooseDelayDateInvoice();
			DateParser parser = new DateParser();
			Calendar delayDate = Calendar.getInstance();
			delayDate.setTime(parser.parse(delayDateString));
			UpdateInvoiceOperation operation = createUpdateOperation(frm.getId());
			operation.delayInvoice(delayDate);
			ClientInvoiceCounterHelper.resetCounterValue();
			InvoiceUpdateInfo invoice = operation.getInvoiceUpdateInfo();
			boolean isAutoCreatedNotConfirm = invoice.getConfirmStrategyType() == null;
			InvoiceMessage.saveMessage(invoice, InvoiceMessage.Type.delay, isAutoCreatedNotConfirm);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward(FORWARD_FAILED);
		}
		catch (AccessControlException ignore)
		{
			saveError(request, StrutsUtils.getMessage("error.accessDeny", "paymentsBundle"));
			return mapping.findForward(FORWARD_FAILED);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, StrutsUtils.getMessage("error.errorHeader", "paymentsBundle"));
			return mapping.findForward(FORWARD_FAILED);
		}

		return mapping.findForward(FORWARD_SUCCESS);
	}

	/**
	 * Удаление инвойса из корзины платежей.
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return переход
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			UpdateInvoiceOperation operation = createUpdateOperation(((ViewInvoiceForm) form).getId());
			InvoiceUpdateInfo invoice =  operation.getInvoiceUpdateInfo();
			boolean isAutoCreatedNotConfirm = invoice.getConfirmStrategyType() == null;
			operation.deleteInvoice();
			ClientInvoiceCounterHelper.resetCounterValue();
			InvoiceMessage.saveMessage(operation.getInvoiceUpdateInfo(), InvoiceMessage.Type.delete, isAutoCreatedNotConfirm);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward(FORWARD_FAILED);
		}
		catch (AccessControlException ignore)
		{
			saveError(request, StrutsUtils.getMessage("error.accessDeny", "paymentsBundle"));
			return mapping.findForward(FORWARD_FAILED);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, StrutsUtils.getMessage("error.errorHeader", "paymentsBundle"));
			return mapping.findForward(FORWARD_FAILED);
		}
		return mapping.findForward(FORWARD_SUCCESS);
	}

	private CreateInvoicePaymentOperation createPaymentOperation(Long invoiceId) throws BusinessException, BusinessLogicException
	{
		CreateInvoicePaymentOperation operation = createOperation(CreateInvoicePaymentOperation.class, FormConstants.INVOICE_PAYMENT_FORM);
		operation.initialize(invoiceId);
		ClientInvoiceCounterHelper.resetCounterValue();
		return operation;
	}

	private UpdateInvoiceOperation createUpdateOperation(Long id) throws BusinessException, BusinessLogicException
	{
		UpdateInvoiceOperation operation = createOperation("UpdateInvoiceOperation", "PaymentBasketManagment");
		operation.initialize(id, AuthModule.getAuthModule().getPrincipal().getLogin().getId());
		return operation;
	}

	private void saveOperationMessages(HttpServletRequest request, CreateInvoicePaymentOperation operation)
	{
		for(String message: operation.getMessageCollector().getMessages())
		{
			saveMessage(request, message);
		}
	}

	private ConfirmInvoiceSubscriptionOperation createConfirmOperation(Long invoiceId) throws BusinessException, BusinessLogicException
	{
		ConfirmInvoiceSubscriptionOperation operation = createOperation(ConfirmInvoiceSubscriptionOperation.class, "PaymentBasketManagment");
		operation.initializeByInvoice(invoiceId);
		return operation;
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
}
