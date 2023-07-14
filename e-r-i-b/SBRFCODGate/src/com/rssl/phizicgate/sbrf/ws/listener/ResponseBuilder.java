package com.rssl.phizicgate.sbrf.ws.listener;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.impl.DOMMessageImpl;

/**
 * @author Roshka
 * @ created 25.10.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class ResponseBuilder
{
	public GateMessage buildResponse(ResponseData responseData) throws GateException
	{
		GateMessage codMessage = createCODMessage();

		appendResponseBody(responseData, codMessage);

		return codMessage;
	}

	/**
	 * Формирование существенной части ответа
	 * @param responseData
	 * @param codMessage
	 */
	protected abstract void appendResponseBody(ResponseData responseData, GateMessage codMessage);

	private GateMessage createCODMessage() throws GateException
	{
		return new DOMMessageImpl( getRequestName() );
	}

	public abstract String getRequestName();

}
