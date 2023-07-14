package com.rssl.phizic.gate.exceptions;
import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * Исключение <code>GateLogicException</code> и его потомки указывают
 * на возникновение ошибки в логике шлюза
 * User: Kidyaev
 * Date: 03.10.2005
 * Time: 18:05:36
 */
public class GateLogicException extends LogicException
{
    public GateLogicException()
    {
    }

    public GateLogicException(String message)
    {
        super(message);
    }

    public GateLogicException(Throwable cause)
    {
        super(cause);
    }

    public GateLogicException(String message, Throwable cause)
    {
        super(message, cause);
    }

	public GateLogicException(String message, String errorCode)
    {
        super(message, errorCode);
    }
}
