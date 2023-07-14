package com.rssl.phizic.web.common.client.payments.forms;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.documents.templates.validators.ERIBTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.TemplateValidator;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.documents.templates.EditTemplateDocumentOperation;
import com.rssl.phizic.operations.documents.templates.RemoveTemplateOperation;
import com.rssl.phizic.operations.forms.ViewTemplateOperation;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.payments.forms.DefaultDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.ViewTemplateActionBase;
import com.rssl.phizic.web.actions.payments.forms.ViewTemplateForm;
import com.rssl.phizic.web.actions.templates.DefaultTemplateAction;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ViewTemplateAction extends ViewTemplateActionBase
{
	private static final String OPEN_PAYMENTS_AND_TEMPLATES = "PaymentsAndTemplates";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.remove", "remove");
		map.put("button.edit_template", "edit");
		map.put("button.makeLongOffer", "makeLongOffer");
		return map;
	}

	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		TemplateDocumentSource source = getTemplateSource(form);
		ViewTemplateOperation operation = createOperation(ViewTemplateOperation.class, source.getMetadata().getName());
		ViewTemplateForm frm = (ViewTemplateForm) form;

		//≈сли пришли на форму просмотра из календар€, значит необходимо добавить в шаблон информацию о напоминании
		if (frm.isFromFinanceCalendar() && StringHelper.isNotEmpty(frm.getExtractId()))
		{
			operation.initializeFromFinanceCalendar(source, frm.getExtractId());
		}
		else
		{
			operation.initialize(source);
		}

		return operation;
	}

	public ActionForward makeLongOffer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			ViewTemplateForm frm = (ViewTemplateForm) form;
			MessageCollector messageCollector = new MessageCollector();
			EditDocumentOperation operation = createCreateLongOfferOperation(frm, messageCollector);

			addLogParameters(new BeanLogParemetersReader("ќбрабатываема€ сущность", operation.getDocument()));

			// посылаем null т.к. документ не должен ничем обновл€тс€
			operation.makeLongOffer(null);
			UrlBuilder urlBuilder = new UrlBuilder();
			urlBuilder.setUrl(DefaultDocumentAction.createStateUrl(operation.getDocumentSate()));
			urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(operation.getDocument().getId()));

			return new ActionForward(urlBuilder.toString(), true);
		}
		catch (BusinessLogicException e)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveSessionErrors(request, msgs);

			return mapping.findForward(OPEN_PAYMENTS_AND_TEMPLATES);
		}
	}

	private EditDocumentOperation createCreateLongOfferOperation(ViewTemplateForm frm, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		TemplateDocumentSource existingSource = getTemplateSource(frm);
		TemplateDocument template = existingSource.getTemplate();

		//ѕровер€ем на возможность создани€ автоплатежа по шаблону
		if (FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER != template.getFormType())
		{
			throw new BusinessLogicException("ƒл€ шаблона " + template.getTemplateInfo().getName() + " ¬ы не можете создать автоплатеж.");
		}

		ServiceProviderShort provider = TemplateHelper.getTemplateProviderShort(template);
		//ѕровер€ем на возможность создани€ автоплатежа по поставщику
		if (!AutoPaymentHelper.isAutoPaymentAllowed(provider))
		{
			throw new BusinessLogicException("ƒл€ шаблона " + template.getTemplateInfo().getName() + " ¬ы не можете создать автоплатеж.");
		}

		boolean isAutoSubscriptionPay = provider == null || !AutoPaymentHelper.isIQWProvider(provider.getSynchKey());
		String paymentForm = isAutoSubscriptionPay ? FormConstants.SERVICE_PAYMENT_FORM : FormConstants.CREATE_AUTOPAYMENT_FORM;
		FieldValuesSource initialValuesSource = new TemplateFieldValueSource(template);

		DocumentSource newDocumentSource = isAutoSubscriptionPay ?
				new NewDocumentSource(template, CreationType.internet, new OwnerTemplateValidator()) :
				new NewDocumentSource(paymentForm, initialValuesSource, CreationType.internet, CreationSourceType.ordinary, messageCollector);

		CreateFormPaymentOperation operation = createOperation(CreateFormPaymentOperation.class, paymentForm);
		operation.initialize(newDocumentSource);
		return operation;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		ViewTemplateForm frm = (ViewTemplateForm) form;

		try
		{
			EditTemplateDocumentOperation operation = createEditEntityOperation(frm);
			operation.edit();

			addLogParameters(new BeanLogParemetersReader("–едактируема€ сущность", operation.getTemplate()));

			return DefaultTemplateAction.redirectEditTemplateForm(operation.getTemplate(), operation.getExecutor().getCurrentState());
		}
		catch (BusinessLogicException e)
		{
			saveError(currentRequest(), e.getMessage());
			return start(mapping, form, request, response);
		}
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		ViewTemplateForm frm = (ViewTemplateForm) form;

		try
		{
			RemoveTemplateOperation operation = createRemoveEntityOperation(frm);
			addLogParameters(new BeanLogParemetersReader("ƒанные удал€емой сущности", operation.getEntity()));
			operation.remove();

			return createRemoveTemplateForward();
		}
		catch (BusinessLogicException e)
		{
			saveError(currentRequest(), e.getMessage());
			return mapping.findForward(FORWARD_START);
		}
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewTemplateForm frm = (ViewTemplateForm) form;
		ViewTemplateOperation viewOperation = (ViewTemplateOperation) operation;

		super.updateFormData(operation, form);

		// обновл€ем признак доступности внешних платежей со счета
		updateExternalAccountPaymentAllowed(frm, viewOperation);

		saveMessages(currentRequest(), viewOperation.collectInfo());
    }

	/**
	 * ќбновить признак доступности шаблонов платежей внешнему получателю со счета
	 * если шаблон платежа не досупен - сообщаем об этом пользователю
	 *
	 * @param form форма
	 * @param operation операци€
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private void updateExternalAccountPaymentAllowed(ViewTemplateForm form, ViewTemplateOperation operation) throws BusinessLogicException, BusinessException
	{
		TemplateDocument template = operation.getTemplate();
		boolean result = TemplateHelper.isTemplateDisallowedFromAccount(template);

		form.setExternalAccountPaymentAllowed(!result);
		if (result)
		{
			saveMessage(currentRequest(), Constants.EXTERNAL_ACCOUNT_PAYMENT_TEMPLATE_ERROR_MESSAGE);
		}
	}

	@Override
	protected TemplateValidator[] getTemplateValidators() throws BusinessException
	{
		return new TemplateValidator[]{new OwnerTemplateValidator(), new ERIBTemplateValidator()};
	}

	protected ActionForward createRemoveTemplateForward()
	{
		return getCurrentMapping().findForward(OPEN_PAYMENTS_AND_TEMPLATES);
	}

}
