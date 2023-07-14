package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.actions.NullAction;
import com.rssl.phizic.rsa.actions.ResponseAction;
import com.rssl.phizic.rsa.actions.SyncNotifyAction;
import com.rssl.phizic.rsa.integration.ws.control.generated.NotifyRequest;
import com.rssl.phizic.rsa.senders.builders.offline.FraudMonitoringNotifyOfflineRequestBuilder;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;
import com.rssl.phizic.rsa.senders.serializers.FraudMonitoringEventNotifyRequestSerializer;
import com.rssl.phizic.rsa.senders.serializers.FraudMonitoringRequestSerializerBase;

/**
 * @author tisov
 * @ created 09.02.15
 * @ $Author$
 * @ $Revision$
 * Базовый класс сендеров оповещений во фрод-мониторинг
 */
public abstract class FraudMonitoringNotifySenderBase<ID extends InitializationData> extends FraudMonitoringDataSenderBase<ID, NotifyRequest>
{
	@Override
	protected ResponseAction createResponseAction() throws GateLogicException, GateException
	{
		ID data = getInitializationData();
		switch (data.getInteractionType())
		{
			case NONE: return new NullAction();
			case SYNC: return new SyncNotifyAction(getRequest());
			default : throw new IllegalArgumentException("Некорректное состояние объекта.");
		}
	}

	@Override
	protected FraudMonitoringRequestSerializerBase getSerializer()
	{
		return new FraudMonitoringEventNotifyRequestSerializer();
	}

	@Override
	protected FraudMonitoringNotifyOfflineRequestBuilder getOfflineBuilder()
	{
		return new FraudMonitoringNotifyOfflineRequestBuilder();
	}
}
