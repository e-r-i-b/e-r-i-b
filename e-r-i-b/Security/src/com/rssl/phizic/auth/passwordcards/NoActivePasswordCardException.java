package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Krenev
 * @ created 18.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class NoActivePasswordCardException extends SecurityLogicException
{

	public NoActivePasswordCardException()
	{
		super("Нет активной карты ключей");
	}
}
