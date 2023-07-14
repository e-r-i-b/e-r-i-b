package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Запрос окончания регистрации мобильного приложения
 * @author Pankin
 * @ created 28.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class FinishMobileRegistrationRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "finishMobileRegistrationRq";

	private String ouid;
	private String password;
	private String deviceState;
	private String deviceId;
	private String appName;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public FinishMobileRegistrationRequestData(String ouid, String password, String deviceState, String deviceId, String appName)
	{
		this.ouid = ouid;
		this.password = password;
		this.deviceState = deviceState;
		this.deviceId = deviceId;
		this.appName = appName;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, RequestConstants.OUID_PARAM_NAME, ouid));
		root.appendChild(createTag(request, RequestConstants.PASSWORD_PARAM_NAME, password));
		if (!StringHelper.isEmpty(deviceState))
			root.appendChild(createTag(request, RequestConstants.DEVICE_STATE_PARAM_NAME, deviceState));
		if (!StringHelper.isEmpty(deviceId))
			root.appendChild(createTag(request, RequestConstants.DEVICE_ID_PARAM_NAME, deviceId));
		root.appendChild(createTag(request, RequestConstants.APP_NAME_PARAM_NAME, appName));

		return request;
	}
}
