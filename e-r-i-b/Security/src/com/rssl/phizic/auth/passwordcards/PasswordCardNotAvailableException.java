package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.security.SecurityLogicException;

/**
 * ѕо какой-то причине невозможно подтвердить операцию паролем с чека
 *
 * @author lepihina
 * @ created 13.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class PasswordCardNotAvailableException extends SecurityLogicException
{
	/**
	 *  онструктор
	 * @param message сообщение
	 */
	public PasswordCardNotAvailableException(String message)
	{
		super(message);
	}
}
