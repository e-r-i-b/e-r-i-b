package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseBuilder;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author gladishev
 * @ created 09.03.14
 * @ $Author$
 * @ $Revision$
 */
public class GetTicketInfoRequestProcessor extends EInvoicingRequestProcessorBase
{
	@Override
	protected String getResponceType()
	{
		return "GetTicketInfoResponce";
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		String orderUUID = getOrderUUID(requestInfo.getBody().getDocumentElement());

		ResponseBuilder successResponseBuilder = getSuccessResponseBuilder();
		String ticketsInfo = service.getTicketInfo(orderUUID);
		if (StringHelper.isNotEmpty(ticketsInfo))
			successResponseBuilder.addParameter(com.rssl.phizgate.einvoicing.Constants.TICKETS_INFO_TAG, ticketsInfo);

		return successResponseBuilder.end().getResponceInfo();
	}
}
