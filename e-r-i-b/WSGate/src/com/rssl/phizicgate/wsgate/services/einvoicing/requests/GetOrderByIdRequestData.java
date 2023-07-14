package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class GetOrderByIdRequestData extends InvoiceRequestDataBase
{
	public GetOrderByIdRequestData(Long orderId)
	{
		super(orderId.toString());
	}

	public String getName()
	{
		return "GetOrderByIdRequest";
	}
}
