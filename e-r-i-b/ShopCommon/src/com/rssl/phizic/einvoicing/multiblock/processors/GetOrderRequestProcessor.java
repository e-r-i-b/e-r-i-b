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
public class GetOrderRequestProcessor extends EInvoicingRequestProcessorBase
{
	protected String getResponceType()
	{
		return "GetOrderResponce";
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		String orderUUID = getOrderUUID(requestInfo.getBody().getDocumentElement());
		ShopOrder order = getOrder(orderUUID);
		if (order == null)
			throw new OrderNotFoundException();

		ResponseBuilder builder = getSuccessResponseBuilder();
		ResponseBuilderHelper.addOrder(order, builder);
		return builder.end().getResponceInfo();
	}

	protected ShopOrder getOrder(String orderIdentificator) throws Exception
	{
		return service.getOrder(orderIdentificator);
	}
}
