package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.fund.ClosedFundRequestException;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.fund.response.ChangeStateFundOperation;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.mobile.payments.forms.EditMobilePaymentAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 16.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Экшен изменения статуса обработки входящего запрос на сбор средств
 */
public class ChangeStateFundSenderResponseAction extends EditMobilePaymentAction
{
	private static final String CLOSED_REQUEST_FORWARD = "FundClosed";

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(new RequestValuesSource(request), ChangeStateFundSenderResponseForm.FORM);

		if (formProcessor.process())
		{
			ChangeStateFundOperation operation = createOperation(formProcessor.getResult());
			Method method = ChangeStateFundSenderResponseAction.class.getMethod(operation.getStateMethodName(), ChangeStateFundOperation.class, ActionForm.class);

			return (ActionForward) method.invoke(this, operation, form);
		}

		saveErrors(request, formProcessor.getErrors());
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Прочитать входящий запрос на сбор средств
	 * @param operation операция
	 * @param form форма
	 * @return форвард
	 */
	public ActionForward read(ChangeStateFundOperation operation, ActionForm form) throws Exception
	{
		return simpleChangeState(operation, form);
	}

	/**
	 * Отклонить входящий запрос на сбор средств
	 * @param operation операция
	 * @param form форма
	 * @return форвард
	 */
	public ActionForward reject(ChangeStateFundOperation operation, ActionForm form) throws Exception
	{
		return simpleChangeState(operation, form);
	}

	/**
	 * Удовлетворить входящий запрос на сбор средств
	 * @param operation операция
	 * @param form форма
	 * @return форвард
	 */
	public ActionForward success(ChangeStateFundOperation operation, ActionForm form) throws Exception
	{
		try
		{
			operation.execute();
			saveOperation(currentRequest(), operation);
			FieldValuesSource valuesSource = operation.getFieldValuesSource();

			if (MaskPaymentFieldUtils.isRequireMasking())
			{
				valuesSource = MaskPaymentFieldUtils.wrapMaskValuesSource(getMaskingInfo(operation, valuesSource), valuesSource);
			}

			updateForm((CreatePaymentForm) form, operation, valuesSource);
			addLogParameters(new BeanLogParemetersReader("Данные документа", operation.getDocument()));
			saveStateMachineEventMessages(currentRequest(), operation, false);

			return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
		}
		catch (ClosedFundRequestException ignore)
		{
			return getCurrentMapping().findForward(CLOSED_REQUEST_FORWARD);
		}
		catch (TemporalBusinessException ignore)
		{
			saveError(currentRequest(), "По техническим причинам операция временно недоступна. Повторите попытку позже");
		}
		catch (BusinessLogicException e)
		{
			saveError(currentRequest(), e);
		}
		return getCurrentMapping().findForward(FORWARD_START);
	}

	private ActionForward simpleChangeState(ChangeStateFundOperation operation, ActionForm form) throws Exception
	{
		try
		{
			operation.execute();
		}
		catch (BusinessLogicException e)
		{
			saveError(currentRequest(), e);
		}
		return getCurrentMapping().findForward(FORWARD_START);
	}

	private ChangeStateFundOperation createOperation(Map<String, Object> result) throws BusinessException
	{
		String externalId = (String) result.get(ChangeStateFundSenderResponseForm.ID_FIELD_NAME);
		BigDecimal sum = (BigDecimal) result.get(ChangeStateFundSenderResponseForm.SUM_FIELD_NAME);
		String message = (String) result.get(ChangeStateFundSenderResponseForm.MESSAGE_FIELD_NAME);
		Boolean closeConfirm = (Boolean) result.get(ChangeStateFundSenderResponseForm.CLOSE_CONFIRM_FIELD_NAME);
		Long fromResource = (Long) result.get(ChangeStateFundSenderResponseForm.FROM_RESOURCE_FIELD_NAME);
		String stateValue = (String) result.get(ChangeStateFundSenderResponseForm.STATE_FIELD_NAME);
		FundResponseState state = FundResponseState.valueOf(stateValue);

		ChangeStateFundOperation operation = createOperation(state.getOperationKey());
		operation.initialize(externalId, sum, message, closeConfirm, fromResource);

		return operation;
	}
}
