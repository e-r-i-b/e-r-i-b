package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.TemplateValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.source.NewTemplateSource;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.documents.templates.validators.TemplateValidator;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.documents.templates.EditJurPaymentTemplateOperation;
import com.rssl.phizic.operations.documents.templates.EditServicePaymentTemplateOperation;
import com.rssl.phizic.operations.documents.templates.EditTemplateDocumentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author khudyakov
 * @ created 10.05.2012
 * @ $Author$
 * @ $Revision$
 *
 * ѕервый шаг создани€ шаблона по произвольным реквизитам
 */
public class EditJurPaymentTemplateAction extends EditTemplateActionBase
{
	private static final String FORWARD_BILLING_TEMPLATE = "BillingTemplate";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.next",              "next");
		map.put("button.payByRequisites",   "payByRequisites");
		map.put("button.search",            "search");
		return map;
	}

	protected EditTemplateDocumentOperation createViewOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		return createEditOperation(form);
	}

	protected EditTemplateDocumentOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditJurPaymentTemplateForm frm = (EditJurPaymentTemplateForm) form;

		EditJurPaymentTemplateOperation operation = (EditJurPaymentTemplateOperation) createOperation(getEditOperationClass(FormType.JURIDICAL_TRANSFER.getName()), FormType.JURIDICAL_TRANSFER.getName());
		//Ќичего не пришло - инициализируем операцию только источником списани€.
		operation.initialize(frm.getFromResource(), (String) frm.getField(EditJurPaymentForm.RECEIVER_ACCOUNT_FIELD), (String) frm.getField(EditJurPaymentForm.RECEIVER_INN_FIELD), (String) frm.getField(EditJurPaymentForm.RECEIVER_BIC_FIELD));
		return operation;
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		EditJurPaymentTemplateForm frm = (EditJurPaymentTemplateForm) form;
		EditJurPaymentTemplateOperation editOperation = (EditJurPaymentTemplateOperation) operation;

		frm.setField(EditJurPaymentForm.RECEIVER_ACCOUNT_FIELD, editOperation.getReceiverAccount());
		frm.setField(EditJurPaymentForm.RECEIVER_INN_FIELD,     editOperation.getReceiverINN());
		frm.setField(EditJurPaymentForm.RECEIVER_BIC_FIELD,     editOperation.getReceiverBIC());
		frm.setField(PaymentFieldKeys.OPERATION_CODE,           editOperation.getOperationCode());

		frm.setOperationUID(OperationContext.getCurrentOperUID());
		frm.setChargeOffResources(editOperation.getChargeOffResources());

		PaymentAbilityERL fromResource = editOperation.getChargeOffResourceLink();
		if (fromResource != null)
		{
			frm.setFromResource(fromResource.getCode());
		}

		frm.setTemplate(editOperation.getTemplate());
		super.saveErrors(editOperation.getTemplate());
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditJurPaymentTemplateForm frm = (EditJurPaymentTemplateForm) form;

		OperationContext.setCurrentOperUID(frm.getOperationUID());

		EditJurPaymentTemplateOperation operation = (EditJurPaymentTemplateOperation) createEditOperation(frm);

		FormProcessor<ActionMessages, ?> processor =
				createFormProcessor(getFormValueSource(frm), EditJurPaymentTemplateForm.EDIT_FORM);

		if (!processor.process())
		{
			saveErrors(request, processor.getErrors());
			updateFormAdditionalData(frm, operation);
			return mapping.findForward(FORWARD_SHOW_FORM);
		}

		Map<String, Object> result = processor.getResult();
		operation.setReceiverAccount((String) result.get(Constants.RECEIVER_ACCOUNT_ATTRIBUTE_NAME));
		operation.setReceiverBIC((String) result.get(Constants.RECEIVER_BANK_BIK_ATTRIBUTE_NAME));
		operation.setReceiverINN((String) result.get(Constants.RECEIVER_INN_ATTRIBUTE_NAME));
		operation.setChargeOffResourceLink((PaymentAbilityERL) result.get(Constants.FROM_RESOURCE_ATTRIBUTE_NAME));
		operation.setExternalReceiverId((String) result.get(Constants.EXTERNAL_PROVIDER_KEY_ATTRIBUTE_NAME));       //TODO переименовать в интернал

		try
		{
			//если пришел внешний идентификатор поставщика - делаем оплату в его адрес
			String externalProviderId = (String) result.get(Constants.EXTERNAL_PROVIDER_KEY_ATTRIBUTE_NAME);
			if (externalProviderId != null)
			{
				return doPrepareExternalProviderPayment(frm, operation);
			}

			String receiverId = (String) result.get(Constants.PROVIDER_EXTERNAL_ID_ATTRIBUTE_NAME);
			if (!StringHelper.isEmpty(receiverId))
			{
				Long providerId = Long.valueOf(receiverId);
				return doPrepareInternalProviderPayment(providerId, operation, frm);
			}

			//идентифкатор не пришел - идем поставщиков в нашей Ѕƒ
			operation.findRecipient();
			if (!CollectionUtils.isEmpty(operation.getServiceProviders()))
			{
				return doPrepareInternalProviderPayment(operation, frm);
			}

			//в Ѕƒ нужных поставщиков нет - карточные переводы всегда через биллинги с карт
			if (operation.isCardsTransfer())
			{
				return doPrepareExternalProviderPayment(frm, operation);
			}


			//перевод со счета - ищем в биллинге по умолчанию
			List<Recipient> defaultBillingRecipients = operation.findDefaultBillingRecipients();
			if (defaultBillingRecipients.size() == 1)
			{
				//если найден 1 получатель - переходим на сразу на форму оплаты
				operation.setExternalReceiverId((String) defaultBillingRecipients.get(0).getSynchKey());
				return doPrepareExternalProviderPayment(frm, operation);
			}

			if (defaultBillingRecipients.size() > 1)
			{
				//на этой же форме отображаем их дл€ выбора
				frm.setExternalProviders(defaultBillingRecipients);
				frm.setField(Constants.EXTERNAL_PROVIDER_KEY_ATTRIBUTE_NAME, operation.getReceiverCodePoint());
				updateFormAdditionalData(frm, operation);
				return mapping.findForward(FORWARD_SHOW_FORM);
			}
			//поставщики нигде не найдены переходим на форму общей оплаты юрику
			return doPrepareJurPayment(frm, operation);
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
		}

		updateFormAdditionalData(frm, operation);
		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	protected ActionForward doPrepareJurPayment(EditJurPaymentTemplateForm form, EditJurPaymentTemplateOperation operation) throws BusinessException, BusinessLogicException
	{
		return forwardJurPayment(form, operation);
	}

	protected ActionForward doPrepareExternalProviderPayment(final EditJurPaymentTemplateForm frm, final EditJurPaymentTemplateOperation jurOperation) throws BusinessException, BusinessLogicException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ActionForward>()
			{
				public ActionForward run(Session session) throws Exception
				{
					//формируем мап данных пользовател€
					final FieldValuesSource fieldValuesSource = getOperationValueSource(frm, jurOperation);

					//создаем источник нового биллигового платежа
					TemplateDocumentSource source = getTemplateSource(frm, jurOperation);
					//создаем операцию редактировани€ биллингового платежа
					EditServicePaymentTemplateOperation providerOperation =
							createOperation(EditServicePaymentTemplateOperation.class, FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER.getName());

					providerOperation.initialize(source);

					//валидируем форму
					FormProcessor<ActionMessages, ?> processor =
							createFormProcessor(fieldValuesSource, providerOperation.getMetadata().getForm(), TemplateValidationStrategy.getInstance());

					if (processor.process())
					{
						providerOperation.update(processor.getResult());
						providerOperation.saveAsDraft();

						UrlBuilder urlBuilder = new UrlBuilder(DefaultDocumentAction.createStateUrl(providerOperation.getExecutor().getCurrentState()));
						urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(providerOperation.getTemplate().getId()));

						updateForwardByFinanceCalendar(urlBuilder, frm);

						return new ActionForward(urlBuilder.toString(), true);
					}
					else
					{
						saveErrorsInForm(frm, processor.getErrors());
					}

					updateFormAdditionalData(frm, jurOperation);
					return getForwardFormError();
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

	protected TemplateDocumentSource getTemplateSource(EditFormBase form, EditJurPaymentTemplateOperation operation) throws BusinessLogicException, BusinessException
	{
		EditTemplateForm frm = (EditTemplateForm) form;

		if (frm.isTemplateDataStored())
		{
			return createSimpleExistingSource(frm);
		}

		Long templateId = frm.getId();
		if (templateId != null && templateId != 0)
		{
			return createExistingTemplateSource(templateId);
		}

		Long paymentId = frm.getPayment();
		if (paymentId != null)
		{
			return createExistingPaymentTemplateSource(paymentId);
		}

		return createNewTemplateSource(frm, operation);
	}

	protected TemplateDocumentSource createNewTemplateSource(EditTemplateForm frm, EditJurPaymentTemplateOperation operation) throws BusinessLogicException, BusinessException
	{
		return new NewTemplateSource(FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER.getName(), new CompositeFieldValuesSource(new RequestValuesSource(currentRequest()), getOperationValueSource(frm, operation)), getCreationType());
	}

	private FieldValuesSource getOperationValueSource(EditFormBase form, EditJurPaymentTemplateOperation operation) throws BusinessException, BusinessLogicException
	{
		Map<String, Object> data = new HashMap<String, Object>(form.getFields());
		data.put(PaymentFieldKeys.FROM_RESOURCE_KEY,        operation.getChargeOffResourceLink().getCode());
		data.put(PaymentFieldKeys.PROVIDER_EXTERNAL_KEY,    operation.getExternalReceiverId());
		data.put(PaymentFieldKeys.RECEIVER_NAME,            operation.getExternalReceiverId() == null ? null : operation.getExternalProviderName());
		data.put(PaymentFieldKeys.BILLING_CODE,             operation.getExternalReceiverId() == null ? null : operation.getDefaultBilling().getCode());
		return new MapValuesSource(data);
	}

	private FieldValuesSource getFormValueSource(EditJurPaymentTemplateForm form)
	{
		Map<String, Object> map = form.getFields();
		map.put(Constants.FROM_RESOURCE_ATTRIBUTE_NAME, form.getFromResource());
		return new MapValuesSource(map);
	}

	protected ActionForward createNextStageDocumentForward(EditDocumentOperation operation)
	{
		UrlBuilder urlBuilder = new UrlBuilder(getCurrentMapping().findForward(FORWARD_EDIT_TEMPLATE).getPath());
		if (operation.getDocument() != null)
		{
			urlBuilder.addParameter("id",  StringHelper.getEmptyIfNull(((EditServicePaymentOperation) operation).getTemplate().getId()));
		}
		return new ActionForward(urlBuilder.toString(), true);
	}

	protected void saveErrorsInForm(EditJurPaymentTemplateForm form, ActionMessages errors)
	{
		saveErrors(currentRequest(), errors); // сохран€ем ошибки валидации
	}

	protected ActionForward getForwardFormError()
	{
		return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
	}

	protected ActionForward doPrepareInternalProviderPayment(Long providerId, EditJurPaymentTemplateOperation operation, EditJurPaymentTemplateForm form) throws BusinessException, BusinessLogicException
	{
		return forwardBillingPayment(providerId, operation, form);
	}

	protected ActionForward doPrepareInternalProviderPayment(EditJurPaymentTemplateOperation operation, EditJurPaymentTemplateForm form) throws BusinessException, BusinessLogicException
	{
		return doPrepareInternalProviderPayment(operation.getServiceProviders().get(0).getId(), operation, form);
	}

	private ActionForward forwardJurPayment(EditJurPaymentTemplateForm form, EditJurPaymentTemplateOperation operation)
	{
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(getCurrentMapping().findForward(FORWARD_EDIT_TEMPLATE).getPath());
		urlBuilder.addParameter("form", FormConstants.JUR_PAYMENT_FORM);
		urlBuilder.addParameter("fromResource", operation.getChargeOffResourceLink().getCode());
		urlBuilder.addParameter("receiverAccount", operation.getReceiverAccount());
		urlBuilder.addParameter("receiverINN", operation.getReceiverINN());
		urlBuilder.addParameter("receiverBIC", operation.getReceiverBIC());
		// если копи€ платежа то устанавливаем id и copy

		Long templateId = form.getTemplateId();
		if (templateId != null && !operation.isBillingPayment())
		{
			urlBuilder.addParameter("template", StringHelper.getEmptyIfNull(templateId));
		}

		updateForwardByFinanceCalendar(urlBuilder, form);

		return new ActionForward(urlBuilder.getUrl(), true);
	}

	protected ActionForward forwardBillingPayment(Long providerId, EditJurPaymentTemplateOperation operation, EditJurPaymentTemplateForm form) throws BusinessException
	{
		UrlBuilder urlBuilder = new UrlBuilder(getCurrentMapping().findForward(FORWARD_BILLING_TEMPLATE).getPath());
		urlBuilder.addParameter("fromResource", operation.getChargeOffResourceLink().getCode());
		urlBuilder.addParameter("recipient", String.valueOf(providerId));

		updateForwardByFinanceCalendar(urlBuilder, form);

		return new ActionForward(urlBuilder.getUrl(), true);
	}

	public ActionForward payByRequisites(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditJurPaymentTemplateForm frm = (EditJurPaymentTemplateForm) form;
		EditJurPaymentTemplateOperation operation = (EditJurPaymentTemplateOperation) createEditOperation(frm);

		Map<String, Object> map = frm.getFields();
		map.put(Constants.FROM_RESOURCE_ATTRIBUTE_NAME, frm.getFromResource());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(map), EditJurPaymentTemplateForm.EDIT_FORM);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			operation.setReceiverAccount((String) result.get(EditJurPaymentForm.RECEIVER_ACCOUNT_FIELD));
			operation.setReceiverBIC((String) result.get(EditJurPaymentForm.RECEIVER_BIC_FIELD));
			operation.setReceiverINN((String) result.get(EditJurPaymentForm.RECEIVER_INN_FIELD));
			operation.setChargeOffResourceLink(((PaymentAbilityERL) result.get(EditJurPaymentForm.FROM_RESOURCE_FIELD)));

			ResourceType type = operation.getChargeOffResourceLink().getResourceType();
			switch (type)
			{
				case ACCOUNT:
					frm.setNextURL(doPrepareJurPayment(frm, operation).getPath());
					return new ActionForward(frm.getNextURL(),true);
				case CARD:
					//карточные переводы всегда через биллинги с карт
					frm.setNextURL(doPrepareExternalProviderPayment(frm, operation).getPath());
					return new ActionForward(frm.getNextURL(),true);
				default:
					throw new BusinessException("Ќекорректный тип ресурса списани€: "+ type);
			}
		}
		saveErrors(request,processor.getErrors());
		updateFormAdditionalData(frm, operation);
		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	@Override
	protected TemplateValidator[] getTemplateValidators()
	{
		return new TemplateValidator[0];
	}

	@Override
	protected DocumentValidator[] getDocumentValidators()
	{
		return new DocumentValidator[0];
	}

	@Override
	protected Form getCheckForm(EditEntityOperation operation) throws BusinessLogicException, BusinessException
	{
		return null;
	}

	@Override
	protected Form getAdditionalForm(EditEntityOperation operation) throws BusinessLogicException, BusinessException
	{
		return FormBuilder.EMPTY_FORM;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{

	}

	protected ActionForward createStartActionForward(ActionForm form, ActionMapping mapping) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
	}
}
