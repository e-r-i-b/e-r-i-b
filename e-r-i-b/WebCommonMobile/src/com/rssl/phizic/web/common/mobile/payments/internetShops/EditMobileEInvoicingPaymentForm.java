package com.rssl.phizic.web.common.mobile.payments.internetShops;

import com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm;

/**
 * @author Dorzhinov
 * @ created 18.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditMobileEInvoicingPaymentForm extends EditServicePaymentForm
{
	private String orderUuid; //uuid заказа

	public String getOrderUuid()
	{
		return orderUuid;
	}

	public void setOrderUuid(String orderUuid)
	{
		this.orderUuid = orderUuid;
	}

	public String getOrderId()
	{
		return orderUuid;
	}
}
