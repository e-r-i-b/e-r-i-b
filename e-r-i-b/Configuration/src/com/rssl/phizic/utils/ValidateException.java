package com.rssl.phizic.utils;

/**
 * @author Omeliyanchuk
 * @ created 17.11.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Исключение валидации документа
 */
public class ValidateException extends Exception
{
    public ValidateException(String message)
    {
        super(message);
    }

    public ValidateException(Throwable cause)
    {
        super(cause);
    }

	public ValidateException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
