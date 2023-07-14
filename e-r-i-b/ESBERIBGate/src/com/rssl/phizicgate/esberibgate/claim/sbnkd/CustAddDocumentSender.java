package com.rssl.phizicgate.esberibgate.claim.sbnkd;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.sbnkd.CustAddDocument;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.CustAddRq;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.AbstractJMSClaimSender;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders.CustAddRequestBuilder;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders.RequestBuilder;

/**
 * Отправка сообщения: заключение УДБО.
 *
 * @author bogdanov
 * @ created 19.12.14
 * @ $Author$
 * @ $Revision$
 */

public class CustAddDocumentSender extends AbstractJMSClaimSender<CustAddDocument, CustAddRq>
{
	public CustAddDocumentSender(GateFactory factory)
	{
		super(factory, CustAddRq.class);
	}

	@Override
	protected RequestBuilder<CustAddRq, CustAddDocument> getRequestBuilder(GateFactory factory)
	{
		return new CustAddRequestBuilder(factory);
	}

	@Override
	protected void setUUID(CustAddDocument document, String uuid)
	{
		document.setUID(uuid);
	}

	@Override
	protected String getUUID(CustAddDocument document)
	{
		return document.getUID();
	}
}
