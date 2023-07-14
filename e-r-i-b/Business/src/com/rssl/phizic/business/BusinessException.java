package com.rssl.phizic.business;

import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * »сключение дл€ олицетворени€ ошибок приложени€
 * возникающих в бизнес-слое, но несв€занных с логикой.
 * User: Roshka
 * Date: 08.09.2005
 * Time: 14:04:49
 */
public class BusinessException extends SystemException
{
    public BusinessException(String message)
    {
        super(message);
    }

    public BusinessException(Throwable cause)
    {
        super(cause);
    }

	public BusinessException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
