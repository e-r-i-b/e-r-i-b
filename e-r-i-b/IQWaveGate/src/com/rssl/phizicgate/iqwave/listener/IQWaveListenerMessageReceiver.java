package com.rssl.phizicgate.iqwave.listener;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizicgate.iqwave.utils.DocumentOfflineHandlerHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Krenev
 * @ created 25.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class IQWaveListenerMessageReceiver extends IQWaveListenerMessageReceiverBase
{
	public IQWaveListenerMessageReceiver(GateFactory factory)
	{
		super(factory);
	}

	@Override
	protected MesssageProcessor getMessageProcessor(String requestType, String request)
	{
		return DocumentOfflineHandlerHelper.getExecutionOfflineRequestHandler(requestType);
	}
}
