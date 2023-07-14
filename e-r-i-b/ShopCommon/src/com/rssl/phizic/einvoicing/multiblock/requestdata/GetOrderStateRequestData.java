package com.rssl.phizic.einvoicing.multiblock.requestdata;

import com.rssl.phizgate.messaging.internalws.client.RequestDataBase;
import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import com.rssl.phizic.einvoicing.multiblock.processors.Constants;
import org.w3c.dom.Document;

/**
 * @author gladishev
 * @ created 12.03.2014
 * @ $Author$
 * @ $Revision$
 */

public class GetOrderStateRequestData extends RequestDataBase
{
	private String orderUuid;

	public GetOrderStateRequestData(String orderUuid)
	{
		this.orderUuid = orderUuid;
	}

	public String getName()
	{
		return "GetOrderStateRequest";
	}

	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();
		builder.addParameter(Constants.UUID_TAG, orderUuid);
		return builder.closeTag().toDocument();
	}
}
