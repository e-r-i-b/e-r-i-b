package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import org.w3c.dom.Document;

import static com.rssl.phizic.business.einvoicing.Constants.*;

/**
 * @author bogdanov
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */

public class GetEndPointProviderRequestData extends InvoiceRequestDataBase
{
	private final long providerId;

	public GetEndPointProviderRequestData(long providerId) {
		super(null);
		this.providerId = providerId;
	}

	public String getName()
	{
		return "GetEndPointProviderRequest";
	}

	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();

		builder.addParameter(ID_TAG, providerId);

		return builder.closeTag().toDocument();
	}
}
