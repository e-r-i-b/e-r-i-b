package com.rssl.phizic.esb.ejb.federal.crediting;

import com.rssl.ikfl.crediting.CRMOffersResponceProcessor;
import com.rssl.phizic.esb.ejb.MessageProcessor;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * базовый обработчик ответов CRM по предодобренным предложениям
 */

abstract class ProcessorBase<T> implements MessageProcessor
{
	private static final CRMOffersResponceProcessor crmOffersResponceProcessor = new CRMOffersResponceProcessor();

	protected abstract String getType(T message);

	protected abstract String getId(T message);

	protected abstract void process(T message, Message source);

	public final String getMessageType(Object message)
	{
		return getType(castMessage(message));
	}

	public final String getMessageId(Object message)
	{
		return getId(castMessage(message));
	}

	private T castMessage(Object message)
	{
		//noinspection unchecked
		return (T) message;
	}

	public final void processMessage(Object message, Message source) throws GateLogicException, GateException
	{
		//noinspection unchecked
		T response = castMessage(message);
		process(response, source);
	}

	protected static CRMOffersResponceProcessor getCrmOffersResponseProcessor()
	{
		return crmOffersResponceProcessor;
	}
}
