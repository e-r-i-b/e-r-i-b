package com.rssl.phizic.web.atm.payments.forms;

import com.rssl.common.forms.FormConstants;
import static com.rssl.common.forms.FormConstants.SERVICE_PAYMENT_FORM;

import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.PrepareLongOfferValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotActiveMobileBankTemplateException;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.metadata.source.ReceiverFieldValueSource;
import com.rssl.phizic.business.documents.metadata.source.ResourceLinkFieldValueSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.payments.forms.*;
import com.rssl.phizic.web.servlet.HttpServletEditableRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Erkin
 * @ created 26.10.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Второй и последующие шаги многошаговой оплаты
 */
public class EditPaymentATMAction extends EditPaymentAction
{
	protected CreationType getNewDocumentCreationType()
	{
		return CreationType.atm;
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		// для отмены автоплатежа
		map.put("save", "save");
		// для ввода СНИЛС в заявке на получение выписки из ПФР
		map.put("edit", "start");
		// для остальных платежей
		map.put("next", "save");
		map.put("init", "start");
		//создание автоплатежа
		map.put("makeLongOffer", "makeLongOffer");
		map.put("makeAutoTransfer", "makeAutoTransfer");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
        EditDocumentForm frm = (EditDocumentForm) form;
		if (FormConstants.ACCOUNT_OPENING_CLAIM_FORM.equals(frm.getForm()))
			checkInputParametrsAccountOpening(request);

		return super.start(mapping, frm, request, response);
	}


	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MessageCollector messageCollector = new MessageCollector();
		EditDocumentOperation operation = getOperation(request, false);
		// Операции не было => создаём и запоминаем
		if (operation == null) {
            try {
                //noinspection ReuseOfLocalVariable
                operation = createEditOperation(request, (EditPaymentForm) form, messageCollector);
            } catch (NotActiveMobileBankTemplateException e) {
                saveError(request, e);
				return mapping.findForward(FORWARD_SHOW_FORM);
            }
			saveOperation(request, operation);
		}

		EditDocumentForm frm = (EditDocumentForm) form;
		String formName = frm.getForm();
		if (FormConstants.BLOCKING_CARD_CLAIM.equals(formName))
			setBlockingCardClaimFields();
		else if (FormConstants.CREATE_AUTOPAYMENT_FORM.equals(formName))
			setFirstPaymentDate();
		else if (FormConstants.EDIT_AUTOPAYMENT_FORM.equals(formName))
			setSellAmountFields();

        return super.save(mapping, form, request, response);
	}

	protected EditDocumentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		EditPaymentATMForm frm = (EditPaymentATMForm) form;
		Long smsTemplateId = frm.getSMStemplate();
		if (smsTemplateId != null && smsTemplateId > 0)
			return createEditMobileBankPaymentOperation(smsTemplateId, frm.getAmount());

		return super.createEditOperation(request, form, messageCollector);
	}

	private EditDocumentOperation createEditMobileBankPaymentOperation(long templateId, double amount) throws BusinessException, BusinessLogicException
	{
		EditServicePaymentOperation operation = createOperation(EditServicePaymentOperation.class, SERVICE_PAYMENT_FORM);
		operation.initializeMobileBankTemplatePayment(templateId, amount, getNewDocumentCreationType());
		return operation;
	}

	protected String buildFormHtml(EditDocumentOperation operation, FieldValuesSource fieldValuesSource, ActionForm form) throws BusinessException
	{
		return operation.buildATMXml(fieldValuesSource);
	}

	protected void updateForm(CreatePaymentForm frm, EditDocumentOperation operation, FieldValuesSource valuesSource) throws BusinessException, BusinessLogicException
	{
		if (operation instanceof CreateFormPaymentOperation && frm instanceof EditPaymentForm)
		{
			EditPaymentForm form = (EditPaymentForm) frm;
			CreateFormPaymentOperation op = (CreateFormPaymentOperation) operation;
			form.setFromResource(op.getFromResourceLink());
		}
		super.updateForm(frm, operation, valuesSource);
	}

	private void setBlockingCardClaimFields() throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		HttpServletEditableRequest req = new HttpServletEditableRequest (request);

		String card = request.getParameter("card");
		if(!StringHelper.isEmpty(card))
		{
			String cardId = card.split(":")[1];
			CardLink cardLink = PersonContext.getPersonDataProvider().getPersonData().getCard(Long.valueOf(cardId));
			req.putParameter("account", cardLink.getCardPrimaryAccount());
			req.putParameter("cardLink", card);
			WebContext.setCurrentRequest(req);
		}
	}

	private void setFirstPaymentDate() throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		HttpServletEditableRequest req = new HttpServletEditableRequest (request);

		String firstPaymentDate = request.getParameter("firstPaymentDate");
		if (StringHelper.isEmpty(firstPaymentDate))
		{
			req.putParameter("firstPaymentDate", request.getParameter("autoPaymentStartDate"));
			WebContext.setCurrentRequest(req);
		}
	}

	private void setSellAmountFields() throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		HttpServletEditableRequest req = new HttpServletEditableRequest (request);

		String card = request.getParameter("fromResource");
		if(!StringHelper.isEmpty(card))
		{
			String cardId = card.split(":")[1];
			CardLink cardLink = PersonContext.getPersonDataProvider().getPersonData().getCard(Long.valueOf(cardId));
			req.putParameter("cardNumber", cardLink.getCard().getNumber());
			WebContext.setCurrentRequest(req);
		}
	}

	private void checkInputParametrsAccountOpening(HttpServletRequest request) throws BusinessException
	{
		String depositId = request.getParameter("depositId");
		String depositType = request.getParameter("depositType");

		if (StringHelper.isEmpty(depositId))
			throw new BusinessException("Не задан входной параметр id депозита");
		if (StringHelper.isEmpty(depositType))
			throw new BusinessException("Не задан входной параметр тип депозита");
	}

	public ActionForward makeAutoTransfer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreatePaymentForm frm = (CreatePaymentForm) form;
		MessageCollector messageCollector = new MessageCollector();
		EditDocumentOperation operation = createOperation(CreateFormPaymentOperation.class, FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM.getName());

		DocumentSource existingDocumentSource = createExistingDocumentSource(frm.getId());
		FieldValuesSource existingValuesSource = getFieldValueSource(existingDocumentSource.getDocument());

		try
		{
			FormProcessor<ActionMessages, ?> processor = createFormProcessor(existingValuesSource, MetadataCache.getExtendedMetadata(FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM.getName(), existingValuesSource).getForm(), PrepareLongOfferValidationStrategy.getInstance());
			if (processor.process())
			{
				operation.initialize(new NewDocumentSource(FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM.getName(), existingValuesSource, getNewDocumentCreationType(), CreationSourceType.ordinary, messageCollector));
				operation.makeLongOffer(null);

				return createNextStageDocumentForward(operation);
			}
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}
		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	private FieldValuesSource getFieldValueSource(BusinessDocument document) throws BusinessException
	{
		return new CompositeFieldValuesSource(new ResourceLinkFieldValueSource(document), new ReceiverFieldValueSource(document), new DocumentFieldValuesSource(MetadataCache.getBasicMetadata(document.getFormName()), document));
	}
}
