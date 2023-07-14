package com.rssl.phizic.rsa.senders.builders;

import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import com.rssl.phizic.rsa.senders.FraudMonitoringRequestHelper;

/**
 * Базовый класс билдера Analyze запросов во ВС ФМ
 *
 * @author khudyakov
 * @ created 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeRequestBuilderBase extends RequestBuilderBase<AnalyzeRequest>
{
	public AnalyzeRequest build() throws GateLogicException, GateException
	{
		AnalyzeRequest request = super.build();

		request.setAutoCreateUserFlag(true);
		ApplicationInfo appInfo = ApplicationConfig.getIt().getApplicationInfo();
		request.setChannelIndicator(appInfo.isMobileApi() ? ChannelIndicatorType.MOBILE : ChannelIndicatorType.WEB);
		request.setClientDefinedChannelIndicator(appInfo.isMobileApi() ? FraudMonitoringRequestHelper.MOBILE_API_CLIENT_DEFINED_CHANNEL_INDICATOR_VALUE : FraudMonitoringRequestHelper.WEB_API_CLIENT_DEFINED_CHANNEL_INDICATOR_VALUE);

		request.setRunRiskType(RunRiskType.ALL);

		DeviceManagementRequestPayload deviceManagementRequestPayload = new DeviceManagementRequestPayload();
		deviceManagementRequestPayload.setDeviceData(new DeviceData(BindingType.HARD_BIND, null, null, null, null));
		deviceManagementRequestPayload.setActionTypeList(new DeviceActionTypeList(new DeviceActionType[]{DeviceActionType.UPDATE_DEVICE}));
		request.setDeviceManagementRequest(deviceManagementRequestPayload);

		request.setEventDataList(new EventData[]{createEventData()});
		return request;
	}

	@Override
	protected AnalyzeRequest createRequest()
	{
		return new AnalyzeRequest();
	}

	@Override
	protected RequestType getRequestType()
	{
		return RequestType.ANALYZE;
	}
}
