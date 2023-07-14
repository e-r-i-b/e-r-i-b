package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseBuilder;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizic.einvoicing.exceptions.OrderNotFoundException;
import com.rssl.phizic.gate.einvoicing.ShopOrder;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class GetOrderByIdRequestProcessor extends GetOrderRequestProcessor
{
	protected String getResponceType()
	{
		return "GetOrderByIdResponce";
	}

	@Override
	protected ShopOrder getOrder(String orderIdentificator) throws Exception
	{
		return service.getOrder(Long.parseLong(orderIdentificator));
	}
}
