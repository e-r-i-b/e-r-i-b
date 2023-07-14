package com.rssl.auth.csa.wsclient.requests;

/**
 * Запрос окончания регистрации социального приложения
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */

public class FinishSocialRegistrationRequestData extends FinishMobileRegistrationRequestData
{
	public static final String REQUEST_NAME = "finishSocialRegistrationRq";

	public String getName()
	{
		return REQUEST_NAME;
	}

	public FinishSocialRegistrationRequestData(String ouid, String password, String deviceState, String deviceId, String appName)
	{
		super(ouid, password, deviceState, deviceId, appName);
	}
}
