package com.rssl.phizic.gate.exceptions;

/**
 * @author Omeliyanchuk
 * @ created 20.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * Логическое ошибка, суть которой расскрыто в сообщении,
 * которая требует каких-то действий со стороны приложения для исправления
 */
public class RedirectGateLogicException extends GateLogicException
{
    public RedirectGateLogicException(String message)
    {
        super(message);
    }

    public RedirectGateLogicException(Throwable cause)
    {
        super(cause);
    }

    public RedirectGateLogicException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
