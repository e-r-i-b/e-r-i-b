package com.rssl.phizic.test.web.social.payments;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jatsky
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 */

public class TestSocialConfirmPaymentAction extends TestSocialDocumentAction
{
	private static final String FORWARD_START = "Start";
	private static final String CONFIRM_ADDRESS = "/private/payments/confirm.do";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestSocialConfirmPaymentForm frm = (TestSocialConfirmPaymentForm) form;
		frm.setAddress(CONFIRM_ADDRESS);
		send(frm);
		return mapping.findForward(FORWARD_START);
	}
}
