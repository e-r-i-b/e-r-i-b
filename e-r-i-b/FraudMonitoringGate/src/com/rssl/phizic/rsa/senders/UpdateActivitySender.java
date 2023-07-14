package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.rsa.integration.ws.UpdateActivityTransportProvider;
import com.rssl.phizic.rsa.integration.ws.notification.generated.UpdateActivityRequestType;
import com.rssl.phizic.rsa.integration.ws.notification.generated.UpdateActivityResponseType;
import com.rssl.phizic.rsa.senders.builders.offline.FraudMonitoringOfflineRequestBuilderBase;
import com.rssl.phizic.rsa.senders.builders.offline.FraudMonitoringUpdateActivityOfflineRequestBuilder;
import com.rssl.phizic.rsa.senders.initialization.UpdateActivityInitializationData;
import com.rssl.phizic.rsa.senders.serializers.FraudMonitoringRequestSerializerBase;
import com.rssl.phizic.rsa.senders.serializers.FraudMonitoringUpdateActivitySerializer;

/**
 * @author tisov
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$
 * Сендер запроса UpdateActivity в ActivityEngine-сервис
 */
public class UpdateActivitySender extends ActivityEngineSenderBase<UpdateActivityRequestType, UpdateActivityResponseType, UpdateActivityInitializationData>
{
	private static final String OPERATOR_NAME = "fraudanalys";
	private UpdateActivityInitializationData initializationData;

	@Override
	protected UpdateActivityTransportProvider getTransportProvider()
	{
		return UpdateActivityTransportProvider.getInstance();
	}

	@Override
	protected FraudMonitoringRequestSerializerBase getSerializer()
	{
		return new FraudMonitoringUpdateActivitySerializer();
	}

	public void initialize(UpdateActivityInitializationData data)
	{
		this.initializationData = data;
	}

	public UpdateActivityInitializationData getInitializationData()
	{
		return this.initializationData;
	}

	@Override
	protected String getClientTransactionId() throws GateLogicException, GateException
	{
		return getRequest().getEventId();
	}

	@Override
	protected FraudMonitoringOfflineRequestBuilderBase<UpdateActivityRequestType> getOfflineBuilder()
	{
		return new FraudMonitoringUpdateActivityOfflineRequestBuilder();
	}

	@Override
	protected UpdateActivityRequestType createOnLineRequest() throws GateException, GateLogicException
	{
		RSAConfig config = RSAConfig.getInstance();
		return new UpdateActivityRequestType(this.initializationData.getClientTransactionId(), this.initializationData.getResolution(), OPERATOR_NAME,
				this.initializationData.getMessage(), config.getLogin(), config.getPassword());
	}
}
