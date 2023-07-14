package com.rssl.auth.csa.wsclient.exceptions;

/**
 * Исключение, сигнализируюее о необходимости аутентификации
 * @author niculichev
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class AuthentificationReguiredException extends BackLogicException
{
	public AuthentificationReguiredException()
	{
		super();
	}

	public AuthentificationReguiredException(String message)
	{
		super(message);
	}

	public AuthentificationReguiredException(Throwable cause)
	{
        super(cause);
    }

	public AuthentificationReguiredException(String message, Throwable cause)
	{
	    super(message, cause);
	}
}
