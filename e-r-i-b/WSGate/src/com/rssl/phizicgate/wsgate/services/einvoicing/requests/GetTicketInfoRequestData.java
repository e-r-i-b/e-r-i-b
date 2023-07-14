package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import org.w3c.dom.Document;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class GetTicketInfoRequestData extends InvoiceRequestDataBase
{
	public GetTicketInfoRequestData(String orderUUID)
	{
		super(orderUUID);
	}

	public String getName()
	{
		return "GetTicketInfoRequest";
	}

	@Override
	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();
		fillOrderUUID(builder);
		return builder.closeTag().toDocument();
	}
}
