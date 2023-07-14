package com.rssl.phizic.authgate;

/**
 * @author Gainanov
 * @ created 07.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class AuthGateException extends Exception
{
	public AuthGateException()
	{
		super();
	}

	public AuthGateException(String message)
	{
		super(message);
	}

	public AuthGateException(Throwable cause)
	{
		super(cause);
	}

	public AuthGateException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
