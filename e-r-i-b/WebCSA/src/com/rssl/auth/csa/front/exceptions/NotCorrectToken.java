package com.rssl.auth.csa.front.exceptions;

/**
 * @author niculichev
 * @ created 14.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class NotCorrectToken extends FrontException
{
	public NotCorrectToken()
	{
	}

	public NotCorrectToken(String message)
	{
		super(message);
	}

	public NotCorrectToken(Throwable cause)
	{
       super(cause);
    }

	public NotCorrectToken(String message, Throwable cause)
	{
	   super(message, cause);
	}
}
