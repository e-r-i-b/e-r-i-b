package com.rssl.phizic.rsa.senders.builders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;

/**
 * Базовый класс билдера Notify запросов во ВС ФМ
 *
 * @author khudyakov
 * @ created 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class NotifyRequestBuilderBase<ID extends InitializationData> extends RequestBuilderBase<NotifyRequest>
{
	@Override
	public NotifyRequest build() throws GateException, GateLogicException
	{
		NotifyRequest request = super.build();
		request.setAutoCreateUserFlag(true);
		DeviceManagementRequestPayload deviceManagementRequestPayload = new DeviceManagementRequestPayload();
		deviceManagementRequestPayload.setDeviceData(new DeviceData(BindingType.HARD_BIND, null, null, null, null));
		deviceManagementRequestPayload.setActionTypeList(new DeviceActionTypeList(new DeviceActionType[]{DeviceActionType.UPDATE_DEVICE}));
		request.setDeviceManagementRequest(deviceManagementRequestPayload);
		request.setEventDataList(new EventData[]{createEventData()});
		return  request;
	}

	@Override
	protected RequestType getRequestType()
	{
		return RequestType.NOTIFY;
	}

	@Override
	protected NotifyRequest createRequest()
	{
		return new NotifyRequest();
	}
}