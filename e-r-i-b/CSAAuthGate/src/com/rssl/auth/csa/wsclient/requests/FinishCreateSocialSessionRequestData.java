package com.rssl.auth.csa.wsclient.requests;

/**
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 *
 * запрос на аутентификацию пользователя через socialApi
 */
public class FinishCreateSocialSessionRequestData extends FinishCreateMobileSessionRequestData
{
	private static final String REQUEST_DATA_NAME = "finishCreateSocialSessionRq";

	/**
	 * конструктор
	 * @param ouid идентификатор операции
	 */
	public FinishCreateSocialSessionRequestData(String ouid)
	{
		super(ouid);
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

}
