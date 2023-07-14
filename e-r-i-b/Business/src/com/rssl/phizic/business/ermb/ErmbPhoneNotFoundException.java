package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * User: Moshenko
 * Date: 20.06.2013
 * Time: 13:57:58
 * Если не нашли телефон.
 */
public class ErmbPhoneNotFoundException  extends BusinessLogicException
{
	public ErmbPhoneNotFoundException(String message)
    {
        super(message);
    }

    public ErmbPhoneNotFoundException(Throwable cause)
    {
        super(cause == null ? null : cause.getMessage(), cause);
    }

    public ErmbPhoneNotFoundException(String message, Throwable cause)
    {
	     super(message, cause);
    }

}
