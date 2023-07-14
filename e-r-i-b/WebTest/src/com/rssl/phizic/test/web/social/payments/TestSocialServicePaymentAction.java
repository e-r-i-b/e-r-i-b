package com.rssl.phizic.test.web.social.payments;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jatsky
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 */

public class TestSocialServicePaymentAction extends TestSocialPaymentActionBase
{
	protected ActionForward submitSave(ActionMapping mapping, TestSocialServicePaymentForm form)
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