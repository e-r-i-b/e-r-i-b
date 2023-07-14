package com.rssl.phizic.test.web.atm.payments;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author vagin
 * @ created 19.01.15
 * @ $Author$
 * @ $Revision$
 * Тестовый экшен редактирования копилки.
 */
public class TestATMEditMoneyBoxAction extends TestATMPaymentActionBase
{
	private static final String PAYMENT_URL = "/private/moneyboxes/edit.do";
	private static final String FORWARD_CONFIRMED_STEP = "СonfirmedStep";

	protected ActionForward submitInit(ActionMapping mapping, TestATMDocumentForm form)
	{
		if (send(form) == 0)
		{
			String lastUrl = form.getLastUrl().toExternalForm();
			if (lastUrl.contains(PAYMENT_URL))
			{
				return mapping.findForward(FORWARD_FIRST_STEP);
			}
		}
		return mapping.findForward(FORWARD_INIT);
	}

	protected ActionForward submitSave(ActionMapping mapping, TestATMDocumentForm form)
	{
		if(send(form) == 0)
		{
			return mapping.findForward(FORWARD_CONFIRMED_STEP);
		}
		return mapping.findForward(FORWARD_FIRST_STEP);
	}
}
