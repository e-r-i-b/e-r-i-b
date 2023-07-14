package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.PrepareDocumentValidatorStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.InitalServicePaymentDocumentSource;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.operations.payment.EditJurPaymentOperation;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author krenev
 * @ created 06.03.2011
 * @ $Author$
 * @ $Revision$
 * экшен общей формы для перевода юрику(с 4 полями: счет получателя, баннк получателя, инн получателя, источник списания)
 * После того, как определены реквизиты получателя и известен источник списания, осуществляется переход на
 * соотвествющую форму:
 * 1) биллинговый платех - если получатель есть в нашей БД или биллинге по умолчанию для ТБ счета списания
 * 2) обычный платеж по реквизитам юрику (или налоговый)
 */
public class EditJurPaymentAction extends EditExternalPaymentAction
{
	protected static final String FORWARD_BILLING_PAYMENT = "BillingPayment";
	protected static final String FORWARD_CREATE_RECIPIENT_AUTOSUB = "CreateRecipientAutoSub";

	protected static final String FORWARD_EDIT_PAYMENT = "EditPayment";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.payByRequisites", "payByRequisites");
		map.put("button.makeRecipientAutoSub", "makeRecipientAutoSubscription");
		map.remove("button.changeConditions");
		map.remove("afterAccountOpening");
		return map;
	}

	/**
	 * Первоначальный показ страницы
	 * Попасть на форму можно в следующих случаях:
	 * 1) Попали по сслыке без параметров.Форма пустая.
	 * 2) Попали через карточку продукта. Нам известен счет списания. Его подставляем по умолчанию.
	 * 3) Попали после нажатия кнопки “назад” из формы редактирования существующего документа.
	 *    В данном случае известен идентификатор платежа. Данные формы инициализируются СОХРАНЕННЫМИ в документе значениями.
	 * 4) Попали после нажатия кнопки “назад” из формы редактирования нового документа(не копии). В данном случае сохраненных данных в документе нет.
	 *    Форма пуста.
	 * 5) Попали после нажатия кнопки “назад” из формы редактирования нового документа на основе копии(повтор существующего платежа).
	 *    В данном случае известен идентификатор источника копии. Форма инициализируется данными документа источника копии.
	 * 6) Попали после нажатия кнопки “назад” из формы редактирования нового документа на основе шаблона.
	 *    В данном случае известен идентификатор шаблона.Форма инициализируется данными шаблона.
	 */
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			MessageCollector messageCollector = new MessageCollector();
			EditJurPaymentForm frm = (EditJurPaymentForm) form;
			EditJurPaymentOperation operation = createEditOperation(request, frm, messageCollector);

			frm.setField(EditJurPaymentForm.RECEIVER_ACCOUNT_FIELD, operation.getReceiverAccount());
			frm.setField(EditJurPaymentForm.RECEIVER_INN_FIELD, operation.getReceiverINN());
			frm.setField(EditJurPaymentForm.RECEIVER_BIC_FIELD, operation.getReceiverBIC());

			updateFormAdditionalData(frm, operation);
		}
		catch (BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
		}

		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditJurPaymentForm frm = (EditJurPaymentForm) form;
		MessageCollector messageCollector = new MessageCollector();
		OperationContext.setCurrentOperUID(frm.getOperationUID());

		EditJurPaymentOperation operation = createEditOperation(request, frm, messageCollector);
		Map<String, Object> map = frm.getFields();
		map.put(EditJurPaymentForm.FROM_RESOURCE_FIELD, frm.getFromResource());//Заполняем отдельно, тк хранится в отдельм свойстве формы
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(map), EditJurPaymentForm.EDIT_FORM);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			operation.setReceiverAccount((String) result.get(EditJurPaymentForm.RECEIVER_ACCOUNT_FIELD));
			operation.setReceiverBIC((String) result.get(EditJurPaymentForm.RECEIVER_BIC_FIELD));
			operation.setReceiverINN((String) result.get(EditJurPaymentForm.RECEIVER_INN_FIELD));
			operation.setChargeOffResource((PaymentAbilityERL) result.get(EditJurPaymentForm.FROM_RESOURCE_FIELD));
			//устанавливаем тип ресурса списания с формы в документ, иначе словим nullPointer на проверке isBilling. BUG032174
			if (frm.getTemplate() != null && frm.getTemplate().compareTo(0L) > 0)
			{
				AbstractPaymentDocument doc = (AbstractPaymentDocument) operation.getDocument();
				com.rssl.phizic.business.documents.ResourceType resType = operation.getChargeOffResource().getResourceType();
				doc.setChargeOffResourceType(resType.getResourceLinkClass().getName());
			}
			try
			{
				//если пришел внешний идентификатор поставщика - делаем оплату в его адрес
				String externalProviderId = (String) result.get(EditJurPaymentForm.EXTERNAL_PROVIDER_KEY_FIELD);
				if (externalProviderId != null)
				{
					return doPrepareExternalProviderPayment(operation, externalProviderId, frm, request);
				}

				String receiverId = (String) result.get(EditJurPaymentForm.PROVIDER_KEY_FIELD);
				if (!StringHelper.isEmpty(receiverId))
				{
					Long providerId = Long.valueOf(receiverId);
					return doPrepareInternalProviderPayment(providerId, operation, frm);
				}

				//идентифкатор не пришел - идем поставщиков в нашей БД
				operation.findRecipient();
				if (!CollectionUtils.isEmpty(operation.getServiceProviders()))
					return doPrepareInternalProviderPayment(operation, frm);

				//в БД нужных поставщиков нет - карточные переводы всегда через биллинги с карт
				if (operation.isCardsTransfer())
				{
					return doPrepareExternalProviderPayment(operation, null, frm, request);
				}

				//перевод со счета - ищем в биллинге по умолчанию
				List<Recipient> defaultBillingRecipients = operation.findDefaultBillingRecipients();

				if (defaultBillingRecipients.size() == 1)
				{
					//если найден 1 получатель - переходим на сразу на форму оплаты
					return doPrepareExternalProviderPayment(operation, (String) defaultBillingRecipients.get(0).getSynchKey(), frm, request);
				}

				if (defaultBillingRecipients.size() > 1)
				{
					//на этой же форме отображаем их для выбора
					frm.setExternalProviders(defaultBillingRecipients);
					frm.setField(EditJurPaymentForm.EXTERNAL_PROVIDER_KEY_FIELD, operation.getReceiverCodePoint());
					updateFormAdditionalData(frm, operation);
					return mapping.findForward(FORWARD_SHOW_FORM);//отображаем форму
				}
				//поставщики нигде не найдены переходим на форму общей оплаты юрику
				return doPrepareJurPayment(operation,frm);
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
		}
		else
		{
			saveErrors(request, processor.getErrors()); // сохраняем ошибки валидации
		}

		updateFormAdditionalData(frm, operation);
		return mapping.findForward(FORWARD_SHOW_FORM);//даем их исправить.
	}

	protected ActionForward doPrepareJurPayment(EditJurPaymentOperation operation,EditJurPaymentForm form) throws BusinessException, BusinessLogicException
	{
		Long paymentId = operation.prepareJurPayment();
		if (paymentId != null)
			return forwardEditPayment(paymentId);
		else return forwardJurPayment(operation, form);
	}

	protected ActionForward doPrepareExternalProviderPayment(final EditJurPaymentOperation editJurPaymentOperation, final String externalProviderId, final EditJurPaymentForm frm,final HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		final boolean copy = frm.getCopying() != null && frm.getCopying();
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ActionForward>()
			{
				public ActionForward run(Session session) throws Exception
				{
					//при оплате внешнего поставщика данных достаточно. поэтому будем сразу сохранять платеж и переходить на дефолтный экшен.
					Long paymentId = editJurPaymentOperation.prepareExternalProviderPayment(externalProviderId);
					//формируем мап данных пользователя
					Map<String, Object> data = new HashMap<String, Object>(frm.getFields());
					data.put(PaymentFieldKeys.FROM_RESOURCE_KEY, editJurPaymentOperation.getChargeOffResource().getCode());// сохраяняем источник списания
					data.put(PaymentFieldKeys.PROVIDER_EXTERNAL_KEY, externalProviderId);// сохраяняем внешний идентифактор получателя
					data.put(PaymentFieldKeys.RECEIVER_NAME, externalProviderId == null ? null : editJurPaymentOperation.getExtenalProviderName(externalProviderId));// сохраяняем наименование получателя
					data.put(PaymentFieldKeys.BILLING_CODE, externalProviderId == null ? null : editJurPaymentOperation.getDefaultBilling().getCode());// сохраяняем код биллинга по умолчанию, при наличии получателя
					final FieldValuesSource fieldValuesSource = new MapValuesSource(data);
					DocumentSource source;
					if (paymentId != null)
					{
						//если редактируем платеж - получаем источник существующего документа
						source = new InitalServicePaymentDocumentSource(createExistingDocumentSource(paymentId), fieldValuesSource);
					}
					else if (frm.getTemplate() != null && frm.getTemplate().compareTo(0L) > 0 && editJurPaymentOperation.isBillingPayment())
					{
						//создаем источник нового биллигового платежа на основе шаблона
						source = new InitalServicePaymentDocumentSource(createCopyTemplateSource(frm.getTemplate(), frm.isMarkReminder()), fieldValuesSource);
					}
					else if (frm.getId() != null && frm.getId() > 0 && copy && editJurPaymentOperation.isBillingPayment())
					{
						//создаем источник нового биллигового платежа на основе существующего документа
						source = new InitalServicePaymentDocumentSource(createCopyDocumentSource(frm.getId()), fieldValuesSource);
					}
					else
					{
						//создаем источник нового биллигового платежа
						source = createNewDocumentSource(FormConstants.SERVICE_PAYMENT_FORM, fieldValuesSource, null);
					}
					//создаем операцию редактирования биллингового платежа
					EditServicePaymentOperation editServicePaymentOperation = createOperation("EditServicePaymentOperation", FormConstants.SERVICE_PAYMENT_FORM);
					editServicePaymentOperation.initialize(source, (Long) null);

					//валидируем форму
					FormProcessor<ActionMessages, ?> processor = createFormProcessor(fieldValuesSource, editServicePaymentOperation.getMetadata().getForm(), PrepareDocumentValidatorStrategy.getInstance());
					if (processor.process())
					{
						//все удачно -> формируем документ и идем дальше
						//1) обновляем документ данными
						editServicePaymentOperation.updateDocument(processor.getResult());
						//2) сохраняем документ
						editServicePaymentOperation.save();
						//3)делаем форвард на сл шаг.

						return createNextStageDocumentForward(editServicePaymentOperation);
					}
					else
					{
						saveErrorsInForm(frm, request, processor.getErrors());
					}
					updateFormAdditionalData(frm, editJurPaymentOperation);
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

	protected void saveErrorsInForm(EditJurPaymentForm frm,  HttpServletRequest request, ActionMessages errors)
	{
		saveErrors(currentRequest(), errors); // сохраняем ошибки валидации
	}

	protected ActionForward getForwardFormError()
	{
		return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
	}

	protected ActionForward doPrepareInternalProviderPayment(Long providerId, EditJurPaymentOperation operation, EditJurPaymentForm form) throws BusinessException, BusinessLogicException
	{
		Long paymentId = operation.prepareInternalProviderPayment(providerId);
		if (paymentId != null)
			return forwardEditPayment(paymentId);
		else return forwardBillingPayment(providerId, operation, form);
	}

	protected ActionForward doPrepareInternalProviderPayment(EditJurPaymentOperation operation, EditJurPaymentForm form) throws BusinessException, BusinessLogicException
	{
		return doPrepareInternalProviderPayment(operation.getServiceProviders().get(0).getId(), operation, form);
	}

	protected void updateFormAdditionalData(EditJurPaymentForm frm, EditJurPaymentOperation operation) throws BusinessException, BusinessLogicException
	{
		frm.setOperationUID(OperationContext.getCurrentOperUID());
		frm.setChargeOffResources(operation.getChargeOffResources());
		frm.setField(PaymentFieldKeys.OPERATION_CODE, operation.getOperationCode());

		PaymentAbilityERL fromResource = operation.getChargeOffResource();
		if (fromResource != null)
		{
			frm.setFromResource(fromResource.getCode());
		}
		frm.setDocument(operation.getDocument());

		super.saveErrors(operation.getDocument());
	}

	protected EditJurPaymentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		EditJurPaymentForm frm = (EditJurPaymentForm) form;
		Long paymentId = form.getId();
		Long templateId = form.getTemplate();
		boolean copy = form.getCopying() != null && form.getCopying();
		EditJurPaymentOperation operation = createOperation("EditJurPaymentOperation", "JurPayment");
		if (paymentId != null && paymentId > 0)
		{
			//если пришел идентфикатор платежа, значит здаем операцию на основе шаблона.
			operation.initialize(frm.getFromResource(), paymentId, !copy, false);
		}
		else if (templateId != null && templateId > 0)
		{
			//если пришел идентфикатор платежа, значит форму нужно заполнить данными этого шаблона.
			operation.initialize(frm.getFromResource(), templateId, frm.isMarkReminder());
		}
		else
		{
			//Ничего не пришло - инициализируем операцию только источником списания.
			operation.initialize(frm.getFromResource());
		}
		return operation;
	}

	private ActionForward forwardEditPayment(Long paymentId)
	{
		ActionForward forward = getCurrentMapping().findForward(FORWARD_EDIT_PAYMENT);
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(forward.getPath());
		urlBuilder.addParameter("id", String.valueOf(paymentId));
		return new ActionForward(urlBuilder.getUrl(), true);
	}

	private ActionForward forwardJurPayment(EditJurPaymentOperation operation, EditJurPaymentForm form)
	{
		ActionForward forward;
		if(form.isNewTemplate())
			forward = getCurrentMapping().findForward(FORWARD_EDIT_TEMPLATE);
		else
			forward = getCurrentMapping().findForward(FORWARD_EDIT_PAYMENT);
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(forward.getPath());
		urlBuilder.addParameter("form", FormConstants.JUR_PAYMENT_FORM);
		urlBuilder.addParameter("fromResource", operation.getChargeOffResource().getCode());
		urlBuilder.addParameter("receiverAccount", operation.getReceiverAccount());
		urlBuilder.addParameter("receiverINN", operation.getReceiverINN());
		urlBuilder.addParameter("receiverBIC", operation.getReceiverBIC());
		// если копия платежа то устанавливаем id и copy
		BusinessDocument document = operation.getDocument();
		if(form.isMarkReminder())
		{
			urlBuilder.addParameter("markReminder", "true");
		}

		Long templateId = operation.getTemplateId();
		if (templateId != null && !operation.isBillingPayment())
		{
			urlBuilder.addParameter("template", StringHelper.getEmptyIfNull(templateId));
		}
		else if (document != null && !operation.isEditPayment() && !operation.isBillingPayment())
		{
			urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(document.getId()));
			urlBuilder.addParameter("copying", "true");
		}
		return new ActionForward(urlBuilder.getUrl(), true);
	}

	protected ActionForward forwardBillingPayment(Long providerId, EditJurPaymentOperation operation, EditJurPaymentForm form)
	{
		ActionForward forward = getCurrentMapping().findForward(FORWARD_BILLING_PAYMENT);
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(forward.getPath());
		urlBuilder.addParameter("fromResource", operation.getChargeOffResource().getCode());
		urlBuilder.addParameter("recipient", String.valueOf(providerId));
		return new ActionForward(urlBuilder.getUrl(), true);
	}

	public ActionForward payByRequisites(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MessageCollector messageCollector = new MessageCollector();
		EditJurPaymentForm frm = (EditJurPaymentForm) form;
		EditJurPaymentOperation operation = createEditOperation(request, frm, messageCollector);

		Map<String, Object> map = frm.getFields();
		map.put(EditJurPaymentForm.FROM_RESOURCE_FIELD, frm.getFromResource());//Заполняем отдельно, тк хранится в отдельным свойстве формы
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(map), EditJurPaymentForm.EDIT_FORM);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			operation.setReceiverAccount((String) result.get(EditJurPaymentForm.RECEIVER_ACCOUNT_FIELD));
			operation.setReceiverBIC((String) result.get(EditJurPaymentForm.RECEIVER_BIC_FIELD));
			operation.setReceiverINN((String) result.get(EditJurPaymentForm.RECEIVER_INN_FIELD));
			operation.setChargeOffResource((PaymentAbilityERL) result.get(EditJurPaymentForm.FROM_RESOURCE_FIELD));
			try
			{
				ResourceType type = operation.getChargeOffResource().getResourceType();
				switch (type)
				{
					case ACCOUNT:
						frm.setNextURL(doPrepareJurPayment(operation,frm).getPath());
						return new ActionForward(frm.getNextURL(),true);
					case CARD:
						//карточные переводы всегда через биллинги с карт
						frm.setNextURL(doPrepareExternalProviderPayment(operation, null, frm, request).getPath());
						return new ActionForward(frm.getNextURL(),true);
					default:
						throw new BusinessException("Некорректный тип ресурса списания: "+ type);
				}
			}
			catch (InactiveExternalSystemException e)
			{
				saveError(request, e.getMessage());
			}
			catch (BusinessLogicException e)
			{
				saveError(request, e.getMessage());
			}
		}
		saveErrors(request,processor.getErrors());
		updateFormAdditionalData(frm, operation);
		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	/**
	 * Переход на форму оформления автоплатежа в адрес найденого в БД ПУ, предоставляющего автоплатеж.
	 * @param mapping - текущий маппинг
	 * @param form - форм
	 * @return форвард на переход создания автоплатежа в пользу найденого ПУ.
	 */
	public ActionForward makeRecipientAutoSubscription(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		EditJurPaymentForm frm = (EditJurPaymentForm) form;
		Long receiverId = Long.valueOf((String)frm.getField("receiverId"));
		if (receiverId != null)
		{
			ActionForward forward = getCurrentMapping().findForward(FORWARD_CREATE_RECIPIENT_AUTOSUB);
			UrlBuilder urlBuilder = new UrlBuilder();
			urlBuilder.setUrl(forward.getPath());
			urlBuilder.addParameter("createLongOffer", "true");
			urlBuilder.addParameter("recipient", receiverId.toString());
			return new ActionForward(urlBuilder.getUrl(), true);
		}
		throw new BusinessException("Неверный идентификатор поставщика услуг.");
	}
}
