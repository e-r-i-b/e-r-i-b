package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import com.rssl.phizic.gate.einvoicing.ShopProfile;
import org.w3c.dom.Document;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class LinkOrderToClientRequestData extends InvoiceRequestDataBase
{
	private ShopProfile profile;

	public LinkOrderToClientRequestData(String orderUUID, ShopProfile profile)
	{
		super(orderUUID);
		this.profile = profile;
	}

	public String getName()
	{
		return "LinkOrderToClientRequest";
	}

	@Override
	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();
		fillOrderUUID(builder);
		addProfileElement(profile, builder);
		return builder.closeTag().toDocument();
	}
}
