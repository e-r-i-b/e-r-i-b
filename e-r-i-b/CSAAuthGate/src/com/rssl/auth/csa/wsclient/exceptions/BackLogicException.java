package com.rssl.auth.csa.wsclient.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * Логическое исключение при отправке запроса в CSABack
 * @author niculichev
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class BackLogicException extends LogicException
{
	public static final String DEFAULT_ERROR_MESSAGE = "Операция временно недоступна, повторите попытку позже.";

	public BackLogicException()
    {
	    super(DEFAULT_ERROR_MESSAGE);
    }

    public BackLogicException(String message)
    {
        super(message);
    }

    public BackLogicException(Throwable cause)
    {
        super(cause);
    }

    public BackLogicException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
