package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.actions.*;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.senders.builders.offline.FraudMonitoringAnalyzeOfflineRequestBuilder;
import com.rssl.phizic.rsa.senders.builders.offline.FraudMonitoringOfflineRequestBuilderBase;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;

/**
 * @author tisov
 * @ created 09.02.15
 * @ $Author$
 * @ $Revision$
 * Базовый класс аналитических запросов во фрод-мониторинг
 */
public abstract class FraudMonitoringAnalyzeSenderBase<ID extends InitializationData> extends FraudMonitoringDataSenderBase<ID, AnalyzeRequest>
{
	@Override
	protected ResponseAction createResponseAction() throws GateException, GateLogicException
	{
		ID data = getInitializationData();
		switch (data.getInteractionType())
		{
			case NONE: return new NullAction();
			case SYNC: return new SyncAnalyzeAction(getRequest());
			case ASYNC:
			{
				switch (data.getPhaseType())
				{
					case SENDING_REQUEST:       return new ASyncAnalyzeSendingPhaseAction(getRequest());
					case WAITING_FOR_RESPONSE:  return new ASyncAnalyzeWaitingPhaseAction();
				}
			}
			default: throw new IllegalArgumentException("Некорректное состояние объекта.");
		}
	}

	@Override
	protected FraudMonitoringOfflineRequestBuilderBase getOfflineBuilder()
	{
		return new FraudMonitoringAnalyzeOfflineRequestBuilder();
	}
}
