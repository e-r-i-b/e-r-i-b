package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import org.w3c.dom.Document;

/**
 * @author vagin
 * @ created 19.08.14
 * @ $Author$
 * @ $Revision$
 * «апрос изменени€ статуса просмотренности.
 */
public class MarkViewedRequestData extends InvoiceRequestDataBase
{
	public MarkViewedRequestData(String orderUUID)
	{
		super(orderUUID);
	}

	public String getName()
	{
		return "MarkViewedRequest";
	}

	@Override
	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();
		fillOrderUUID(builder);
		return builder.closeTag().toDocument();
	}
}
