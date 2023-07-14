package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Evgrafov
 * @ created 03.01.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 3210 $
 */
public class PasswordCardPermanentBlockedException extends SecurityLogicException
{
	/**
	 * ctor
	 */
	public PasswordCardPermanentBlockedException()
	{
		super("Карта паролей заблокирована");
	}
}