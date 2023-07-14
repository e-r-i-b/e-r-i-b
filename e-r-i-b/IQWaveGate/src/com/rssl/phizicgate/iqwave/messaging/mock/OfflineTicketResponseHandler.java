package com.rssl.phizicgate.iqwave.messaging.mock;

import com.rssl.phizgate.common.messaging.mock.MockRequestHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 02.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class OfflineTicketResponseHandler implements MockRequestHandler
{
	private MockRequestHandler handlerWithBody;
	private MockRequestHandler handlerWithoutBody;
	private int counter;

	public OfflineTicketResponseHandler() throws GateException
	{
		handlerWithBody = new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/OfflineTicket.xml");
		handlerWithoutBody = new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/OfflineTicketNoBody.xml");
	}

	public Document proccessRequest(Document request) throws GateException
	{
		counter++;
		if (counter % 2 == 0)
		{
			return handlerWithoutBody.proccessRequest(request);
		}
		return handlerWithBody.proccessRequest(request);
	}
}
