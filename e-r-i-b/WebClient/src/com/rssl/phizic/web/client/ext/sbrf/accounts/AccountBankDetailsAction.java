package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.bank.BankDetailsConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.account.AccountBankDetailsOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.download.DownloadAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Печать реквизитов банка
 * @author Pankin
 * @ created 24.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AccountBankDetailsAction extends OperationalActionBase
{
	private static final String PDF_FILE_TYPE = "AccountDetailsPDF";
	private static final String RTF_FILE_TYPE = "AccountDetailsRTF";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.send", "send");
		map.put("button.unloadPDF", "unloadPDF");
		map.put("button.unloadRTF", "unloadRTF");
		return map;
	}

	protected Class getLinkClassType()
	{
		return AccountLink.class;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AccountBankDetailsOperation operation = createOperation(AccountBankDetailsOperation.class);

		AccountBankDetailsForm frm = (AccountBankDetailsForm) form;

		operation.initialize(frm.getId(), getLinkClassType());

		updateForm(operation, frm);

		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AccountBankDetailsForm frm = (AccountBankDetailsForm) form;

		AccountBankDetailsOperation operation = createOperation(AccountBankDetailsOperation.class);
		operation.initialize(frm.getId(), getLinkClassType());
		if (StringHelper.isEmpty((String) frm.getField("mailSubject")))
			frm.setField("mailSubject", operation.getDefaultMailSubject());

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new RequestValuesSource(request), AccountBankDetailsForm.SEND_MAIL_FORM);
		if(processor.process())
		{
			Map<String, Object> result = processor.getResult();
			String address = (String) result.get(AccountBankDetailsForm.MAIL_ADDRESS);
			String subject = operation.getDefaultMailSubject();
			String text = (String) result.get(AccountBankDetailsForm.MAIL_TEXT);

			String emailImageUrl = ConfigFactory.getConfig(BankDetailsConfig.class).getSendToEmailImageUrl();
			operation.sendMail(address, subject, text, emailImageUrl);
			frm.setEmailSended(true);
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}

		updateForm(operation, frm);
		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward unloadPDF (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AccountBankDetailsOperation operation = createOperation(AccountBankDetailsOperation.class);

		AccountBankDetailsForm frm = (AccountBankDetailsForm) form;

		operation.initialize(frm.getId(), getLinkClassType());

		DownloadAction.unload(operation.getPDF(), PDF_FILE_TYPE, "AccountDetails.pdf", frm, request);

		updateForm(operation, frm);
		frm.setField("fileType", PDF_FILE_TYPE);
		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward unloadRTF (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AccountBankDetailsOperation operation = createOperation(AccountBankDetailsOperation.class);

		AccountBankDetailsForm frm = (AccountBankDetailsForm) form;

		operation.initialize(frm.getId(), getLinkClassType());

		DownloadAction.unload(operation.getRTF(), RTF_FILE_TYPE, "AccountDetails.rtf", frm, request);

		updateForm(operation, frm);
		frm.setField("fileType", RTF_FILE_TYPE);
		return mapping.findForward(FORWARD_SHOW);
	}

	private void updateForm(AccountBankDetailsOperation operation, AccountBankDetailsForm frm) throws BusinessException, BusinessLogicException
	{
		frm.setAccountLink(operation.getAccount());
		frm.setCardLink(operation.getCardLink());
		frm.addFields(operation.getDetails());
		if (StringHelper.isEmpty((String) frm.getField("mailSubject")))
			frm.setField("mailSubject", operation.getDefaultMailSubject());
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		frm.setField("mailAddress", person.getEmail());
	}
}
