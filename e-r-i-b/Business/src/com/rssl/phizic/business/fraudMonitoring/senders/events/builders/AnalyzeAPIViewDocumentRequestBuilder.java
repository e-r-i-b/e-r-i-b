package com.rssl.phizic.business.fraudMonitoring.senders.events.builders;

import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.rsa.integration.ws.control.generated.DeviceIdentifier;
import com.rssl.phizic.rsa.integration.ws.control.generated.DeviceRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.MobileDevice;
import com.rssl.phizic.rsa.senders.types.EventsType;

/**
 * Билдер запроса для события просмотра документа
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeAPIViewDocumentRequestBuilder extends AnalyzeEventRequestBuilderBase
{
	@Override
	protected EventsType getEventsType()
	{
		return EventsType.VIEW_STATEMENT;
	}

	@Override
	protected String getEventDescription()
	{
		return "Просмотр документа";
	}

	protected DeviceRequest getDeviceRequest()
	{
		DeviceRequest devRequest = super.getDeviceRequest();
		String mobileSdkData = RSAContext.getMobileSdkData();
		if (mobileSdkData != null)
		{
			MobileDevice mobileDevice = new MobileDevice();
			mobileDevice.setMobileSdkData(mobileSdkData);
			devRequest.setDeviceIdentifier(new DeviceIdentifier[]{mobileDevice});
		}
		return devRequest;
	}
}
