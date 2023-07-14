package com.rssl.phizicgate.esberibgate.ws.jms.common.sender;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.ws.jms.common.JMSTransportProvider;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OfflineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;

/**
 * @author akrenev
 * @ created 22.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * сендер оффлайн запросов во ВС посредством MQ
 */

public abstract class OfflineJMSSender implements Service
{
	private final GateFactory factory;

	protected OfflineJMSSender(GateFactory factory)
	{
		this.factory = factory;
	}

	/**
	 * отправить онлайн запрос
	 * @param processor процессор запроса
	 * @throws GateException
	 * @throws GateLogicException
	 */
	protected final void process(OfflineMessageProcessor processor) throws GateException, GateLogicException
	{
		Request request = processor.makeRequest();
		//noinspection unchecked
		JMSTransportProvider.getInstance(processor.getSegment()).processOffline(request);
		processor.processAfterSend(request);
	}

	public final GateFactory getFactory()
	{
		return factory;
	}
}
