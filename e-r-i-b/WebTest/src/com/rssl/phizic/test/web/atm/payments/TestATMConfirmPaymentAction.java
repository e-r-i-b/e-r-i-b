package com.rssl.phizic.test.web.atm.payments;

import com.rssl.phizic.business.test.atm.generated.ResponseElement;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 21.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestATMConfirmPaymentAction extends TestATMDocumentAction
{
	private static final String FORWARD_START = "Start";
	private static final String CONFIRM_ADDRESS = "/private/payments/confirm.do";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestATMConfirmPaymentForm frm = (TestATMConfirmPaymentForm) form;
		frm.setAddress(CONFIRM_ADDRESS);
		switch (send(frm))
		{
			case 0:
				return mapping.findForward(FORWARD_START);
		}

		if ((frm.getResponse() != null) && ((ResponseElement) frm.getResponse()).getInitialData() != null && ((ResponseElement) frm.getResponse()).getInitialData().getForm().equals("PFRStatementClaim"))
		{
			frm.setAddress("/private/payments/payment.do");
			ActionRedirect redirect = new ActionRedirect("/atm/PFRStatementClaim.do");
			redirect.addParameter("id", frm.getId());
			redirect.addParameter("operation", "edit");
			redirect.addParameter("cookie", frm.getCookie());
			redirect.addParameter("url", frm.getUrl());
			redirect.addParameter("address", frm.getAddress());
			redirect.addParameter("transactionToken", frm.getTransactionToken());
			return redirect;
		}

		return mapping.findForward(FORWARD_START);
	}
}