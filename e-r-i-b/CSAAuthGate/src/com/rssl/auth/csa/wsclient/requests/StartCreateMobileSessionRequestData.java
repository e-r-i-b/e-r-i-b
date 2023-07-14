package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author osminin
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 *
 * запрос на начало входа пользователя через МАПИ
 */
public class StartCreateMobileSessionRequestData extends RequestDataBase
{
	private static final String REQUEST_DATA_NAME = "startCreateMobileSessionRq";

	private String guid;
	private String deviceState;
	private String deviceId;
	private AuthorizedZoneType authorizedZoneType;
	private String pin;
	private String mobileSDKData;

	private final String version;

	/**
	 * конструктор
	 * @param guid идентификатор приложения
	 * @param deviceState состояние устройства
	 * @param deviceId идентификатор устройства
	 * @param version Версия протокола
	 * @param authorizedZoneType Тип зоны входа пользователя
	 */
	public StartCreateMobileSessionRequestData(String guid, String deviceState, String deviceId, String version, AuthorizedZoneType authorizedZoneType, String pin, String mobileSDKData)
	{
		if (authorizedZoneType == null)
		{
			throw new IllegalArgumentException("Тип зоны входа пользователя не может быть null");
		}
		this.guid = guid;
		this.deviceState = deviceState;
		this.deviceId = deviceId;
		this.version = version;
		this.authorizedZoneType = authorizedZoneType;
		this.pin = pin;
		this.mobileSDKData = mobileSDKData;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		XmlHelper.appendSimpleElement(root, RequestConstants.GUID_PARAM_NAME, guid);
		if (StringHelper.isNotEmpty(deviceState))
			XmlHelper.appendSimpleElement(root, RequestConstants.DEVICE_STATE_PARAM_NAME, deviceState);
		XmlHelper.appendSimpleElement(root, RequestConstants.DEVICE_ID_PARAM_NAME, deviceId);
		XmlHelper.appendSimpleElement(root, RequestConstants.VERSION_PARAM_NAME, version);
		XmlHelper.appendSimpleElement(root, RequestConstants.AUTHORIZED_ZONE_PARAM_NAME, authorizedZoneType.name());
		if (StringHelper.isNotEmpty(pin))
			XmlHelper.appendSimpleElement(root, RequestConstants.PASSWORD_PARAM_NAME, pin);
		if (StringHelper.isNotEmpty(mobileSDKData))
		{
			XmlHelper.appendSimpleElement(root, RequestConstants.MOBILE_SDK_DATA_PARAM_NAME, mobileSDKData);
		}

		return request;
	}
}
