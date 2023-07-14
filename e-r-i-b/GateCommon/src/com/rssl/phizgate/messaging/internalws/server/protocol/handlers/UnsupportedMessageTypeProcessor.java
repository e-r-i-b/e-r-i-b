package com.rssl.phizgate.messaging.internalws.server.protocol.handlers;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;

/**
 * @author krenev
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class UnsupportedMessageTypeProcessor implements RequestProcessor
{
	public static final UnsupportedMessageTypeProcessor INSTANCE = new UnsupportedMessageTypeProcessor();

	public ResponseInfo process(RequestInfo requestInfo)
	{
		throw new UnsupportedOperationException("Недопустимый тип запроса: " + requestInfo.getType());
	}
}
