package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.FormHelper;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.FieldValueValuesSource;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.*;
import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.phizic.business.*;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.ChangeCreditLimitClaim;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.P2PAutoTransferClaimBase;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.DocumentNotice;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.strategies.monitoring.FraudMonitoringDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.monitoring.FraudMonitoringSaveDocumentStrategy;
import com.rssl.phizic.business.documents.templates.source.ExistingTemplateSource;
import com.rssl.phizic.business.documents.templates.validators.ERIBTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.fraudMonitoring.exceptions.ProhibitionOperationFraudException;
import com.rssl.phizic.business.fraudMonitoring.exceptions.RequireAdditionConfirmFraudException;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.business.util.MaskingInfo;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;
import com.rssl.phizic.messaging.MessageTemplateType;
import com.rssl.phizic.messaging.MessagingHelper;
import com.rssl.phizic.operations.documents.templates.EditTemplateOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateESBAutoPayOperation;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperationBase;
import com.rssl.phizic.operations.payment.RemoveDocumentOperation;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.web.actions.DocumentActionBase;
import com.rssl.phizic.web.actions.NoActiveOperationException;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.log.FormLogParametersReader;
import com.rssl.phizic.web.struts.forms.ActionMessagesCollector;
import org.apache.struts.action.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 09.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 85552 $
 */
@SuppressWarnings({"JavaDoc"})
public abstract class EditDocumentActionBase extends DocumentActionBase
{
	public static final String TEMPLATE_DATA_STORE_KEY = "edit.template.operation.store.key";
	public static final String FORWARD_EDIT = "EditPayment";
	public static final String FORWARD_SHOW_LIST = "ShowList";
	public static final String FORWARD_BACK = "Close";
	public static final String FORWARD_EDIT_TEMPLATE = "EditTemplate";
	public static final String FORWARD_HISTORY = "History";
	public static final String AUTO_PAYMENT_BACK = "autoPaymentBack";
	public static final String AUTO_SUBSCRIPTION_BACK = "autoSubscriptionBack";
	public static final String FORWARD_PAYMENTS_CATEGORY = "ShowPaymentsCategoryForm";
	public static final String FORWARD_BACK_AUTOSUB = "BackAutoSub";
	public static final String FORWARD_BACK_FREE_DETAIL_AUTOSUB = "BackFreeDetailAutoSub";
	public static final String FORWARD_DEPO = "ShowDepoPayments";
	public static final String FORWARD_IMA = "ShowIMAPayments";
	public static final String FORWARD_LOAN_CLAIM_WITHOUT_REGISTRATION = "ShowLoanClaimWithoutRegistration";

    /**
	 * Возвращает тип создания для вновь создаваемых документов
	 * @return тип документа
	 */
	protected CreationType getNewDocumentCreationType()
	{
		return CreationType.internet;
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		map.put("button.next", "start");
		map.put("button.prev", "prev");
		map.put("button.saveAsDraft", "saveDraft");
		map.put("button.makeLongOffer", "makeLongOffer");
		map.put("button.makeAutoTransfer", "makeAutoTransfer");
		map.put("button.edit", "editPayment");
		map.put("button.remove", "remove");
		map.put("button.edit_template", "editTempalte");
		return map;
	}

