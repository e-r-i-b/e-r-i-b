package com.rssl.phizic.web.common.socialApi.payments.forms;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotActiveMobileBankTemplateException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import com.rssl.phizic.web.actions.payments.forms.EditDocumentForm;
import com.rssl.phizic.web.actions.payments.forms.EditPaymentAction;
import com.rssl.phizic.web.actions.payments.forms.EditPaymentForm;
import com.rssl.phizic.web.servlet.HttpServletEditableRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rssl.common.forms.FormConstants.SERVICE_PAYMENT_FORM;


/**
 * @author Erkin
 * @ created 26.10.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Второй и последующие шаги многошаговой оплаты
 */
public class EditSocialPaymentAction extends EditPaymentAction
{
	protected CreationType getNewDocumentCreationType()
	{
		return CreationType.social;
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		// для отмены автоплатежа
		map.put("save", "save");
		// для остальных платежей
		map.put("next", "save");
		map.put("init", "start");
		//создание автоплатежа
		map.put("makeLongOffer", "makeLongOffer");
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

	 protected void updateForm(CreatePaymentForm frm, EditDocumentOperation operation, FieldValuesSource valuesSource) throws BusinessException, BusinessLogicException
	{
		if (operation instanceof CreateFormPaymentOperation &&	frm instanceof EditPaymentForm)
		{
			EditPaymentForm form = (EditPaymentForm) frm;
			CreateFormPaymentOperation op = (CreateFormPaymentOperation) operation;
			form.setFromResource(op.getFromResourceLink());
		}
		super.updateForm(frm, operation, valuesSource);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditDocumentOperation operation = getOperation(request, false);
		MessageCollector messageCollector = new MessageCollector();
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
		final String formName = frm.getForm();
		if (FormConstants.BLOCKING_CARD_CLAIM.equals(formName))
			setBlockingCardClaimFields();
		else if (FormConstants.CREATE_AUTOPAYMENT_FORM.equals(formName))
			setFirstPaymentDate();
        else if (FormConstants.IMA_OPENING_CLAIM.equals(formName))
			setImaOpeningClaimFields();
		else if (FormConstants.EDIT_AUTOPAYMENT_FORM.equals(formName))
			setSellAmountFields();
        return super.save(mapping, form, request, response);
	}

	//todo CHG059734 удалить
	protected EditDocumentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		EditSocialPaymentForm frm = (EditSocialPaymentForm) form;
		Long smsTemplateId = frm.getSMStemplate();
		if (smsTemplateId != null && smsTemplateId > 0)
			return createEditSocialBankPaymentOperation(smsTemplateId, frm.getAmount());

		return super.createEditOperation(request, form, messageCollector);
	}

	@Deprecated
	//todo CHG059738 удалить
	private EditDocumentOperation createEditSocialBankPaymentOperation(long templateId, double amount) throws BusinessException, BusinessLogicException
	{
		EditServicePaymentOperation operation = createOperation(EditServicePaymentOperation.class, SERVICE_PAYMENT_FORM);
		operation.initializeMobileBankTemplatePayment(templateId, amount, getNewDocumentCreationType());
		return operation;
	}

	protected String buildFormHtml(EditDocumentOperation operation, FieldValuesSource fieldValuesSource, ActionForm form) throws BusinessException
	{
		return operation.buildSocialXml(fieldValuesSource);
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

	private void checkInputParametrsAccountOpening(HttpServletRequest request) throws BusinessException
	{
		String depositId = request.getParameter("depositId");
		String depositType = request.getParameter("depositType");

		if (StringHelper.isEmpty(depositId))
			throw new BusinessException("Не задан входной параметр id депозита");
		if (StringHelper.isEmpty(depositType))
			throw new BusinessException("Не задан входной параметр тип депозита");
	}

    private void setImaOpeningClaimFields() throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		HttpServletEditableRequest req = new HttpServletEditableRequest (request);

		String fromResource = request.getParameter("fromResource");
		if(StringHelper.isNotEmpty(fromResource))
		{
			req.putParameter("fromResourceCode", fromResource); //сохраняем код в поле string
			WebContext.setCurrentRequest(req);
		}
	}
}
