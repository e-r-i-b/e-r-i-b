package com.rssl.phizicgate.iqwave.messaging.mock;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizicgate.iqwave.messaging.IQWaveMessageData;
import com.rssl.phizicgate.iqwave.messaging.IQWaveMessagingService;

/**
 * @author gladishev
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockIQWaveMessagingService extends IQWaveMessagingService
{

	public MockIQWaveMessagingService(GateFactory factory) throws GateException
	{
		super(factory);
	}

	public MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException
	{
		IQWaveMessageData iqWaveMessageData = (IQWaveMessageData) messageData;
		String result = MockRequestProcessor.processMessage(iqWaveMessageData.getBody());
		return new IQWaveMessageData(result);
	}
}