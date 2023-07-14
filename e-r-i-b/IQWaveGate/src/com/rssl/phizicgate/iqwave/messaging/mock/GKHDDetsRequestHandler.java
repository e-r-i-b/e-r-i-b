package com.rssl.phizicgate.iqwave.messaging.mock;

import com.rssl.phizgate.common.messaging.mock.MockRequestHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author krenev
 * @ created 15.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class GKHDDetsRequestHandler implements MockRequestHandler
{
	private List<MockRequestHandler> handlersMap = new ArrayList<MockRequestHandler>();

	public GKHDDetsRequestHandler() throws GateException
	{
		handlersMap.add(new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/PaymentDebtsGkhResponseEmpty.xml"));
		handlersMap.add(new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/PaymentDebtsGkhResponse1case.xml"));
		handlersMap.add(new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/PaymentDebtsGkhResponse2case.xml"));
		handlersMap.add(new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/PaymentDebtsGkhResponse2debts.xml"));
		handlersMap.add(new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/PaymentDebtsGkhResponse-BUG023018.xml"));
	}

	public Document proccessRequest(Document request) throws GateException
	{
		return handlersMap.get(new Random().nextInt(handlersMap.size())).proccessRequest(request);
//		return handlersMap.get(4).proccessRequest(request);
	}
}
