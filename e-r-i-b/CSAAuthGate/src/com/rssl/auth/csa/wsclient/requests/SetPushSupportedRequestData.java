package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author EgorovaA
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Класс запроса на изменение признака подключения push-уведомлений в CSABack
 */
public class SetPushSupportedRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "changePushSupportedRq";

	private String guid;
	private String deviceId;
	private boolean pushSupported;
	private String securityToken;

	public SetPushSupportedRequestData(String guid, String deviceId, boolean pushSupported, String securityToken)
	{
		this.guid = guid;
		this.deviceId = deviceId;
		this.pushSupported = pushSupported;
		this.securityToken = securityToken;
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, RequestConstants.GUID_PARAM_NAME, guid));
		root.appendChild(createTag(request, RequestConstants.DEVICE_ID_PARAM_NAME, deviceId));
		root.appendChild(createTag(request, RequestConstants.PUSH_SUPPORTED_PARAM_NAME, Boolean.toString(pushSupported)));
		root.appendChild(createTag(request, RequestConstants.SECURITY_TOKEN_PARAM_NAME, securityToken));
		return request;
	}
}
