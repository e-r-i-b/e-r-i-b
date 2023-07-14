package com.rssl.auth.csa.wsclient.exceptions;

/**
 * Исключение, возникающее в случае, если бэк не смог идентифицировать клиента
 * @author niculichev
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class FailureIdentificationException extends BackLogicException
{
	public FailureIdentificationException()
    {
	    super();
    }

    public FailureIdentificationException(String message)
    {
        super(message);
    }

    public FailureIdentificationException(Throwable cause)
    {
        super(cause);
    }

    public FailureIdentificationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
