package com.rssl.phizicgate.iqwave.messaging.mock;

import com.rssl.phizgate.common.messaging.mock.MockRequestHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import org.w3c.dom.Document;

/**
 * @author hudyakov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class RostelecomDebtsRequestHandler implements MockRequestHandler
{
	private final IQWFilenameRequestHandler filenameRequestHandler;

	public RostelecomDebtsRequestHandler() throws GateException
	{
		filenameRequestHandler = new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/PaymentDebtsRostelecomResponse.xml");
	}

	public Document proccessRequest(Document request) throws GateException
	{
		return filenameRequestHandler.proccessRequest(request);
	}
}
