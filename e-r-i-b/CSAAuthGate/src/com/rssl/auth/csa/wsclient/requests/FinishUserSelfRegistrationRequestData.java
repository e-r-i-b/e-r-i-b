package com.rssl.auth.csa.wsclient.requests;

/**
 * @author osminin
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * данные по запросу завершения самостоятельной регистрации
 */
public class FinishUserSelfRegistrationRequestData extends FinishUserRegistrationRequestData
{
	private static final String REQUEST_NAME = "finishUserSelfRegistrationRq";

	public String getName()
	{
		return REQUEST_NAME;
	}
	/**
	 * Конеструктор
	 * @param ouid идентификатор
	 * @param login логин
	 * @param password пароль
	 * @param notification нужно ли использовать оповещение
	 */
	public FinishUserSelfRegistrationRequestData(String ouid, String login, String password, boolean notification)
	{
		super(ouid, login, password, notification);
	}
}
