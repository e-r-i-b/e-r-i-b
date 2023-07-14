package com.rssl.phizic.security.password;

/**
 * @author Egorova
 * @ created 25.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class BlockedException extends PasswordValidationException
{
	public BlockedException(String message)
	{
		super("Ошибка регистрации. "+message);
	}
}
