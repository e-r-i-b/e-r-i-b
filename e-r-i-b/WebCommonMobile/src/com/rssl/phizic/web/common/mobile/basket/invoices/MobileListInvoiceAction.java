package com.rssl.phizic.web.common.mobile.basket.invoices;

import com.rssl.phizic.operations.payment.ListInvoicesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Список счетов в корзине
 * @ author: Gololobov
 * @ created: 24.10.14
 * @ $Author$
 * @ $Revision$
 */
public class MobileListInvoiceAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListInvoicesOperation operation = createOperation("ListInvoicesOperation", "PaymentBasketManagment");
		Set<String> errorCollector = new HashSet<String>();
		MobileListInvoiceForm frm = (MobileListInvoiceForm) form;
		operation.initializeMobile(errorCollector, frm.getShowInvoicesCount());
		if(!errorCollector.isEmpty())
		{
			ActionMessages msgs = new ActionMessages();
			for(String errorText: errorCollector)
		        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorText, false));
			saveMessages(request, msgs);
		}
		frm.setData(operation.getData());

		return mapping.findForward(FORWARD_START);
	}

}
