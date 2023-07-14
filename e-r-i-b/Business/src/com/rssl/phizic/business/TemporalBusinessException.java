package com.rssl.phizic.business;

/**
 * @author Omeliyanchuk
 * @ created 20.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * Временная ошибка приложения
 */
public class TemporalBusinessException extends BusinessException
{
    public TemporalBusinessException(String message)
    {
        super(message);
    }

    public TemporalBusinessException(Throwable cause)
    {
        super(cause);
    }

	public TemporalBusinessException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
