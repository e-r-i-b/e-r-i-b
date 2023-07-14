package com.rssl.phizicgate.sbrf.ws.listener;

import com.rssl.phizic.gate.messaging.GateMessage;

/**
 * @author Roshka
 * @ created 25.10.2006
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmationBuilder extends ResponseBuilder
{
	protected void appendResponseBody(ResponseData responseData, GateMessage codMessage)
	{
	}

	public String getRequestName()
	{
		return "confirmation_a";
	}
}