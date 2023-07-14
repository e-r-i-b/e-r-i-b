package com.rssl.phizic.gate.clients.stoplist;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Roshka
 * @ created 15.02.2007
 * @ $Author$
 * @ $Revision$
 */

public class StopListGateException extends GateException
{

	public StopListGateException()
	{
		super();
	}

	public StopListGateException(Throwable cause)
	{
		super(cause);
	}

	public StopListGateException(String message)
	{
		super(message);
	}

	public StopListGateException(String message, Throwable cause)
	{
		super(message, cause);
	}
}