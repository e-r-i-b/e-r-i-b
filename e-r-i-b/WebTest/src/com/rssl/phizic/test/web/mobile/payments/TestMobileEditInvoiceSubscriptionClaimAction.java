package com.rssl.phizic.test.web.mobile.payments;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author saharnova
 * @ created 10.07.15
 * @ $Author$
 * @ $Revision$
 */

public class TestMobileEditInvoiceSubscriptionClaimAction extends TestMobilePaymentActionBase
{
	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestMobileDocumentForm form = (TestMobileDocumentForm) frm;
		String operation = form.getOperation();

		if (StringHelper.isEmpty(operation))
			return mapping.findForward(FORWARD_INIT);

		if ("init".equals(operation))
			return submitInit(mapping, form);

		if ("save".equals(operation))
			return submitSave(mapping, form);

		return null;
	}

	protected ActionForward submitSave(ActionMapping mapping, TestMobileDocumentForm form)
	{
		if (send(form) == 0)
			return mapping.findForward(FORWARD_SECOND_STEP);
		return mapping.findForward(FORWARD_FIRST_STEP);
	}
}
