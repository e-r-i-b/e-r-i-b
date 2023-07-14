package com.rssl.phizic.test.web.atm.payments;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Erkin
 * @ created 02.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestATMServicePaymentAction extends TestATMPaymentActionBase
{
	protected ActionForward submitSave(ActionMapping mapping, TestATMDocumentForm form)
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