package com.rssl.phizic.business.access;

/**
 * ���������, ���� ������������ �������� ������ � �������������� �������
 * @author gladishev
 * @ created 18.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class AccessException extends SecurityException
{
	public AccessException(String message)
    {
        super(message);
    }

    public AccessException(Throwable cause)
    {
	    super(cause);
    }

    public AccessException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
