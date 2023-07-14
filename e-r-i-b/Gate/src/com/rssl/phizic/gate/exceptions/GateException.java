package com.rssl.phizic.gate.exceptions;
import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * ���������� <code>GateException</code> � ��� ������� ���������
 * �� ������������� ������, ��������� � �����, ������� �� ������� � �������
 * User: Kidyaev
 * Date: 03.10.2005
 * Time: 18:05:36
 */
public class GateException extends SystemException
{
    public GateException()
    {
    }

    public GateException(String message)
    {
        super(message);
    }

    public GateException(Throwable cause)
    {
        super(cause);
    }

    public GateException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
