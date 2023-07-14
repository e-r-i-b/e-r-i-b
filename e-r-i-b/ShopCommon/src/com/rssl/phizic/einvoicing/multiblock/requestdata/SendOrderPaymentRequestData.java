package com.rssl.phizic.einvoicing.multiblock.requestdata;

import com.rssl.phizic.gate.einvoicing.ShopOrder;

/**
 * @author gladishev
 * @ created 28.02.14
 * @ $Author$
 * @ $Revision$
 */
public class SendOrderPaymentRequestData extends CreateOrderPaymentRequestData
{
	public SendOrderPaymentRequestData(ShopOrder order)
	{
		super(order);
	}

	public String getName()
	{
		return "SendOrderPaymentRequest";
	}
}
