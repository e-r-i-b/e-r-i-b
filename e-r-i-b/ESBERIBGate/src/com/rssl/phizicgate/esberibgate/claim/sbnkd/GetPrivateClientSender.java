package com.rssl.phizicgate.esberibgate.claim.sbnkd;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.sbnkd.ClientInfoDocument;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateClientRq;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.AbstractJMSClaimSender;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders.GetPrivateClientRequestBuilder;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders.RequestBuilder;

/**
 * Отправка сообщения о получении информаии по клиенту.
 *
 * @author bogdanov
 * @ created 19.12.14
 * @ $Author$
 * @ $Revision$
 */

public class GetPrivateClientSender extends AbstractJMSClaimSender<ClientInfoDocument, GetPrivateClientRq>
{
	public GetPrivateClientSender(GateFactory factory)
	{
		super(factory, GetPrivateClientRq.class);
	}

	@Override
	protected RequestBuilder<GetPrivateClientRq, ClientInfoDocument> getRequestBuilder(GateFactory factory)
	{
		return new GetPrivateClientRequestBuilder(factory);
	}

	@Override
	protected void setUUID(ClientInfoDocument document, String uuid)
	{
		document.setUID(uuid);
	}

	@Override
	protected String getUUID(ClientInfoDocument document)
	{
		return document.getUID();
	}
}
