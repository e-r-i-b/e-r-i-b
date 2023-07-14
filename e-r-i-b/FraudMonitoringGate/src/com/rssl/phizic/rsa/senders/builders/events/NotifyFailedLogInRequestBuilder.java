package com.rssl.phizic.rsa.senders.builders.events;

import com.rssl.phizic.rsa.integration.ws.control.generated.DeviceRequest;
import com.rssl.phizic.rsa.senders.initialization.FailedLoginInitializationData;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

import static com.rssl.phizic.context.Constants.*;
import static com.rssl.phizic.context.Constants.DEVICE_TOKEN_FSO_PARAMETER_NAME;

/**
 * Билдер сообщений во Фрод-мониторинг по событию неудачного входа в приложение
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class NotifyFailedLogInRequestBuilder extends NotifyFailedLogInRequestBuilderBase<FailedLoginInitializationData>
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

		deviceRequest.setHttpAccept(source.get(HTTP_ACCEPT_HEADER_NAME));
		deviceRequest.setHttpAcceptChars(source.get(HTTP_ACCEPT_CHARS_HEADER_NAME));
		deviceRequest.setHttpAcceptEncoding(source.get(HTTP_ACCEPT_ENCODING_HEADER_NAME));
		deviceRequest.setHttpAcceptLanguage(source.get(HTTP_ACCEPT_LANGUAGE_HEADER_NAME));
		deviceRequest.setHttpReferrer(source.get(HTTP_REFERRER_HEADER_NAME));

		deviceRequest.setUserAgent(source.get(HTTP_USER_AGENT_HEADER_NAME));
		deviceRequest.setPageId(source.get(PAGE_ID_PARAMETER_NAME));
		deviceRequest.setDomElements(source.get(DOM_ELEMENTS_PARAMETER_NAME));
		deviceRequest.setJsEvents(source.get(JS_EVENTS_PARAMETER_NAME));
		deviceRequest.setDevicePrint(source.get(DEVICE_PRINT_PARAMETER_NAME));
		deviceRequest.setDeviceTokenFSO(source.get(DEVICE_TOKEN_FSO_PARAMETER_NAME));

		return deviceRequest;
	}
}
