package com.rssl.phizicgate.esberibgate.ws.jms.common.sender;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.config.ExternalSystemConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.ws.jms.common.JMSTransportProvider;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;

/**
 * @author akrenev
 * @ created 22.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * сендер онлайн запросов во ВС посредством MQ
 */

public abstract class OnlineJMSSender implements Service
{
	private final GateFactory factory;

	protected OnlineJMSSender(GateFactory factory)
	{
		this.factory = factory;
	}

	/**
	 * отправить онлайн запрос
	 * @param processor процессор запроса
	 * @throws GateException
	 * @throws GateLogicException
	 */
	protected final <Rs> void process(OnlineMessageProcessor<Rs> processor) throws GateException, GateLogicException
	{
		Request request = processor.makeRequest();
		long timeout = ConfigFactory.getConfig(ExternalSystemConfig.class).getMQIntegrationTimeout(processor.getServiceName());
		//noinspection unchecked
		Response<Rs> response = JMSTransportProvider.getInstance(processor.getSegment()).processOnline(request, timeout);
		processor.processResponse(response);
	}

	public final GateFactory getFactory()
	{
		return factory;
	}
}
