package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Запрос на начало регистрации мобильного приложения
 * @author Pankin
 * @ created 25.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class StartMobileRegistrationRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "startMobileRegistrationRq";

	private String login;
	private String deviceInfo;
	private ConfirmStrategyType confirmStrategyType;
	private boolean registrationIPasAvailable;
	private String deviceId;
    private String card;
    private String appName;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public StartMobileRegistrationRequestData(String login, String deviceInfo, ConfirmStrategyType confirmStrategyType, boolean registrationIPasAvailable, String deviceId, String appName)
	{
		this.login = login;
		this.deviceInfo = deviceInfo;
		this.confirmStrategyType = confirmStrategyType;
		this.registrationIPasAvailable = registrationIPasAvailable;
		this.deviceId = deviceId;
		this.appName = appName;
        card = null;
	}

    public StartMobileRegistrationRequestData(String login, String deviceInfo, ConfirmStrategyType confirmStrategyType, boolean registrationIPasAvailable, String deviceId, String card, String appName)
    {
        this.login = login;
        this.deviceInfo = deviceInfo;
        this.confirmStrategyType = confirmStrategyType;
        this.registrationIPasAvailable = registrationIPasAvailable;
        this.deviceId = deviceId;
        this.card = card;
        this.appName = appName;
    }

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, RequestConstants.LOGIN_PARAM_NAME, login));
		root.appendChild(createTag(request, RequestConstants.DEVICE_INFO_PARAM_NAME, deviceInfo));
		root.appendChild(createTag(request, RequestConstants.CONFIRMATION_PARAM_NAME, confirmStrategyType.name()));
		root.appendChild(createTag(request, RequestConstants.REGISTRATION_IPAS_PARAM_NAME, Boolean.toString(registrationIPasAvailable)));
		if (!StringHelper.isEmpty(deviceId))
			root.appendChild(createTag(request, RequestConstants.DEVICE_ID_PARAM_NAME, deviceId));
        if (!StringHelper.isEmpty(card))
        			root.appendChild(createTag(request, RequestConstants.CARD_PARAM_NAME, card));
		root.appendChild(createTag(request, RequestConstants.APP_NAME_PARAM_NAME, appName));
		return request;
	}
}
