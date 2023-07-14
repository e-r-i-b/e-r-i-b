package com.rssl.phizic.test.web.mobile.payments;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Balovtsev
 * @since 28.10.14.
 */
public class TestMobileCreateInvoiceSubscriptionClaimAction extends TestMobileDocumentAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestMobileConfirmPaymentForm frm = (TestMobileConfirmPaymentForm) form;

		if (StringHelper.isNotEmpty(frm.getOperation()))
		{
			frm.setEditSupported(false);
			frm.setAddress("/private/basket/subscriptions/claim.do");
			send(frm);
		}

		return mapping.findForward("Start");
	}
}
