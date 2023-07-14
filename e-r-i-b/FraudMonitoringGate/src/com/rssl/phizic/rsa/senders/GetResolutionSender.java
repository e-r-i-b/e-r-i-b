package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.rsa.integration.ws.GetResolutionTransportProvider;
import com.rssl.phizic.rsa.integration.ws.notification.generated.GetResolutionRequestType;
import com.rssl.phizic.rsa.integration.ws.notification.generated.GetResolutionResponseType;
import com.rssl.phizic.rsa.senders.builders.offline.FraudMonitoringOfflineRequestBuilderBase;
import com.rssl.phizic.rsa.senders.initialization.GetResolutionInitializationData;
import com.rssl.phizic.rsa.senders.serializers.FraudMonitoringRequestSerializerBase;

/**
 * @author tisov
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$
 * ������ ������� GetResolution � ActivityEngine
 */
public class GetResolutionSender extends ActivityEngineSenderBase<GetResolutionRequestType, GetResolutionResponseType, GetResolutionInitializationData>
{

	private GetResolutionInitializationData initializationData;

	@Override
	protected GetResolutionTransportProvider getTransportProvider()
	{
		return GetResolutionTransportProvider.getInstance();
	}

	@Override
	protected FraudMonitoringRequestSerializerBase getSerializer()
	{
		throw new UnsupportedOperationException("������� GetResolution �� ����������� ��� ����-��������");
	}

	public void initialize(GetResolutionInitializationData data)
	{
		this.initializationData = data;
	}

	public GetResolutionInitializationData getInitializationData()
	{
		return this.initializationData;
	}

	@Override
	protected String getClientTransactionId() throws GateLogicException, GateException
	{
		return getRequest().getEventId();
	}

	@Override
	protected FraudMonitoringOfflineRequestBuilderBase<GetResolutionRequestType> getOfflineBuilder()
	{
		throw new UnsupportedOperationException("������ GetResolution �� ����� ��������� � ���� ������");
	}

	@Override
	protected GetResolutionRequestType createOnLineRequest() throws GateException, GateLogicException
	{
		RSAConfig config = RSAConfig.getInstance();

		return new GetResolutionRequestType(this.initializationData.getClientTransactionId(), config.getLogin(), config.getPassword());
	}

	protected void saveNotification() {}
}
