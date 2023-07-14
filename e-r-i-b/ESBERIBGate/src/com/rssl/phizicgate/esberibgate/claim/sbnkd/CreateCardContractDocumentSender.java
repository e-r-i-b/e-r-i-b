package com.rssl.phizicgate.esberibgate.claim.sbnkd;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.sbnkd.CardInfo;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.CreateCardContractRq;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.AbstractJMSClaimSender;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders.CreateCardContractRequestBuilder;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders.RequestBuilder;

/**
 * Сендер: создание договора на карту.
 *
 * @author bogdanov
 * @ created 19.12.14
 * @ $Author$
 * @ $Revision$
 */

public class CreateCardContractDocumentSender extends AbstractJMSClaimSender<CardInfo, CreateCardContractRq>
{
	public CreateCardContractDocumentSender(GateFactory factory)
	{
		super(factory, CreateCardContractRq.class);
	}

	@Override
	protected RequestBuilder<CreateCardContractRq, CardInfo> getRequestBuilder(GateFactory factory)
	{
		return new CreateCardContractRequestBuilder(factory);
	}

	@Override
	protected void setUUID(CardInfo document, String uuid)
	{
		document.setUID(uuid);
		if (StringHelper.isEmpty(document.getParent().getUID()))
			document.getParent().setUID(new RandomGUID().getStringValue());
	}

	@Override
	protected String getUUID(CardInfo document)
	{
		return document.getUID();
	}
}
