package com.rssl.phizic.web.common.client.offert;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.loans.offert.OfferDetailsOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.download.DownloadAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Moshenko
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */
public class CreditOffertConditionsAction extends OperationalActionBase
{
	private static final String PDF_FILE_TYPE = "OfferDetailsPDF";
	private static final String MAIL_SUBJECT = "Индивидуальные условия кредита";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.send", "send");
		map.put("button.unloadPDF", "unloadPDF");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreditOffertConditionsForm frm = (CreditOffertConditionsForm) form;

		OfferDetailsOperation operation = createOperation("OfferDetailsOperation","ExtendedLoanClaim");
		operation.initialize(frm.getAppNum(), frm.getOfferId());

		updateForm(operation, frm);
		saveOperation(request,operation);

		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Выгрузка текста оферты в PDF
	 * @throws Exception
	 */
	public ActionForward unloadPDF (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OfferDetailsOperation operation = getOperation(request);
		CreditOffertConditionsForm frm = (CreditOffertConditionsForm) form;

		DownloadAction.unload(operation.getPDF(), PDF_FILE_TYPE, "OfferDetails.pdf", frm, request);
		frm.setField("fileType", PDF_FILE_TYPE);
		updateForm(operation, frm);

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreditOffertConditionsForm frm = (CreditOffertConditionsForm) form;

		OfferDetailsOperation operation = getOperation(request);

		if (StringHelper.isEmpty((String) frm.getField("mailSubject")))
			frm.setField("mailSubject", MAIL_SUBJECT);

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new RequestValuesSource(request), CreditOffertConditionsForm.SEND_MAIL_FORM);
		if(processor.process())
		{
			Map<String, Object> result = processor.getResult();
			String address = (String) result.get(CreditOffertConditionsForm.MAIL_ADDRESS);
			String subject = (String) result.get(CreditOffertConditionsForm.MAIL_SUBJECT);
			String text = (String) result.get(CreditOffertConditionsForm.MAIL_TEXT);

			operation.sendMail(address, subject, text);
			frm.setEmailSended(true);
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}

		updateForm(operation, frm);
		return mapping.findForward(FORWARD_START);
	}

	private void updateForm(OfferDetailsOperation operation, CreditOffertConditionsForm frm) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty((String) frm.getField("mailSubject")))
			frm.setField("mailSubject", MAIL_SUBJECT);
		frm.setOfferText(operation.getOfferText());
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		frm.setField("mailAddress", person.getEmail());
		frm.setDepartment(operation.getClaimDrawDepartment());
        frm.setFormatedConfirmOfferDate(operation.getFormatedConfirmOfferDate());
        frm.setClaimETSMId(operation.getClaimETSMId());
	}
}
