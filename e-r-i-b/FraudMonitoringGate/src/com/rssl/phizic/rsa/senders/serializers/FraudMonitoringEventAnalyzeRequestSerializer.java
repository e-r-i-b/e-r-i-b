package com.rssl.phizic.rsa.senders.serializers;

import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestType;

/**
 * @author tisov
 * @ created 08.07.15
 * @ $Author$
 * @ $Revision$
 * ����� ������������ ������������� �������� �� ����-���������� �� �������
 */
public class FraudMonitoringEventAnalyzeRequestSerializer extends FraudMonitoringAnalyzeRequestSerializerBase
{
	@Override
	protected FraudMonitoringRequestType getRequestType()
	{
		return FraudMonitoringRequestType.ANALYZE_BY_EVENT;
	}
}
