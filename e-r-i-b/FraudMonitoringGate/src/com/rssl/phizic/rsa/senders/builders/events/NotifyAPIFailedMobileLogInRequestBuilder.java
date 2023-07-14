package com.rssl.phizic.rsa.senders.builders.events;

import com.rssl.phizic.rsa.integration.ws.control.generated.DeviceIdentifier;
import com.rssl.phizic.rsa.integration.ws.control.generated.DeviceRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.MobileDevice;
import com.rssl.phizic.rsa.senders.initialization.FailedLoginInitializationData;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

import static com.rssl.phizic.context.Constants.MOBILE_SDK_DATA_PARAMETER_NAME;

/**
 * Билдер отправки сообщения во фрод-мониторинг о неудачном входе в мобильное приложение
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class NotifyAPIFailedMobileLogInRequestBuilder extends NotifyFailedLogInRequestBuilderBase<FailedLoginInitializationData>
{
	@Override
	protected DeviceRequest getDeviceRequest()
	{
		DeviceRequest deviceRequest = new DeviceRequest();
		deviceRequest.setIpAddress(getInitializationData().getIp());

		Map<String, String> source = getInitializationData().getRsaData();
		if (MapUtils.isEmpty(source))
		{
			return deviceRequest;
		}

		MobileDevice mobileDevice = new MobileDevice();
		mobileDevice.setMobileSdkData(source.get(MOBILE_SDK_DATA_PARAMETER_NAME));
		deviceRequest.setDeviceIdentifier(new DeviceIdentifier[]{mobileDevice});
		return deviceRequest;
	}
}
