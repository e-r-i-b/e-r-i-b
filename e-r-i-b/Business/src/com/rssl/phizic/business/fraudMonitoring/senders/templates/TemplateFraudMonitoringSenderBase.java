package com.rssl.phizic.business.fraudMonitoring.senders.templates;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.actions.*;
import com.rssl.phizic.rsa.senders.FraudMonitoringAnalyzeSenderBase;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;
import com.rssl.phizic.rsa.senders.serializers.FraudMonitoringDocumentRequestSerializer;
import com.rssl.phizic.rsa.senders.serializers.FraudMonitoringRequestSerializerBase;

/**
 * Базовый класс сендеров во Фрод-мониторинг по шаблону документа
 *
 * @author khudyakov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class TemplateFraudMonitoringSenderBase<ID extends InitializationData> extends FraudMonitoringAnalyzeSenderBase<ID>
{
	protected abstract FraudAuditedObject getFraudAuditedObject();

	@Override
	protected ResponseAction createResponseAction() throws GateException, GateLogicException
	{
		ID data = getInitializationData();
		switch (data.getInteractionType())
		{
			case NONE: return new NullAction();
			case SYNC: return new SyncAnalyzeObjectAction(getRequest(), getFraudAuditedObject());
			case ASYNC:
			{
				switch (data.getPhaseType())
				{
					case SENDING_REQUEST:       return new ASyncAnalyzeSendingPhaseObjectAction(getRequest(), getFraudAuditedObject());
					case WAITING_FOR_RESPONSE:  return new ASyncAnalyzeWaitingPhaseObjectAction(getFraudAuditedObject());
				}
			}
			default: throw new IllegalArgumentException("Некорректное состояние объекта.");
		}
	}

	@Override
	protected FraudMonitoringRequestSerializerBase getSerializer()
	{
		return new FraudMonitoringDocumentRequestSerializer();
	}
}
