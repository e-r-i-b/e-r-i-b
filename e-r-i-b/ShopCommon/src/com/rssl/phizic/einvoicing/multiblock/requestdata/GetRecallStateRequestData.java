package com.rssl.phizic.einvoicing.multiblock.requestdata;

import com.rssl.phizgate.messaging.internalws.client.RequestDataBase;
import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import com.rssl.phizic.einvoicing.multiblock.processors.Constants;
import com.rssl.phizic.gate.einvoicing.ShopRecall;
import org.w3c.dom.Document;
/**
 * @author gladishev
 * @ created 12.03.2014
 * @ $Author$
 * @ $Revision$
 */

public class GetRecallStateRequestData extends RequestDataBase
{
	private ShopRecall recall;

	public GetRecallStateRequestData(ShopRecall recall)
	{
		this.recall = recall;
	}

	public String getName()
	{
		return "GetRecallStateRequest";
	}

	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();
		builder.addParameter(Constants.UUID_TAG, recall.getOrderUuid());
		builder.addParameter(Constants.RECALL_UUID_TAG, recall.getUuid());
		builder.addParameter(Constants.RECALL_TYPE_TAG, recall.getType());
		return builder.closeTag().toDocument();
	}
}
