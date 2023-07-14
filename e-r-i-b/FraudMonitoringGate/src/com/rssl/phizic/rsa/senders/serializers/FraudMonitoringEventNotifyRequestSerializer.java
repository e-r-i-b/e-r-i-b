package com.rssl.phizic.rsa.senders.serializers;

import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestType;

/**
 * @author tisov
 * @ created 07.07.15
 * @ $Author$
 * @ $Revision$
 * Сериализатор оповестительных фрод-запросов по событию
 */
public class FraudMonitoringEventNotifyRequestSerializer extends FraudMonitoringNotifyRequestSerializerBase
{

	@Override
	protected FraudMonitoringRequestType getRequestType()
	{
		return FraudMonitoringRequestType.NOTIFY_BY_EVENT;
	}
}
