package com.rssl.phizic.web.common.client.payments.internetShops;

import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.operations.payment.internetShops.CancelOrderOperation;
import com.rssl.phizic.business.basket.InvoiceMessage;
import com.rssl.phizic.web.tags.InvoiceMessageTag;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * экшн отмены интернет заказа или брони авиабилетов.
 *
 * @author bogdanov
 * @ created 18.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class CancelInternetOrderAction extends OperationalActionBase
{
	private static final String TO_INVOICES_LIST_FORWARD_NAME = "ToInvoicesList";


	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CancelInternetOrderForm frm = (CancelInternetOrderForm) form;
		CancelOrderOperation operation = createOperation(CancelOrderOperation.class, "InternetOrderPayments");
		if (!StringHelper.isEmpty(frm.getOrderId()))
			operation.initialize(frm.getOrderId(), false);
		else
			operation.initialize(frm.getId(), false);
		operation.doCancel();

		InvoiceMessage.saveMessage(operation.getOrder(), InvoiceMessage.Type.delete);

		if (StringHelper.isNotEmpty(frm.getPageType()) && frm.getPageType().equals("list"))
			return mapping.findForward(TO_INVOICES_LIST_FORWARD_NAME);
		else
 		    return mapping.findForward(FORWARD_START);
	}
}
