package com.rssl.phizicgate.sbrf.ws.listener.baikal;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.InvalidTargetException;
import com.rssl.phizicgate.sbrf.ws.listener.CODListenerMessageReceiver;

/**
 * @author gladishev
 * @ created 01.11.2013
 * @ $Author$
 * @ $Revision$
 */

public class MulticastCODListenerMessageReceiver extends CODListenerMessageReceiver
{
	public MulticastCODListenerMessageReceiver(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected String processInvalidTargetException(InvalidTargetException ex, String xmlMessage) throws GateException
	{
		//пробрасываем исключение дальше.
		throw ex;
	}
}
