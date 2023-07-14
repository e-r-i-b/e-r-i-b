package com.rssl.phizic.web.common.mobile.accounts;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.bank.BankDetailsConfig;
import com.rssl.phizic.operations.account.AccountBankDetailsOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author EgorovaA
 * @ created 28.11.13
 * @ $Author$
 * @ $Revision$
 */
public class SendAccountBankDetailsAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SendAccountBankDetailsForm frm = (SendAccountBankDetailsForm) form;

		AccountBankDetailsOperation operation = createOperation(AccountBankDetailsOperation.class);
		operation.initialize(frm.getId(), getLinkClassType());

		Form editForm = SendAccountBankDetailsForm.SEND_MAIL_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new RequestValuesSource(request), editForm);
		if(processor.process())
		{
			Map<String, Object> result = processor.getResult();
			String address = (String) result.get("address");
			String subject = (String) result.get("subject");
			if (StringHelper.isEmpty(subject))
				subject = operation.getDefaultMailSubject();
			String text = (String) result.get("comment");

			String emailImageUrl = ConfigFactory.getConfig(BankDetailsConfig.class).getSendToEmailImageUrl();
			operation.sendMail(address, subject, text, emailImageUrl);
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}

		return mapping.findForward(FORWARD_SHOW);
	}

	protected Class getLinkClassType()
	{
		return AccountLink.class;
	}
}
