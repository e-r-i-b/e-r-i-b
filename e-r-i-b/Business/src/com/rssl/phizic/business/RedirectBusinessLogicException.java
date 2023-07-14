package com.rssl.phizic.business;

/**
 * @author Omeliyanchuk
 * @ created 20.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ошбика в бизнес-логике приложения, исправление
 * которой требует дополнительных действий со стороны приложения
 */
public class RedirectBusinessLogicException extends BusinessLogicException
{
    public RedirectBusinessLogicException(String message)
    {
        super(message);
    }

    public RedirectBusinessLogicException(Throwable cause)
    {
        super(cause == null ? null : cause.getMessage(), cause);
    }

    public RedirectBusinessLogicException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
