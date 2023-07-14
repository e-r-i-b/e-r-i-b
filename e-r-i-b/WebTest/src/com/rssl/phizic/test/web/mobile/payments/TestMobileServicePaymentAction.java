package com.rssl.phizic.test.web.mobile.payments;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Оплата услуг
 * @author Erkin
 * @ created 02.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileServicePaymentAction extends TestMobilePaymentActionBase
{
	protected ActionForward submitSave(ActionMapping mapping, TestMobileDocumentForm form)
	{
		switch (send(form))
		{
			case 0:
			case 1:
				if (form.getDocument() != null)
					return mapping.findForward(FORWARD_SECOND_STEP);
				break;
		}
		return mapping.findForward(FORWARD_FIRST_STEP);
	}
}
