package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Обработчик запроса на получение информации о пользователе по идентификатор клиента во внешнем приложении и идентификатору платформы
 * @author Jatsky
 * @ created 06.06.14
 * @ $Author$
 * @ $Revision$
 */

public class GetUserInfoByDeviceIdAndInfoRequestProcessor extends GetUserInfoRequestProcessorBase
{
	private static final String RESPONSE_TYPE = "getUserInfoByDeviceIdAndInfoRs";
	private static final String REQUEST_TYPE = "getUserInfoByDeviceIdAndInfoRq";

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		Element element = document.getDocumentElement();

		String deviceId = XmlHelper.getSimpleElementValue(element, Constants.DEVICE_ID_TAG);
		String deviceInfo = XmlHelper.getSimpleElementValue(element, Constants.DEVICE_INFO_TAG);
		return doRequest(deviceId, deviceInfo);
	}

	protected ResponseInfo doRequest(String deviceId, String deviceInfo) throws Exception
	{
		Connector connector = Connector.findByDeviceIdAndInfo(deviceId, deviceInfo);
		if (connector != null)
		{
			return createUserAndNodeInfoRs(connector.getProfile());
		}

		return getFailureResponseBuilder().buildActiveCSAConnectorsNotFoundResponse("Не найдены активные коннекторы с deviceId = " + deviceId + " и deviceInfo = " + deviceInfo);
	}
}

