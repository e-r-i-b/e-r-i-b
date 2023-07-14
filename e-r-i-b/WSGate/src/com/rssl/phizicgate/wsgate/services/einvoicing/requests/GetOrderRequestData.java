package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class GetOrderRequestData extends InvoiceRequestDataBase
{
	public GetOrderRequestData(String orderUUID)
	{
		super(orderUUID);
	}

	public String getName()
	{
		return "GetOrderRequest";
	}
}
