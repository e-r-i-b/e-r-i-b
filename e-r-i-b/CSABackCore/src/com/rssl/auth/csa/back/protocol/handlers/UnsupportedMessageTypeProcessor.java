package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.RequestProcessor;
import com.rssl.auth.csa.back.protocol.ResponseInfo;

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

	public boolean isAccessStandIn()
	{
		return true;
	}
}
