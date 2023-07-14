package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.common.types.ConfirmStrategyType;

/**
 * Запрос на начало регистрации мобильного приложения
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */

public class StartSocialRegistrationRequestData extends StartMobileRegistrationRequestData
{
	public static final String REQUEST_NAME = "startSocialRegistrationRq";

	public String getName()
	{
		return REQUEST_NAME;
	}

    public StartSocialRegistrationRequestData(String login, String deviceInfo, ConfirmStrategyType confirmStrategyType, boolean registrationIPasAvailable, String deviceId, String card, String appName)
    {
        super(login,deviceInfo,confirmStrategyType,registrationIPasAvailable,deviceId,card,appName);
    }
}
