package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * Запрос на получение информации о пользователе по идентификатор клиента во внешнем приложении и идентификатору платформы
 * @author Jatsky
 * @ created 06.06.14
 * @ $Author$
 * @ $Revision$
 */

public class GetUserInfoByDeviceIdAndInfoRequestData extends RequestDataBase
{
	private static final String REQUEST_DATA_NAME = "getUserInfoByDeviceIdAndInfoRq";

	private String deviceId;
	private String deviceInfo;

	public GetUserInfoByDeviceIdAndInfoRequestData(String deviceId, String deviceInfo)
	{
		this.deviceId = deviceId;
		this.deviceInfo = deviceInfo;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.DEVICE_ID_PARAM_NAME, deviceId);
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.DEVICE_INFO_PARAM_NAME, deviceInfo);

		return document;
	}
}
