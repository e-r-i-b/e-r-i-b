package com.rssl.phizicgate.sbrf.ws.listener;

import com.rssl.phizic.gate.messaging.GateMessage;

/**
 * @author Roshka
 * @ created 26.10.2006
 * @ $Author$
 * @ $Revision$
 */

public class NonreplyBuilder extends ResponseBuilder
{
	protected void appendResponseBody(ResponseData responseData, GateMessage codMessage)
	{
	}

	public String getRequestName()
	{
		return "";
	}
}