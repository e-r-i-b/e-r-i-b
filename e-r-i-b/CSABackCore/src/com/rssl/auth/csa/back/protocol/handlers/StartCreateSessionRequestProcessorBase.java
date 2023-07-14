package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.rsa.FraudMonitoringSendersFactory;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.FailedLoginInitializationData;
import com.rssl.phizic.rsa.senders.types.EventsType;

import java.util.TreeMap;


/**
 * @author tisov
 * @ created 27.05.15
 * @ $Author$
 * @ $Revision$
 * Базовый процессор создания сессии клиента
 */
public abstract class StartCreateSessionRequestProcessorBase extends LogableProcessorBase
{
	@Override
	protected void exceptionProcessing(LogIdentificationContext logIdentificationContext, RequestInfo requestInfo) throws GateLogicException, GateException
	{
		try
		{
			IdentificationContext context = logIdentificationContext.getIdentificationContext();
			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(getEventType());
			//noinspection unchecked
			sender.initialize(new FailedLoginInitializationData(context.getUserId(), context.getProfile().getTb(), requestInfo.getIP(), context.getProfile().getId(), createRSAData(requestInfo)));
			sender.send();
		}
		catch (Exception e)
		{
			log.error(e);
		}
	};

	protected abstract EventsType getEventType();

	protected abstract TreeMap<String, String> createRSAData(RequestInfo info);
}
