package com.rssl.phizic.esb.ejb.federal.loan;

import com.rssl.phizic.esb.ejb.MessageProcessor;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import javax.jms.Message;

/**
 * @author sergunin
 * @ created 02.06.15
 * @ $Author$
 * @ $Revision$
 *
 * базовый процессор обработки отложенных запросов по кредитам
 */

abstract class ProcessorBase<T> implements MessageProcessor
{
	protected abstract Object getStatus(T message);

	protected abstract String getType(T message);

	protected abstract String getId(T message);

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
		return (T) message;
	}

	public final void processMessage(Object message, Message source) throws GateLogicException, GateException
	{
	}
}
