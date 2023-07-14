package com.rssl.phizic.security.password;

import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Evgrafov
 * @ created 23.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3210 $
 */
public class PasswordValidationException extends SecurityLogicException
{
	protected PasswordValidationException(String message)
	{
		super(message);
	}
}