package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseBuilder;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizic.utils.xml.XmlHelper;

import static com.rssl.phizic.einvoicing.multiblock.processors.Constants.ORDER_INFO_TAG;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class GetOrderInfoRequestProcessor extends EInvoicingRequestProcessorBase
{
	protected String getResponceType()
	{
		return "GetOrderInfoResponce";
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		String orderUUID = getOrderUUID(requestInfo.getBody().getDocumentElement());
		ResponseBuilder builder = getSuccessResponseBuilder();
		builder.addParameter(ORDER_INFO_TAG, XmlHelper.parse(service.getOrderInfo(orderUUID)).getDocumentElement());
		return builder.end().getResponceInfo();
	}
}
