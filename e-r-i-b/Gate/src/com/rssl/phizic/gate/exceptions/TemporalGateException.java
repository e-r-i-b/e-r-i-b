package com.rssl.phizic.gate.exceptions;

/**
 * @author Omeliyanchuk
 * @ created 20.12.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������ � �����, �.�. �������� ����� ���� ������ ��������� ��������.
 */
public class TemporalGateException extends GateException
{
	public TemporalGateException()
	{
		super();
	}

	public TemporalGateException(String message)
    {
        super(message);
    }

    public TemporalGateException(Throwable cause)
    {
        super(cause);
    }

    public TemporalGateException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
