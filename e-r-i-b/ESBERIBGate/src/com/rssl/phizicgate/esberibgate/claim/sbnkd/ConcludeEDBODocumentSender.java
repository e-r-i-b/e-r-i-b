package com.rssl.phizicgate.esberibgate.claim.sbnkd;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.sbnkd.ConcludeEDBODocument;
import com.rssl.phizic.gate.claims.sbnkd.impl.ConcludeEDBODocumentImpl;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.ConcludeEDBORq;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.AbstractJMSClaimSender;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders.ConcludeEDBORequestBuilder;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders.RequestBuilder;

/**
 * Отправка сообщения: заключение УДБО.
 *
 * @author bogdanov
 * @ created 19.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ConcludeEDBODocumentSender extends AbstractJMSClaimSender<ConcludeEDBODocument, ConcludeEDBORq>
{
	public ConcludeEDBODocumentSender(GateFactory factory)
	{
		super(factory, ConcludeEDBORq.class);
	}

	@Override
	protected RequestBuilder<ConcludeEDBORq, ConcludeEDBODocument> getRequestBuilder(GateFactory factory)
	{
		return new ConcludeEDBORequestBuilder(factory);
	}

	@Override
	protected void setUUID(ConcludeEDBODocument document, String uuid)
	{
		document.setUID(uuid);
	}

	@Override
	protected String getUUID(ConcludeEDBODocument document)
	{
		return document.getUID();
	}

	@SuppressWarnings("MagicNumber")
	public static boolean checkUuid(String uuid, ConcludeEDBODocument claim)
	{
		int docIdent = Integer.parseInt(uuid.substring(0, 6), 16);
		if (claim instanceof ConcludeEDBODocumentImpl)
			return docIdent >= 0x007fffff;
		else
			return docIdent < 0x007fffff;
	}

	@SuppressWarnings("MagicNumber")
	public static boolean isBusinessDocument(String uuid)
	{
		int docIdent = Integer.parseInt(uuid.substring(0, 6), 16);
		return docIdent < 0x007fffff;
	}
}
