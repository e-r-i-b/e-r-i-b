package com.rssl.phizic.gate.exceptions;

/**
 * @author Omeliyanchuk
 * @ created 20.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� ������, ���� ������� ��������� � ���������,
 * ������� ������� �����-�� �������� �� ������� ���������� ��� �����������
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
