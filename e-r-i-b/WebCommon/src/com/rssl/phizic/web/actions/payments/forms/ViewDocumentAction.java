package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.PrepareLongOfferValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.documents.P2PAutoTransferClaimBase;
import com.rssl.phizic.business.documents.exceptions.NotOwnDocumentException;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.metadata.source.ReceiverFieldValueSource;
import com.rssl.phizic.business.documents.metadata.source.ResourceLinkFieldValueSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.payments.CardToAccountTransfer;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.payment.RecallDocumentOperation;
import com.rssl.phizic.operations.payment.RemoveDocumentOperation;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.help.HelpIdGenerator;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 04.09.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class ViewDocumentAction extends ViewDocumentActionBase
{
	private static final String MESSAGE_RECALL = "¬ы не можете отозвать этот документ";
	private static final String FORWARD_VIEW_FORM = "View";
	public static final String FORWARD_HISTORY = "History";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.withdraw", "recall");
		map.put("button.remove","remove");
		map.put("button.makeLongOffer", "makeLongOffer");
		map.put("button.makeAutoTransfer", "makeAutoTransfer");
		return map;
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;

		ExistingSource source = null;
		try
		{
		    source = new ExistingSource(frm.getId(), new IsOwnDocumentValidator());
		}
		catch(NotOwnDocumentException e)
		{
		    throw new AccessException(e.getMessage());
		}
		ViewDocumentOperation operation = createOperation(ViewDocumentOperation.class, source.getMetadata().getName());
		operation.initialize(source);
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		ViewDocumentOperation op =  (ViewDocumentOperation) operation;

		BusinessDocument doc = op.getEntity();

		frm.setPrintCheck(op.checkPrintCheck());
		if (doc instanceof AbstractAccountsTransfer)
		{
			AbstractAccountsTransfer accountsTransfer = (AbstractAccountsTransfer) doc;
			frm.setProviderName(accountsTransfer.getReceiverName());
			frm.setExternalPayment(op.getExternalPayment());
			frm.setFnsPayment(op.getFnsPayment());
		}

		super.updateFormData(op, form);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		DocumentSource source = new ExistingSource(frm.getId(), new IsOwnDocumentValidator());

		RemoveDocumentOperation removeOperation = createOperation(RemoveDocumentOperation.class, source.getMetadata().getName());

		removeOperation.initialize(source);
		removeOperation.remove();

		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("paymentsBundle", "com.rssl.phizic.web.client.payments.remove.payment"), false), null);

		return mapping.findForward(FORWARD_HISTORY);
	}

	public ActionForward recall(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		DocumentSource source = new ExistingSource(frm.getId(), new IsOwnDocumentValidator());

		RecallDocumentOperation operation = createOperation(RecallDocumentOperation.class, source.getMetadata().getName());
		operation.initialize(source);

		if (operation.getDocumentState().equals(new State("DELAYED_DISPATCH")) || operation.getDocumentState().equals(new State("OFFLINE_DELAYED")))
		{
			operation.recall();
		}
		else
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(MESSAGE_RECALL, false));
			saveErrors(request, errors);
		}
		return start(mapping, frm, request, response);
	}


	public ActionForward makeLongOffer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		MessageCollector messageCollector = new MessageCollector();
		try
		{
			DocumentSource existingSource = createExistingDocumentSource(frm);
			BusinessDocument originalDocument = existingSource.getDocument();
			if(originalDocument instanceof InternalTransfer)
			{
				InternalTransfer payment = (InternalTransfer) originalDocument;
				if(payment.asGateDocument().getType() != CardToAccountTransfer.class)
					return getErrorForward("ƒл€ данного документа нельз€ создать автоплатеж", mapping, frm, request, response);
				return createInitialMoneyBoxForward(payment);
			}
			if(originalDocument instanceof AccountOpeningClaim)
			{
				AccountOpeningClaim claim = (AccountOpeningClaim) originalDocument;
				AccountLink toResource = PersonContext.getPersonDataProvider().getPersonData().findAccount(claim.getReceiverAccount());
				if (toResource == null)
					return getErrorForward("ѕо техническим причинам дл€ открываемого вклада не удалось подключить копилку.", mapping, frm, request, response);

				return new ActionForward("/private/payments/payment.do?form=" + FormConstants.CREATE_MONEY_BOX_FORM +"&toResource=" + toResource.getCode(), true);
			}
			//Ќе используем AutoPaymentHelper.isAutoPaymentAllowed(BusinessDocument document), что бы не обращатьс€ 2 раза в Ѕƒ за поставщиком
			if (!(originalDocument instanceof JurPayment))
				return getErrorForward("ƒл€ данного документа нельз€ создать автоплатеж", mapping, frm, request, response);

			JurPayment payment = (JurPayment) originalDocument;
			if (payment.asGateDocument().getType() != CardPaymentSystemPayment.class)
				return getErrorForward("ƒл€ данного документа нельз€ создать автоплатеж", mapping, frm, request, response);

			ServiceProviderShort provider = getProviderForLongOffer(payment);
			boolean isAutoSubscriptionPay = provider == null || !AutoPaymentHelper.isIQWProvider(provider.getSynchKey());
			PaymentAbilityERL resourceLink = payment.getChargeOffResourceLink();
			if (resourceLink == null)
			{
				ActionForward forward = mapping.findForward("StartNewAutoPayment");
				return new ActionRedirect(forward.getPath() + "?recipient=" + payment.getReceiverInternalId() + "&createLongOffer=true&originalPaymentId=" + payment.getId());
			}

			EditDocumentOperation operation = createCreateLongOfferOperation(isAutoSubscriptionPay, existingSource, payment, messageCollector);
			addLogParameters(new BeanLogParemetersReader("ќбрабатываема€ сущность", operation.getDocument()));
			// посылаем null т.к. документ не должен ничем обновл€тс€
			operation.makeLongOffer(null);

			return createNextStageDocumentForward(operation);
		}
		catch (BusinessLogicException e)
		{
			return getErrorForward(e.getMessage(), mapping, frm, request, response);
		}
	}

	private ActionForward getErrorForward(String message, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		saveError(request, message);
		if(ApplicationUtil.isApi())
			return mapping.findForward("unsupported");

		return start(mapping, form, request, response);
	}

	public ActionForward makeAutoTransfer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		MessageCollector messageCollector = new MessageCollector();
		EditDocumentOperation operation = createOperation(CreateFormPaymentOperation.class, FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM.getName());

		DocumentSource existingDocumentSource = createExistingDocumentSource(frm);
		FieldValuesSource existingValuesSource = getFieldValueSource(existingDocumentSource.getDocument());

		try
		{
			FormProcessor<ActionMessages, ?> processor = createFormProcessor(existingValuesSource, MetadataCache.getExtendedMetadata(FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM.getName(), existingValuesSource).getForm(), PrepareLongOfferValidationStrategy.getInstance());
			if (processor.process())
			{
				operation.initialize(new NewDocumentSource(FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM.getName(), new MapValuesSource(processor.getResult()), getDocumentCreationType(), CreationSourceType.ordinary, messageCollector));
				operation.makeLongOffer(null);

				return createNextStageDocumentForward(operation);
			}
		}
		catch (BusinessLogicException e)
		{
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false), null);
			return new ActionForward(mapping.findForward(FORWARD_VIEW_FORM).getPath() + "?id=" + frm.getId(), true);
		}

		operation.initialize(existingDocumentSource);

		return createDefaultStageDocumentForward(operation);
	}

	private FieldValuesSource getFieldValueSource(BusinessDocument document) throws BusinessException
	{
		return new CompositeFieldValuesSource(new ResourceLinkFieldValueSource(document), new ReceiverFieldValueSource(document), new DocumentFieldValuesSource(MetadataCache.getBasicMetadata(document.getFormName()), document));
	}

	protected EditDocumentOperation createCreateLongOfferOperation(boolean isAutoSubscriptionPay, DocumentSource existingSource, JurPayment payment, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		String paymentForm = isAutoSubscriptionPay ? FormConstants.SERVICE_PAYMENT_FORM : FormConstants.CREATE_AUTOPAYMENT_FORM;

		FieldValuesSource initialValuesSource = new DocumentFieldValuesSource(existingSource.getMetadata(), payment);
		DocumentSource newDocumentSource = isAutoSubscriptionPay ?
				new NewDocumentSource(payment, new IsOwnDocumentValidator(), getDocumentCreationType()) :
				new NewDocumentSource(paymentForm, initialValuesSource, getDocumentCreationType(), CreationSourceType.ordinary, messageCollector);

		CreateFormPaymentOperation operation = createOperation(CreateFormPaymentOperation.class, paymentForm);
		operation.initialize(newDocumentSource);
		return operation;
	}

	protected CreationType getDocumentCreationType()
	{
		return CreationType.internet;
	}

	private ServiceProviderShort getProviderForLongOffer(JurPayment payment) throws BusinessLogicException, BusinessException
	{
		ServiceProviderShort provider = ServiceProviderHelper.getServiceProvider(payment.getReceiverInternalId());
		if (!AutoPaymentHelper.isAutoPaymentAllowed(provider))
		{
			throw new BusinessLogicException("ƒл€ данного документа нельз€ создать автоплатеж");
		}
		return provider;
	}

	protected boolean isAutoPaymentAllowed(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		return AutoPaymentHelper.isAutoPaymentAllowed(document);
	}

	private DocumentSource createExistingDocumentSource(ViewDocumentForm frm) throws BusinessException, BusinessLogicException
	{
		Long paymentId = frm.getId();
		if (paymentId == null && paymentId <= 0)
			throw new BusinessException("Ќе указан идентификатор платежа");

		// отключаем синхронизацию
		return new ExistingSource(frm.getId(), new IsOwnDocumentValidator())
		{
			protected boolean isSynchContext()
			{
				return false;
			}
		};
	}

	@Override
	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		String helpId = super.getHelpId(mapping, form);
		BusinessDocument document = frm.getDocument();
		if (document instanceof RurPayment)
			helpId += HelpIdGenerator.getHelpIdSuffix((RurPayment) document);

		return helpId;
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

	/**
	 * создать форвард на следующую стадию работы с документом
	 * @param operation
	 * @return
	 */
	protected ActionForward createDefaultStageDocumentForward(EditDocumentOperation operation)
	{
		return new ActionForward(getCurrentMapping().findForward("Recall").getPath() + "?form=" + FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM.getName() + "&receiverType=" + getAutoSubscriptionReceiverType(operation), true);
	}

	/**
	 * создать форвард на стадию заполнени€ реквизитов создани€ копилки по исполненому переводу между своими счетами.
	 * @param payment - перевод между своими счетами.
	 * @return
	 * @throws DocumentException
	 */
	protected ActionForward createInitialMoneyBoxForward(InternalTransfer payment) throws DocumentException
	{
		String toResource = payment.getDestinationResourceLink().getCode();
		String fromResource = payment.getChargeOffResourceLink().getCode();
		String sellAmount = payment.getExactAmount().getDecimal().toString();
		return new ActionForward("/private/payments/payment.do?form=" + FormConstants.CREATE_MONEY_BOX_FORM +
				"&fromResource=" + fromResource + "&toResource=" + toResource + "&sellAmount=" + sellAmount, true);
	}

	/**
	 * ѕолучить наименование сервиса при создании подписки
	 * @return наименование сервиса
	 */
	protected String getAutoSubscriptionReceiverType(EditDocumentOperation operation)
	{
		FormType formType = operation.getDocument().getFormType();
		if (FormType.INTERNAL_TRANSFER.equals(formType))
		{
			return P2PAutoTransferClaimBase.SEVERAL_RECEIVER_TYPE_VALUE;
		}
		if (FormType.INDIVIDUAL_TRANSFER.equals(formType) || FormType.INDIVIDUAL_TRANSFER_NEW.equals(formType))
		{
			return RurPayment.PHIZ_RECEIVER_TYPE_VALUE;
		}
		throw new IllegalArgumentException("Ќеподдерживаемый тип платежа, FormType = " + formType);
	}
}
