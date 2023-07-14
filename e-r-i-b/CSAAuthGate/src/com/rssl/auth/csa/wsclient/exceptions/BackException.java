package com.rssl.auth.csa.wsclient.exceptions;

import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * Системное сключение при отправке запроса в CSABack
 * @author niculichev
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class BackException extends SystemException
{
	public BackException()
    {
    }

    public BackException(String message)
    {
        super(message);
    }

    public BackException(Throwable cause)
    {
        super(cause);
    }

    public BackException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
