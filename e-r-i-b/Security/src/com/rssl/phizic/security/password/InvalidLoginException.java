package com.rssl.phizic.security.password;

/**
 * @author Evgrafov
 * @ created 26.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3210 $
 */

public class InvalidLoginException extends PasswordValidationException
{
	protected InvalidLoginException(String message)
	{
		super(message);
	}
}