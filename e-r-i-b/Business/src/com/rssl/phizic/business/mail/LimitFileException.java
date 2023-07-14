package com.rssl.phizic.business.mail;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author kligina
 * @ created 05.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class LimitFileException extends BusinessLogicException
{
	/**
	 * конструктор по сообщению
	 * @param message
	 */
	public LimitFileException(String message)
    {
        super(message);
    }

    public LimitFileException(Throwable cause)
    {
        super(cause);
    }

	public LimitFileException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
