package com.rssl.phizic.ermb.ejb;

import com.rssl.phizic.ejb.EjbListenerBase;
import com.rssl.phizic.messaging.MessageProcessor;
import com.rssl.phizic.module.Module;

/**
 * @author EgorovaA
 * @ created 04.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class TransportSmsResponseListener extends EjbListenerBase
{
	private final ErmbTransportChannel ermbTransportChannel = moduleManager.getModule(ErmbTransportChannel.class);

	protected Module getModule()
	{
		return ermbTransportChannel;
	}

	protected MessageProcessor getMessageProcessor()
	{
		return ermbTransportChannel.getTransportSmsResponseProcessor();
	}
}
