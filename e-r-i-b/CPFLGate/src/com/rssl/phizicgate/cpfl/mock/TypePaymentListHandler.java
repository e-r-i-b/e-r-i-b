package com.rssl.phizicgate.cpfl.mock;

import com.rssl.phizgate.common.messaging.mock.FilenameSelectorRequesHandler;
import com.rssl.phizgate.common.messaging.mock.MockRequestHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import org.w3c.dom.Document;

/**
 * Обработчик запроса типов платежа(typePaymentList_q).
 * @author krenev
 * @ created 18.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class TypePaymentListHandler extends MockRequestHandlerBase
{
	private MockRequestHandler handler;

	public TypePaymentListHandler() throws GateException
	{
		handler = new FilenameSelectorRequesHandler
				(
						"com/rssl/phizicgate/cpfl/messaging/examples/xml/typePaymentList_a_0.xml",
						"com/rssl/phizicgate/cpfl/messaging/examples/xml/typePaymentList_a_3.xml"
				);
	}

	public Document doProcessRequest(Document request) throws GateException
	{
		return handler.proccessRequest(request);
	}
}
