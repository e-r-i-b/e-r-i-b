package com.rssl.phizic.test.web.mobile.payments;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Ёкшн создани€ подписки из подтвержденного документа
 *
 * @ author: Gololobov
 * @ created: 15.10.14
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileInvoiceSubscriptionPaymentAction extends TestMobilePaymentActionBase
{
	private static final String EDIT_AND_VIEW_SUBSCRIPTION_STEP = "EditAndViewSubscription";
	private static final String CONFIRM_SUBSCRIPTION_STEP = "ConfirmSubscription";

	protected ActionForward submitInit(ActionMapping mapping, TestMobileDocumentForm form)
	{
		if (send(form) == 0)
			return mapping.findForward(EDIT_AND_VIEW_SUBSCRIPTION_STEP);

		if (form.getResponse() != null)
			return mapping.findForward(EDIT_AND_VIEW_SUBSCRIPTION_STEP);
		return mapping.findForward(FORWARD_INIT);
	}

	protected ActionForward submitSave(ActionMapping mapping, TestMobileDocumentForm form)
	{
		if (send(form) == 0)
			return mapping.findForward(CONFIRM_SUBSCRIPTION_STEP);
		return mapping.findForward(EDIT_AND_VIEW_SUBSCRIPTION_STEP);
	}
}
