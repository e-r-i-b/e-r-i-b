package com.rssl.phizic.test.web.mobile.payments;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 21.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileConfirmPaymentAction extends TestMobileDocumentAction
{
	private static final String FORWARD_START = "Start";
	private static final String CONFIRM_ADDRESS = "/private/payments/confirm.do";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestMobileConfirmPaymentForm frm = (TestMobileConfirmPaymentForm) form;
		frm.setAddress(CONFIRM_ADDRESS);
		send(frm);
		return mapping.findForward(FORWARD_START);
	}
}
