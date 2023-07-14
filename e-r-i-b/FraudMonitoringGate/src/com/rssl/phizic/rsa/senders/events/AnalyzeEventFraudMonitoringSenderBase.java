package com.rssl.phizic.rsa.senders.events;

import com.rssl.phizic.rsa.senders.FraudMonitoringAnalyzeSenderBase;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;
import com.rssl.phizic.rsa.senders.serializers.FraudMonitoringEventAnalyzeRequestSerializer;
import com.rssl.phizic.rsa.senders.serializers.FraudMonitoringRequestSerializerBase;

/**
 * @author tisov
 * @ created 11.02.15
 * @ $Author$
 * @ $Revision$
 * ������� ����� ��� �������� �� ������� ����-����������� �� �������
 */
public abstract class AnalyzeEventFraudMonitoringSenderBase<ID extends InitializationData> extends FraudMonitoringAnalyzeSenderBase<ID>
{
	@Override
	protected FraudMonitoringRequestSerializerBase getSerializer()
	{
		return new FraudMonitoringEventAnalyzeRequestSerializer();
	}
}
