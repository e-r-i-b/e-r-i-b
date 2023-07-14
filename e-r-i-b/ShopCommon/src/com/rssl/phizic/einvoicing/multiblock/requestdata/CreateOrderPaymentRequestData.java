package com.rssl.phizic.einvoicing.multiblock.requestdata;

import com.rssl.phizgate.messaging.internalws.client.RequestDataBase;
import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import com.rssl.phizic.einvoicing.multiblock.processors.ResponseBuilderHelper;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import org.w3c.dom.Document;

/**
 * @author gladishev
 * @ created 28.02.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateOrderPaymentRequestData extends RequestDataBase
{
	private ShopOrder order;

	public CreateOrderPaymentRequestData(ShopOrder order)
	{
		this.order = order;
	}

	public String getName()
	{
		return "CreateOrderPaymentRequest";
	}

	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();
		ResponseBuilderHelper.addOrder(order, builder);
		return builder.closeTag().toDocument();
	}
}
