package com.rssl.phizic.web.common.client.ext.sbrf.payment;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.web.actions.payments.forms.ConfirmDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;
import com.rssl.phizic.web.help.HelpIdGenerator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gladishev
 * @ created 24.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmClientDocumentAction extends ConfirmDocumentAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    return autoConfirm(mapping, form, request, response);
    }

	@Override
	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
		String helpId = super.getHelpId(mapping, form);
		BusinessDocument document = frm.getDocument();
		if (document instanceof RurPayment)
			helpId += HelpIdGenerator.getHelpIdSuffix((RurPayment) document);

		return helpId;
	}
}
