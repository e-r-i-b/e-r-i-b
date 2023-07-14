package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.PrepareDocumentValidatorStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateESBAutoPayOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateFreeDetailAutoSubOperation;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import org.apache.struts.action.*;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 03.08.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class CreateFreeDetailAutoSubAction extends EditJurPaymentAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateFreeDetailAutoSubForm frm = (CreateFreeDetailAutoSubForm) form;
		CreateFreeDetailAutoSubOperation operation = createEditOperation(frm);
		
		frm.setField(CreateFreeDetailAutoSubForm.RECEIVER_ACCOUNT_FIELD, operation.getReceiverAccount());
		frm.setField(CreateFreeDetailAutoSubForm.RECEIVER_INN_FIELD, operation.getReceiverINN());
		frm.setField(CreateFreeDetailAutoSubForm.RECEIVER_BIC_FIELD, operation.getReceiverBIC());

		updateFormAdditionalData(frm, operation);

		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	protected abstract CreateFreeDetailAutoSubOperation createEditOperation(CreateFreeDetailAutoSubForm form) throws BusinessException, BusinessLogicException;

	protected abstract CreateESBAutoPayOperation createESBAutoPayOperation(DocumentSource source) throws BusinessException, BusinessLogicException;

	/**
	 * переход к оформлению автоподписки в адрес найденого ПУ
	 * @param providerId - идентификатор ПУ , найденый по реквизитам
	 * @param fromResource - код, выбранного ресурс списания
	 * @return переход на следующий шаг оформления АП в адрес ПУ
	 */
	protected abstract ActionForward makeRecipientAutoSubscription(Long providerId, String fromResource) throws BusinessException;

	/**
	 * Поиск поставщиков по заданным реквизитам и переход на следущий шаг в зависимости от количесва найденых ПУ
	 * @param frm - Form
	 * @param operation - операция
	 * @param request - HttpServletRequest
	 * @return форвард следущего шага создания автоплатежа по свободным реквизитам
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected abstract ActionForward findRecipients(CreateFreeDetailAutoSubForm frm, CreateFreeDetailAutoSubOperation operation, HttpServletRequest request) throws BusinessException, BusinessLogicException;


	/**
	 * Сохранение ошибок валидации
	 * @param request - request
	 * @param frm - форма в кторую сохраняем ошибки
	 * @param errors - ошибки валидации
	 */
	protected void saveFormErrors(HttpServletRequest request,CreateFreeDetailAutoSubForm frm, ActionMessages errors)
	{
		saveErrors(request, errors);
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateFreeDetailAutoSubForm frm = (CreateFreeDetailAutoSubForm) form;

		OperationContext.setCurrentOperUID(frm.getOperationUID());

		CreateFreeDetailAutoSubOperation operation = createEditOperation(frm);
		Map<String, Object> map = frm.getFields();
		map.put(CreateFreeDetailAutoSubForm.FROM_RESOURCE_FIELD, frm.getFromResource());//Заполняем отдельно, тк хранится в отдельм свойстве формы
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(map), CreateFreeDetailAutoSubForm.EDIT_FORM);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			operation.setReceiverAccount((String) result.get(CreateFreeDetailAutoSubForm.RECEIVER_ACCOUNT_FIELD));
			operation.setReceiverBIC((String) result.get(CreateFreeDetailAutoSubForm.RECEIVER_BIC_FIELD));
			operation.setReceiverINN((String) result.get(CreateFreeDetailAutoSubForm.RECEIVER_INN_FIELD));
			operation.setChargeOffResource((PaymentAbilityERL) result.get(CreateFreeDetailAutoSubForm.FROM_RESOURCE_FIELD));
			try
			{
				return findRecipients(frm, operation, request);
			}
			catch (InactiveExternalSystemException e)
			{
				saveInactiveESMessage(request, e);
			}
			catch (BusinessLogicException e)
			{
				ActionMessages actionErrors = new ActionMessages();
				actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				saveFormErrors(request, frm, actionErrors);
			}
		}
		else
		{
			saveFormErrors(request,frm,processor.getErrors()); // сохраняем ошибки валидации
		}

		updateFormAdditionalData(frm, operation);
		return mapping.findForward(FORWARD_SHOW_FORM);//даем их исправить.
	}

	protected ActionForward doPrepareExternalProviderPayment(final CreateFreeDetailAutoSubOperation editPaymentOperation, final CreateFreeDetailAutoSubForm frm, final HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ActionForward>()
			{
				public ActionForward run(Session session) throws Exception
				{
					editPaymentOperation.prepareExternalProviderPayment(null);
					//формируем мап данных пользователя
					Map<String, Object> data = new HashMap<String, Object>(frm.getFields());
					data.put(PaymentFieldKeys.FROM_RESOURCE_KEY, editPaymentOperation.getChargeOffResource().getCode());// сохраяняем источник списания
					data.put(PaymentFieldKeys.PROVIDER_EXTERNAL_KEY, null);
					data.put(PaymentFieldKeys.RECEIVER_NAME, null);
					data.put(PaymentFieldKeys.BILLING_CODE, null);
					final FieldValuesSource fieldValuesSource = new MapValuesSource(data);
					//создаем источник нового биллигового платежа
					DocumentSource source = createNewDocumentSource(FormConstants.SERVICE_PAYMENT_FORM, fieldValuesSource, null);
					//создаем операцию редактирования шинного автоплатежа
					CreateESBAutoPayOperation esbAutoPayOperation = createESBAutoPayOperation(source);

					//валидируем форму
					FormProcessor<ActionMessages, ?> processor = createFormProcessor(fieldValuesSource, esbAutoPayOperation.getMetadata().getForm(), PrepareDocumentValidatorStrategy.getInstance());
					if (processor.process())
					{
						//обновляем документ данными и сохраняем как longOffer
						esbAutoPayOperation.makeLongOffer(processor.getResult());
						//делаем форвард на сл шаг.
						return createNextStageDocumentForward(esbAutoPayOperation);
					}
					else
					{
						saveErrorsInForm(frm, request, processor.getErrors());
					}
					updateFormAdditionalData(frm, editPaymentOperation);
					return getForwardFormError();//отображаем форму в случае ошибки
				}
			});
		}
		catch (InactiveExternalSystemException e)
		{
			throw e;
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
