package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author tisov
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UserProfileBlockedException extends BackLogicException
{

	private static final String DEFAULT_MESSAGE = "Уважаемый клиент, ваша учётная запись заблокирована. Для того чтобы возобновить обслуживание, позвоните в контактный центр по номеру 8-800-555-5550 или обратитесь в ближайшее отделение Сбербанка России.";

	public UserProfileBlockedException()
	{
		super(DEFAULT_MESSAGE);
	}
}
