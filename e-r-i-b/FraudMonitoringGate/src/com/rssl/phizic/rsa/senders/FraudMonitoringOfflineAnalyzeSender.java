package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.actions.AnalyzeResponseOfflineAction;
import com.rssl.phizic.rsa.actions.ResponseAction;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestSendingType;
import com.rssl.phizic.rsa.senders.serializers.FraudMonitoringEventAnalyzeRequestSerializer;
import com.rssl.phizic.rsa.senders.serializers.FraudMonitoringRequestSerializerBase;

/**
 * @author tisov
 * @ created 10.07.15
 * @ $Author$
 * @ $Revision$
 * Сендер оффлайн-запросов на анализ во фрод-мониторинг
 */
public class FraudMonitoringOfflineAnalyzeSender extends FraudMonitoringAnalyzeSenderBase
{
	protected ResponseAction createResponseAction() throws GateLogicException, GateException
	{
		return new AnalyzeResponseOfflineAction((AnalyzeRequest) createOffLineRequest());
	}

	@Override
	protected GenericRequest createOnLineRequest() throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Метод не поддерживается оффлайн-сендером");
	}

	@Override
	protected FraudMonitoringRequestSerializerBase getSerializer()
	{
		return new FraudMonitoringEventAnalyzeRequestSerializer();
	}

	@Override
	public FraudMonitoringRequestSendingType getSendingType()
	{
		return FraudMonitoringRequestSendingType.OFFLINE;
	}
}
