package com.rssl.phizicgate.esberibgate.claim.sbnkd;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.sbnkd.CardInfo;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.IssueCardRq;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.AbstractJMSClaimSender;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders.IssueCardClaimRequestBuilder;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders.RequestBuilder;

/**
 * Сендер: заявка на выпуск карты.
 *
 * @author bogdanov
 * @ created 19.12.14
 * @ $Author$
 * @ $Revision$
 */

public class IssueCardDocumentSender extends AbstractJMSClaimSender<CardInfo, IssueCardRq>
{
	public IssueCardDocumentSender(GateFactory factory)
	{
		super(factory, IssueCardRq.class);
	}

	@Override
	protected RequestBuilder<IssueCardRq, CardInfo> getRequestBuilder(GateFactory factory)
	{
		return new IssueCardClaimRequestBuilder(factory);
	}

	@Override
	protected void setUUID(CardInfo document, String uuid)
	{
	}

	@Override
	protected String getUUID(CardInfo document)
	{
		return document.getUID();
	}
}
