package com.rssl.phizicgate.iqwave.messaging.mock;

import com.rssl.phizgate.common.messaging.mock.MockRequestHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 21.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardToCardResponseHandler implements MockRequestHandler
{
	private MockRequestHandler successHandler;
	private MockRequestHandler errorHandler;
	private int counter;

	public CardToCardResponseHandler() throws GateException
	{
		successHandler = new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/CardToCardResponse.xml");
		errorHandler = new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/ErrorCardToCardResponse.xml");
	}

	public Document proccessRequest(Document request) throws GateException
	{
		counter++;
		if (counter % 10 == 5)
		{
			//каждый 10 отказываем
			return errorHandler.proccessRequest(request);
		}
		return successHandler.proccessRequest(request);
	}
}
