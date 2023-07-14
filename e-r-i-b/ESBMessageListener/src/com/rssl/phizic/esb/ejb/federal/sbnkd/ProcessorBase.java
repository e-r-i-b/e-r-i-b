package com.rssl.phizic.esb.ejb.federal.sbnkd;

import com.rssl.phizic.esb.ejb.MessageProcessor;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.claim.sbnkd.IssueCardClaimProcessor;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * базовый процессор обработки заявок СБНКД
 */

abstract class ProcessorBase<T> implements MessageProcessor
{
	protected abstract String getExternalId(T message, Message source);

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
		//noinspection unchecked
		return (T) message;
	}

	public final void processMessage(Object message, Message source) throws GateLogicException, GateException
	{
		//noinspection unchecked
		T response = castMessage(message);
		IssueCardClaimProcessor.getIt().process(getExternalId(response, source), response, getStatus(response));
	}
}
