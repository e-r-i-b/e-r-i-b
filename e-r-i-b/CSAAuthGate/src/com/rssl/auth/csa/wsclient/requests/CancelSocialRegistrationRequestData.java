package com.rssl.auth.csa.wsclient.requests;

/**
 * Запрос отмены регистрации социального приложения
 * @author sergunin
 * @ created 04.09.14
 * @ $Author$
 * @ $Revision$
 */

public class CancelSocialRegistrationRequestData extends CancelMobileRegistrationRequestData
{
	public static final String REQUEST_NAME = "cancelSocialRegistrationRq";


	public String getName()
	{
		return REQUEST_NAME;
	}

	public CancelSocialRegistrationRequestData(String guid)
	{
		super(guid);
	}
}
