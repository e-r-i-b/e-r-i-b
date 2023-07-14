package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Omeliyanchuk
 * @ created 27.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class LastOneCardPasswordException extends SecurityLogicException
{
	/**
	 * ctor
	 */
	public LastOneCardPasswordException()
	{
		super("На карте ключей остался один ключ");
	}
}
