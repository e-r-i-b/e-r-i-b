package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizic.einvoicing.exceptions.OrderNotFoundException;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import org.w3c.dom.Element;

/**
 * @author vagin
 * @ created 19.08.14
 * @ $Author$
 * @ $Revision$
 */
public class MarkViewedRequestProcessor extends EInvoicingRequestProcessorBase
{
	protected String getResponceType()
	{
		return "MarkViewedResponce";
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element documentElement = requestInfo.getBody().getDocumentElement();
		String orderUUID = getOrderUUID(documentElement);
		ShopOrder order = service.getOrder(orderUUID);
		if (order == null)
			throw new OrderNotFoundException();

		service.markViewed(orderUUID);
		return getSuccessResponseBuilder().end().getResponceInfo();
	}
}