	protected abstract EditDocumentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm frm, MessageCollector messageCollector) throws BusinessException, BusinessLogicException;

	protected void updateForm(CreatePaymentForm frm, EditDocumentOperation operation, FieldValuesSource valuesSource) throws BusinessException, BusinessLogicException
	{
		Metadata metadata = operation.getMetadata();
		frm.setHtml(buildFormHtml(operation, valuesSource, frm));
		frm.setForm(metadata.getName());
		frm.setMetadata(metadata);
		frm.setMetadataPath(operation.getMetadataPath());
		frm.setDocument(operation.getDocument());
		//некоторые документы могут в составе своих даных содержать информацию об ошибках. показываем пользователю их, если нужно
		saveErrors(currentRequest(), operation.getDocumentErrors(new ActionMessagesCollector()).errors());

	    // определяет, является ли платеж, пришедшим из ФНС или интернет-магазина
		if (operation instanceof EditDocumentOperationBase && frm instanceof EditPaymentForm)
		{
			EditDocumentOperationBase operationBase = (EditDocumentOperationBase) operation;
			((EditPaymentForm)frm).setExternalPayment(operationBase.isExternal());
			((EditPaymentForm)frm).setProviderName(operationBase.getProviderName());
		}

		if (ApplicationUtil.isNotMobileApi())
			showDisallowExternalAccountPaymentMessage(operation.getDocument());

		showRiskRecipietMessage(operation);
		try
		{
			if (operation.getDocument().isByTemplate() && FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER != operation.getDocument().getFormType())
			{
				Metadata temp = MetadataCache.getExtendedMetadata(operation.getDocument());
				FieldValuesSource source = new CompositeFieldValuesSource(new MapValuesSource(Collections.singletonMap("fromResource", operation.getDocument().getChargeOffResourceLink().getCode())), new TemplateFieldValueSource(operation.getDocument()));

				FormProcessor<ActionMessages, ?> processor = createFormProcessor(source, temp.getForm(), PreTemplateValidationStrategy.getInstance());
				if (!processor.process())
				{
					String message = "Для выполнения платежа по шаблону не хватает данных. Пожалуйста, отредактируйте шаблон в основном приложении «Сбербанк Онлайн».";

					ApplicationConfig applicationConfig = ApplicationConfig.getIt();
					if (Application.PhizIC == applicationConfig.getApplicationInfo().getApplication())
					{
						message = "Для выполнения платежа по шаблону не хватает данных. Пожалуйста, отредактируйте шаблон, перейдя по ссылке "
								+ "<a href=\"#\" onclick=\"createCommandButton(\'button.edit_template\', \'редактировать шаблон\').click(\'\', false);\">\"Редактировать\"</a>";
						frm.setTemplate(operation.getDocument().getTemplateId());
					}
					saveMessage(currentRequest(), message);
				}
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Отображает сообщение о недоступности внешних платежей со счета, если это актуально.
	 *
	 * @param document документ.
	 */
	protected void showDisallowExternalAccountPaymentMessage(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (DocumentHelper.isPaymentDisallowedFromAccount(document))
				saveMessage(currentRequest(), DocumentHelper.getDisallowedExternalAccountMessage(document));
		}
		catch(DocumentException ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Отображает сообщение о рискованности поставщика
	 * @param operation
	 * @throws BusinessException
	 */
	protected void showRiskRecipietMessage(EditDocumentOperation operation) throws BusinessException
	{
		if (operation instanceof CreateFormPaymentOperation)
			((CreateFormPaymentOperation)operation).fetchRiskFields();
	}

	/**
	 * Построить html формы платежа
	 */
	protected String buildFormHtml(EditDocumentOperation operation, FieldValuesSource fieldValuesSource, ActionForm form) throws BusinessException
	{
		String webRoot = currentRequest().getContextPath();
		String resourceRoot = currentServletContext().getInitParameter("resourcesRealPath");
		return operation.buildFormHtml(fieldValuesSource, webRoot, resourceRoot, getSkinUrl(form), ((ActionFormBase)form).isFromStart());
	}

	protected <T extends Operation> T getOperation(HttpServletRequest request, boolean throwIfNull) throws NoActiveOperationException
	{
		T operation = super.<T>getOperation(request, throwIfNull);
		if (operation instanceof EditDocumentOperation)
		{
			EditDocumentOperation editDocumentOperation = (EditDocumentOperation) operation;
			OperationContextUtil.synchronizeObjectAndOperationContext(editDocumentOperation.getDocument());
		}
		return operation;
	}

	/**
	 * Первоначальный показ страницы
	 */
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return startEditPayment(mapping, form, request);
	}

	/**
	 * Возвращает категорию формы, если таковая известна. Если неизвестна, то null.
	 *
	 * @param form
	 * @return
	 */
	protected String getFormCategory(CreatePaymentForm form)
	{
		String cat = form.getCategory();
		if (!StringHelper.isEmpty(cat))
			return cat;
		return FormConstants.formNameToCategory.get(form.getForm());
	}

	protected ActionForward startEditPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws BusinessException
	{
		CreatePaymentForm frm = (CreatePaymentForm) form;
		MessageCollector messageCollector = new MessageCollector();

		try
		{
			EditDocumentOperation operation = createEditOperation(request, frm, messageCollector);
			if (messageCollector.isNotEmpty())
			{
				processErrors(request, messageCollector);
			}
			BusinessDocument document = operation.getDocument();
			if (operation.upgrade())
				return DefaultDocumentAction.createDefaultEditForward(document.getId(), operation.getDocumentSate());
			// если документ изменился, то сохраняем его
			if ((document.isUpgradable() | showMessageBox(document)) && !"INITIAL".equals(document.getState().getCode()))
				((EditDocumentOperationBase) operation).saveDocument();

			// TODO будет переделано в рамках задачи Реализация формы заявки на кредит (мещерякова)
			/* Для предодобренной заявки на кредит: если у клиента в анкете прописан не паспорт РФ,
			   то клиенту выводится сообщение «Данная операция доступна только для резидентов РФ.»,
			   и форма заявки не отображается
			  */
			if (frm.getForm() != null && (frm.getForm().equals(FormConstants.LOAN_CARD_CLAIM) || frm.getForm().equals(FormConstants.LOAN_CARD_OFFER_FORM)) && !PersonHelper.isGuest() && !PersonHelper.hasRegularPassportRF())
			{
				String exceptionMessage = "Данная операция доступна только для резидентов РФ";
				ActionMessage error = new ActionMessage(exceptionMessage, false);
				saveSessionError(exceptionMessage, error, null);
				return mapping.findForward(FORWARD_BACK);
			}

			saveOperation(request, operation);

			FieldValuesSource valuesSource = operation.getFieldValuesSource();
			if(MaskPaymentFieldUtils.isRequireMasking())
				valuesSource = MaskPaymentFieldUtils.wrapMaskValuesSource(getMaskingInfo(operation, valuesSource), valuesSource);

			updateForm(frm, operation, valuesSource);

			addLogParameters(new BeanLogParemetersReader("Данные документа", operation.getDocument()));
			saveStateMachineEventMessages(request, operation, false);

			ActionForward forward = mapping.findForward(FORWARD_SHOW_FORM + operation.getMetadata().getName());
			forward = forward == null ? mapping.findForward(FORWARD_SHOW_FORM) : forward;
			if (LoanClaimHelper.isUnregisteredClientClaim(frm.getForm()))
				forward = mapping.findForward(FORWARD_LOAN_CLAIM_WITHOUT_REGISTRATION);
			return forward;
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
			if (StringHelper.isEmpty(getFormCategory(frm)) || ApplicationUtil.isApi())
			{
				return forwardBack(((CreatePaymentForm) form).getForm(), mapping, request);
			}

			return new ActionForward((mapping.findForward(FORWARD_PAYMENTS_CATEGORY).getPath() + "?categoryId=" + getFormCategory(frm)));
		}
		catch (TemporalBusinessException ignored)
		{
			String exceptionMessage = "По техническим причинам операция временно недоступна. Повторите попытку позже";
			ActionMessage error = new ActionMessage(exceptionMessage, false);
			saveSessionError(exceptionMessage, error, null);
			return forwardBack(((CreatePaymentForm) form).getForm(), mapping, request);
		}
		catch (BusinessLogicException e)
		{
			String exceptionMessage = e.getMessage();
			ActionMessage error = new ActionMessage(exceptionMessage, false);
			saveSessionError(exceptionMessage, error, null);

			return forwardBack(((CreatePaymentForm) form).getForm(), mapping, request);
		}
	}

	protected void processErrors(HttpServletRequest request, MessageCollector messageCollector)
	{
		// Добавляем информационные сообщения
		if (!messageCollector.getMessages().isEmpty())
		{
			ActionMessages msgs = new ActionMessages();
			for (String message : messageCollector.getMessages())
			{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
			}
			saveSessionMessages(request, msgs);
		}
		// Добавляем сообщения об ошибках
		if (!messageCollector.getErrors().isEmpty())
		{
			ActionMessages errors = new ActionMessages();
			for (String error : messageCollector.getErrors())
			{
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(error, false));
			}
			saveSessionErrors(request, errors);
		}
		// Добавляем ошибки о недоступности внешней системы
		if (!messageCollector.getInactiveErrors().isEmpty())
		{
			ActionMessages inactiveErrors = new ActionMessages();
			for (String inactiveError : messageCollector.getInactiveErrors())
			{
				inactiveErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(inactiveError, false));
			}
			saveInactiveESMessage(request, inactiveErrors);
		}
		messageCollector.clear();
	}

	protected Map<String, Object> checkFormData(CreatePaymentForm frm, EditDocumentOperation operation, ValidationStrategy strategy) throws BusinessException, BusinessLogicException
	{
		Metadata metadata = operation.getMetadata();
		FieldValuesSource valuesSource =  getValidateFormFieldValuesSource(frm, operation);

		Form form = metadata.getForm();
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, form, strategy);

		if (!processor.process())
		{
			saveErrors(currentRequest(), processor.getErrors());
			//передаем на форму ошибки валидации
			return null;
		}
		return processor.getResult();
	}

	/**
	 * Возвращает источник значений полей из HTTP-запроса
	 */
	protected FieldValuesSource getRequestFieldValuesSource() throws BusinessException
	{
		return new RequestValuesSource(currentRequest());
	}

	/**
	 * Возвращает композитный источник значений полей (при оплате по шаблону статические поля отображаем из шаблона)
	 *
	 * @param operation операция
	 * @return источник данных
	 * @throws BusinessException
	 */
	protected FieldValuesSource getShowFormFieldValuesSource(EditDocumentOperation operation) throws BusinessException
	{
		BusinessDocument document = operation.getDocument();
		if (document.isByTemplate())
		{
			//если оплата по шаблону, то в случае ошибки статические поля должны показать из шаблона
			return new CompositeFieldValuesSource(getRequestFieldValuesSource(), new FieldValueValuesSource(operation.getMetadata().getForm(), Collections.<String, String>emptyMap()));
		}
		if (FormHelper.isShowStatic(document.getFormType()))
		{
			return new CompositeFieldValuesSource(getRequestFieldValuesSource(), new FieldValueValuesSource(operation.getMetadata().getForm(), Collections.<String, String>emptyMap()));
		}
		return getRequestFieldValuesSource();
	}

	/**
	 * Возвращает источник значений полей для валидации формы
	 */
	protected FieldValuesSource getValidateFormFieldValuesSource(CreatePaymentForm frm, EditDocumentOperation operation) throws BusinessException, BusinessLogicException
	{
		EditDocumentOperationBase op = (EditDocumentOperationBase) operation;

		FieldValuesSource valuesSource =  getShowFormFieldValuesSource(operation);
		if(MaskPaymentFieldUtils.isRequireMasking())
			valuesSource = MaskPaymentFieldUtils.wrapUnmaskValuesSource(getMaskingInfo(op, valuesSource), valuesSource, getUnmaskFieldValuesSource(op, frm, valuesSource));

		return valuesSource;
	}

	protected FieldValuesSource getUnmaskFieldValuesSource(EditDocumentOperation operation, CreatePaymentForm frm, FieldValuesSource valuesSource) throws BusinessException
	{
		EditDocumentOperationBase op = (EditDocumentOperationBase) operation;
		return op.getDocumentFieldValuesSource();
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		EditDocumentOperation operation = getOperation(request);

		addLogParameters(new BeanLogParemetersReader("Первоначальные данные", operation.getDocument()));
		CreatePaymentForm frm = (CreatePaymentForm) form;
		ValidationStrategy strategy = getValidationStrategy(operation);
		try
		{
			Map<String, Object> formData = checkFormData(frm, operation, strategy);
			if (formData == null)
			{
				ActionForward forward = forwardShow(operation, frm);
				if (LoanClaimHelper.isUnregisteredClientClaim(frm.getForm()))
					forward = mapping.findForward(FORWARD_LOAN_CLAIM_WITHOUT_REGISTRATION);
				return forward;
			}
			addLogParameters(new FormLogParametersReader("Данные, введенные пользователем", operation.getMetadata().getForm(), formData));

			operation.updateDocument(formData);
			addInfoMessages(operation, formData);

			doFraudControl(operation.getDocument());

			operation.save();
			resetOperation(request);
			showRiskRecipietMessage(operation);
			return createNextStageDocumentForward(operation);
		}
		catch (InactiveExternalSystemException e)
		{
			if (DocumentHelper.isCallCentreExecutionAvailable(operation.getDocument()))
				saveInactiveESMessage(request, StrutsUtils.getMessage("message.callCentre", "paymentsBundle"));
			else
				saveInactiveESMessage(request, e);

			return forwardShow(operation, frm);
		}
		catch (RuntimeException e)
		{
			if (e.getMessage() != null && e.getMessage().substring(0, 3).matches("\\$\\d\\$"))
			{
				saveMessage(request, e.getMessage().substring(3));
				frm.setDocumentStatus(Integer.parseInt(e.getMessage().substring(1, 2)));
			}
			else
			{
				saveError(request, e);
			}
			return forwardShow(operation, frm);
		}
		catch (TemporalBusinessException ignored)
		{
			ActionMessages actionErrors = new ActionMessages();
			String exceptionMessage = "По техническим причинам операция временно недоступна. Повторите попытку позже.";
			actionErrors.add(exceptionMessage, new ActionMessage(exceptionMessage, false));
			saveErrors(request, actionErrors);
			return forwardShow(operation, frm);
		}
		catch (MessageBusinessLogicException e)
		{
			request.setAttribute("onLoad", "addMessage('" + e.getMessage() + "');");
			return forwardShow(operation, frm);
		}
		catch (IndicateFormFieldsException e)
		{
			// Ошибки и сообщения, для которых должны быть подсвечены поля
			ActionMessages actionErrors = new ActionMessages();
			boolean isEmptyFieldMessages = true;
			for (Map.Entry<String, String> entry : e.getFieldMessages().entrySet())
			{
				actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(entry.getKey(), new ActionMessage(entry.getValue(), false)));
				if (!StringHelper.isEmpty(entry.getValue()) && isEmptyFieldMessages)
				{
					isEmptyFieldMessages = false;
				}
			}
			if (e.isError())
			{
				saveErrors(request, actionErrors);
			}
			else
			{
				// Если ни для одного из полей нет сообщения с описанием проблемы, добавляем общее сообщение в
				// блоке информационных сообщений
				if (isEmptyFieldMessages)
				{
					actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				}
				saveMessages(request, actionErrors);
			}
			return forwardShow(operation, frm);
		}
		catch(ForceRedirectBusinessLogicException e)
		{
			UrlBuilder urlBuilder = new UrlBuilder();
			urlBuilder.setUrl(e.getRedirectUrl());
			urlBuilder.addParameter("errorMsg", StringHelper.getEmptyIfNull(e.getMessage()));
			return new ActionForward(urlBuilder.toString(), true);
		}
		catch (BusinessLogicMessageException e)
		{
			String message = StrutsUtils.getMessage(e.getMessage(), e.getBundle());
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(message, false));
			saveErrors(request, actionErrors);
			return forwardShow(operation, frm);
		}
		catch (BusinessLogicWithBusinessDocumentException e)
		{
			ActionForward forward = processDocumentInError(e, request, mapping);
			if (forward != null)
				return forward;
			else
				return forwardShow(operation, frm);
		}
		catch (BusinessLogicException e)
		{
			if (e.getMessage().substring(0, 3).matches("\\$\\d\\$"))
			{
				saveMessage(request, e.getMessage().substring(3));
				frm.setDocumentStatus(Integer.parseInt(e.getMessage().substring(1, 2)));
				return forwardShow(operation, frm);
			}
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionErrors);
			return forwardShow(operation, frm);
		}
		finally
		{
			saveStateMachineEventMessages(request, operation, false);
		}
	}

	protected void doFraudControl(BusinessDocument document) throws BusinessLogicException, BusinessException
	{
		try
		{
			FraudMonitoringDocumentStrategy strategy = new FraudMonitoringSaveDocumentStrategy(document);
			strategy.process(null, null);
		}
		catch (RequireAdditionConfirmFraudException e)
		{
			MessagingHelper.sendMessage(document, MessageTemplateType.REVIEW.name() + ".FM." + document.getClass().getName());
			throw e;
		}
		catch (ProhibitionOperationFraudException e)
		{
			MessagingHelper.sendMessage(document, MessageTemplateType.DENY.name() + ".FM." + document.getClass().getName());
			throw e;
		}
	}

	protected ActionForward forwardShow(EditDocumentOperation operation, CreatePaymentForm frm) throws BusinessException, BusinessLogicException, DocumentException
	{
		updateForm(frm, operation, getShowFormFieldValuesSource(operation));

		ActionForward forward = getCurrentMapping().findForward(FORWARD_SHOW_FORM + operation.getMetadata().getName());
		return forward != null ? forward : getCurrentMapping().findForward(FORWARD_SHOW_FORM);
	}

	protected ValidationStrategy getValidationStrategy(EditDocumentOperation operation)
	{
		BusinessDocument document = operation.getDocument();

		if (DocumentHelper.isUseLongOfferValidationStrategy(document))
		{
			return LongOfferValidationStrategy.getInstance();
		}

		if (document.isByTemplate())
		{
			return ByTemplateValidationStrategy.getInstance();
		}

		return DocumentValidationStrategy.getInstance();
	}

	private void addInfoMessages(EditDocumentOperation operation, Map<String, Object> formData) throws BusinessException
	{
		BusinessDocument document = operation.getDocument();
		String formName = document.getFormName();
		if ("LoanPayment".equals(formName))
		{
			BigDecimal amount = (BigDecimal) formData.get("amount");
			BigDecimal nextPaymentAmount = (BigDecimal) formData.get("nextPaymentAmount");
			if (!(formData.get("loanCurrency")).equals(formData.get("resourceCurrency")))
				saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.client.payments.loan.info.variousCurrencies"), null);
			else if(amount != null && amount.compareTo(nextPaymentAmount)<0)
				saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.client.payments.loan.info.recommendedAmount"), null);
		}
		else if("SecuritiesTransferClaim".equals(formName))
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.client.payments.SecuritiesTransferClaim.info"), null);
		else if (formName.equals(FormConstants.CHANGE_CREDIT_LIMIT_CLAIM) && document.getCreationType() == CreationType.internet)
		{
			if (((ChangeCreditLimitClaim) document).getFeedbackType().equals(FeedbackType.ACCEPT.toString()))
				currentRequest().getSession().setAttribute("documentNotice", new DocumentNotice("TICK", StrutsUtils.getMessage("message.change.limit.title", "paymentsBundle"), StrutsUtils.getMessage("message.change.limit.accept", "paymentsBundle", DateHelper.getCountDaysToString(((ChangeCreditLimitClaim) document).getOfferExpDate()))));
		}
		if (ApplicationUtil.isMobileApi())
		{
			String tarifPlanCodeType = (String) formData.get("tarifPlanCodeType");
			String tariffPlanCode = (String) formData.get("tariffPlanCode");
			TariffPlanHelper tariffPlanHelper = new TariffPlanHelper();
			if (tariffPlanCode == null && StringHelper.isNotEmpty(tarifPlanCodeType))
				tariffPlanCode = tariffPlanHelper.getCodeBySynonym(tarifPlanCodeType);

			if (tariffPlanCode != null && !TariffPlanHelper.isUnknownTariffPlan(tariffPlanCode))
			{
				String tarifPlanMessage = PersonHelper.getTarifPlanConfigMeessage(tariffPlanCode);
				if (StringHelper.isNotEmpty(tarifPlanMessage))
				{
					String fromResourceCurrency = (String) formData.get("fromResourceCurrency");
					String toResourceCurrency = (String) formData.get("toResourceCurrency");
					String fromResourceLink = (String) formData.get("fromResourceLink");
					String toResourceLink = (String) formData.get("toResourceLink");

					if ( StringHelper.isNotEmpty(fromResourceCurrency) &&
							StringHelper.isNotEmpty(toResourceCurrency) &&
							StringHelper.isNotEmpty(fromResourceLink) &&
							StringHelper.isNotEmpty(toResourceLink) &&
							!fromResourceCurrency.equalsIgnoreCase(toResourceCurrency) &&
                            !(fromResourceLink.startsWith(IMAccountLink.CODE_PREFIX) || toResourceLink.startsWith(IMAccountLink.CODE_PREFIX)) &&
							!(fromResourceLink.startsWith(CardLink.CODE_PREFIX) &&
									(toResourceLink.startsWith(CardLink.CODE_PREFIX) || toResourceLink.startsWith(AccountLink.CODE_PREFIX))) )
						saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(tarifPlanMessage, false), null);
				}
			}
		}
	}

	/**
	 * создать форвард на следующую стадию работы с документом
	 * @param operation
	 * @return
	 */
	protected ActionForward createNextStageDocumentForward(EditDocumentOperation operation)
	{
		return DefaultDocumentAction.createDefaultEditForward(operation.getDocument().getId(), operation.getDocumentSate());
	}

	public ActionForward saveDraft(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		EditDocumentOperation operation = getOperation(request);
		CreatePaymentForm frm = (CreatePaymentForm) form;

		Map<String, Object> formData = checkFormData(frm, operation, DraftValidationStrategy.getInstance());
		if (formData != null)
		{
			operation.updateDocument(formData);
			operation.saveDraft();
			ActionMessages msg = new ActionMessages();
			msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Черновик успешно сохранен", false));
			saveMessages(request, msg);
		}
		return forwardShow(operation, frm);
	}

	public ActionForward makeAutoTransfer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreatePaymentForm frm = (CreatePaymentForm) form;
		MessageCollector messageCollector = new MessageCollector();
		EditDocumentOperation storedOperation = getOperation(request);
		String serviceName = getAutoSubscriptionServiceName(storedOperation);

		try
		{
			FormProcessor<ActionMessages, ?> processor = createFormProcessor(getRequestFieldValuesSource(), MetadataCache.getExtendedMetadata(serviceName, getRequestFieldValuesSource()).getForm(), PrepareLongOfferValidationStrategy.getInstance());
			if (processor.process())
			{
				EditDocumentOperation editOperation = createAutoSubscriptionOperation(serviceName, new MapValuesSource(processor.getResult()), messageCollector);
				editOperation.makeLongOffer(null);

				return createNextStageDocumentForward(editOperation);
			}
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
			return forwardShow(storedOperation, frm);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return forwardShow(storedOperation, frm);
		}

		return new ActionForward(DefaultDocumentAction.createStateUrl(storedOperation.getDocument(), storedOperation.getDocumentSate()) + "?form=" + serviceName + "&receiverType=" + getAutoSubscriptionReceiverType(storedOperation), true);
	}

	/**
	 * Получить наименование сервиса при создании подписки
	 * @return наименование сервиса
	 */
	protected String getAutoSubscriptionServiceName(EditDocumentOperation operation)
	{
		return FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM.getName();
	}

	/**
	 * Получить наименование сервиса при создании подписки
	 * @return наименование сервиса
	 */
	protected String getAutoSubscriptionReceiverType(EditDocumentOperation operation)
	{
		FormType formType = operation.getDocument().getFormType();
		if (FormType.INTERNAL_TRANSFER == formType)
		{
			return P2PAutoTransferClaimBase.SEVERAL_RECEIVER_TYPE_VALUE;
		}
		if (FormType.INDIVIDUAL_TRANSFER == formType || FormType.INDIVIDUAL_TRANSFER_NEW == formType)
		{
			return RurPayment.PHIZ_RECEIVER_TYPE_VALUE;
		}
		throw new IllegalArgumentException("Неподдерживаемый тип платежа, FormType = " + formType);
	}

	public ActionForward makeLongOffer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreatePaymentForm frm = (CreatePaymentForm) form;
		EditDocumentOperation operation = getOperation(request);
		if (!checkOperation(frm, operation.getDocument()))
		{
			saveError(request, "У вас нет прав на создание автоплатежа.");
			return forwardShow(operation, frm);
		}

		try
		{
			if(doLongOffer(operation, getRequestFieldValuesSource()))
			{
				BusinessDocument document = operation.getDocument();
				// если автоподписка то переходим по дефолту
				if(document instanceof AbstractPaymentSystemPayment)
				{
					if (FormConstants.RUR_PAYMENT_FORM.equals(frm.getForm()))
					{
						operation.saveAsInitial();
					}

					return createNextStageDocumentForward(operation);
				}
			}
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}

		return forwardShow(operation, frm);
	}

	//проверка прав на операцию
	//в зависимости от услуги , проверяем есть ли права на тот или иной сервис
	private boolean checkOperation(CreatePaymentForm frm, BusinessDocument document) throws BusinessException
	{
		if (frm.getForm().equals(FormConstants.INTERNAL_PAYMENT_FORM) || frm.getForm().equals(FormConstants.CONVERT_CURRENCY_PAYMENT_FORM) || frm.getForm().equals(FormConstants.LOAN_PAYMENT_FORM))
		{
			return checkAccess(CreateFormPaymentOperation.class,"CreateLongOfferPayment");
		}
		else if (frm.getForm().equals(FormConstants.RUR_PAYMENT_FORM))
		{
			return checkAccess(CreateFormPaymentOperation.class,"CreateLongOfferPaymentForRur");
		}
		else if (frm.getForm().equals(FormConstants.JUR_PAYMENT_FORM))
		{
			return checkAccess(CreateFormPaymentOperation.class,"CreateLongOfferPaymentForJur");
		}
		else if (frm.getForm().equals(FormConstants.SERVICE_PAYMENT_FORM))
		{
			return checkAccess(CreateESBAutoPayOperation.class, "ClientCreateAutoPayment") && isFreeRequisitesAutoPayAvailable(document);
		}
		else if (frm.getForm().equals(FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM.getName()))
		{
			return checkAccess(CreateFormPaymentOperation.class, "CreateP2PAutoTransferClaim");
		}
		else
		{
			return false;
		}
	}

	/**
	 * @param document документ
	 * @return Разрешен ли автоплатеж по произвольным реквизитам
	 * @throws BusinessException
	 */
	private boolean isFreeRequisitesAutoPayAvailable(BusinessDocument document) throws BusinessException
	{
		JurPayment payment = (JurPayment)document;
		// Создавать автоплатеж по свободным реквизитам со второго шага запрещено
		return payment.getReceiverInternalId() != null || payment.isLongOffer();
	}

	/**
	 * Проверка на валидность данных длительного поручения
	 * @param operation
	 * @return true данные валидны
	 * @throws BusinessException
	 */
	protected boolean doLongOffer(EditDocumentOperation operation, FieldValuesSource valuesSource) throws BusinessException, BusinessLogicException
	{
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, operation.getMetadata().getForm(), PrepareLongOfferValidationStrategy.getInstance());
		boolean isValid = processor.process();
		if (isValid)
		{
			operation.makeLongOffer(processor.getResult());
		}
		else
		{
			saveErrors(currentRequest(), processor.getErrors());
		}

		return isValid;
	}

	protected EditDocumentOperation createAutoSubscriptionOperation(String formName, FieldValuesSource source, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		EditDocumentOperation operation = createOperation(CreateFormPaymentOperation.class, formName);
		operation.initialize(new NewDocumentSource(formName, source, getNewDocumentCreationType(), CreationSourceType.ordinary, messageCollector), source);
		return operation;
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		CreatePaymentForm frm = (CreatePaymentForm) form;
		boolean isLongOffer = frm.getDocument() instanceof AbstractLongOfferDocument && frm.getDocument().isLongOffer();
		return mapping.getPath() + "/" + frm.getMetadataPath() + (isLongOffer ? "/" + "LongOffer" : "");
	}

	public ActionForward prev(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreatePaymentForm frm = (CreatePaymentForm) form;
		EditDocumentOperation operation = getOperation(request);
		BusinessDocument document = operation.getDocument();
		resetOperation(request);//убиваем операцию
		ActionForward forward = findBackForward(mapping, document);
		if (frm.getId() == null)
		{
			return forward;
		}
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(forward.getPath());
		urlBuilder.addParameter("id", frm.getId().toString());
		return new ActionForward(urlBuilder.getUrl(), true);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateFormPaymentOperation operation = getOperation(request);
		RemoveDocumentOperation removeOperation = createOperation(RemoveDocumentOperation.class, operation.getMetadata().getName());
		removeOperation.initialize(operation.getDocument());
		removeOperation.remove();
		if (!StringHelper.isEmpty(request.getParameter("history")))
		{
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("paymentsBundle", "com.rssl.phizic.web.client.payments.remove.payment"), false), null);
			return mapping.findForward(FORWARD_HISTORY);
		}
		else
			return mapping.findForward(FORWARD_BACK);
	}

	protected ActionForward findBackForward(ActionMapping mapping, BusinessDocument document)
	{
		// для автоподписки и автоплатежа переходим на первый шаг создания автоплатежа
		if((document instanceof JurPayment && ((JurPayment) document).isLongOffer()))
		{
			//для автоплатежа по свободным реквизитам переходим на первый шаг заполнения реквизитов
			if(((JurPayment)document).getReceiverInternalId() == null)
				return mapping.findForward(FORWARD_BACK_FREE_DETAIL_AUTOSUB);
			return mapping.findForward(FORWARD_BACK_AUTOSUB);
		}
		else if(document instanceof CreateAutoPayment)
			return mapping.findForward(FORWARD_BACK_AUTOSUB);

		ActionForward forward = mapping.findForward(FORWARD_BACK + document.getFormName());
		if (forward != null)
		{
			return forward;
		}
		return mapping.findForward(FORWARD_BACK);
	}

	protected EditDocumentOperation createEditTemplateOperation(CreatePaymentForm form, HttpServletRequest request) throws NoActiveOperationException, BusinessException, BusinessLogicException
	{
		return getOperation(request);
	}

	protected ActionForward forwardBack(String formName, ActionMapping mapping, HttpServletRequest request)
	{
		if (FormConstants.EDIT_AUTOPAYMENT_FORM.equals(formName) || FormConstants.REFUSE_AUTOPAYMENT_FORM.equals(formName))
		{
			ActionForward forward = new ActionForward();
			forward.setPath(mapping.findForward(AUTO_PAYMENT_BACK).getPath() + request.getParameter("linkId"));
			forward.setRedirect(true);
			return forward;
		}
		else if (FormConstants.DELAY_AUTOSUBSCRIPTION_PAYMENT_FORM.equals(formName) || FormConstants.REFUSE_AUTOSUBSCRIPTION_PAYMENT_FORM.equals(formName)
				|| FormConstants.EDIT_AUTOSUBSCRIPTION_PAYMENT.equals(formName) || FormConstants.RECOVERY_AUTOSUBSCRIPTION_PAYMENT_FORM.equals(formName)
				|| FormConstants.DELAY_P2P_AUTO_TRANSFER_CLAIM_FORM.equals(formName) || FormConstants.RECOVERY_P2P_AUTO_TRANSFER_CLAIM_FORM.equals(formName)
				|| FormConstants.CLOSE_P2P_AUTO_TRANSFER_CLAIM_FORM.equals(formName))
		{
			ActionForward forward = new ActionForward();
			forward.setPath(mapping.findForward(AUTO_SUBSCRIPTION_BACK).getPath() + request.getParameter("autoSubNumber"));
			forward.setRedirect(true);
			return forward;
		}
		else if (FormConstants.DEPOSITOR_FORM_CLAIM_FORM.equals(formName) || FormConstants.RECALL_DEPOSITARY_CLAIM_FORM.equals(formName)
				|| FormConstants.SECURIRIES_REGISTRATION_CLAIM_FORM.equals(formName) || FormConstants.SECURIRIES_TRANSFER_CLAIM_FORM.equals(formName))
		{
			ActionForward forward = new ActionForward();
			forward.setPath(mapping.findForward(FORWARD_DEPO).getPath());
			forward.setRedirect(true);
			return forward;
		}
		else if (FormConstants.IMA_PAYMENT_FORM.equals(formName))
		{
			ActionForward forward = new ActionForward();
			forward.setPath(mapping.findForward(FORWARD_IMA).getPath());
			forward.setRedirect(true);
			return forward;
		}
		else
			return mapping.findForward(FORWARD_BACK);
	}

	/**
	 * Веб метод для редатирования документа(через машину состояния)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreatePaymentForm frm = (CreatePaymentForm) form;
		EditDocumentOperation operation = getOperation(request);
		try
		{
			// обрабатывем машиной состояния
			operation.edit();

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getDocument()));

			return createEditForward(operation);
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
			return forwardShow(operation, frm);
		}
		catch(BusinessLogicException e)
		{
			saveError(request, e);
			return forwardShow(operation, frm);
		}
	}

	public ActionForward editTempalte(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreatePaymentForm frm = (CreatePaymentForm) form;

		ExistingTemplateSource source = new ExistingTemplateSource(frm.getTemplate(), new ERIBTemplateValidator(), new OwnerTemplateValidator());
		EditTemplateOperation operation = createOperation(EditTemplateOperation.class, source.getMetadata().getName());
		operation.initialize(source);

		operation.edit();

		addLogParameters(new BeanLogParemetersReader("Редактируемая сущность", operation.getTemplate()));

		return DefaultDocumentAction.createDefaultEditForward(operation.getTemplate().getId(), operation.getExecutor().getCurrentState());
	}

	/**
	 * @param operation операция для вычисления форварда
	 * @return форвард для перехода после редактирования
	 */
	protected ActionForward createEditForward(EditDocumentOperation operation)
	{
		BusinessDocument document = operation.getDocument();
		//автоподписка по свободным реквизитам
		if((document instanceof JurPayment) && document.isLongOffer() && ((JurPayment) document).getReceiverInternalId() == null)
		{
			UrlBuilder builder = new UrlBuilder(getCurrentMapping().findForward(FORWARD_BACK_FREE_DETAIL_AUTOSUB).getPath());
			builder.addParameter("id", document.getId().toString());
			return new ActionForward(builder.getUrl(), true);
		}
		// если автоподписка
		if((document instanceof JurPayment) && document.isLongOffer())
		{
			UrlBuilder builder = new UrlBuilder(getCurrentMapping().findForward(FORWARD_BACK_AUTOSUB).getPath());
			builder.addParameter("id", document.getId().toString());
			return new ActionForward(builder.getUrl(), true);
		}

		return createNextStageDocumentForward(operation);
	}

	/**
	 * @param operation операция
	 * @param valuesSource окружение значений
	 * @return Информация для маскирования полей
	 */
	protected MaskingInfo getMaskingInfo(EditDocumentOperation operation, FieldValuesSource valuesSource) throws BusinessException
	{
		return new MaskingInfo(operation.getMetadata());
	}
}
