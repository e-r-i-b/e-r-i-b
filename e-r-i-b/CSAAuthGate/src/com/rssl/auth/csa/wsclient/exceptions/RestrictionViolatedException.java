package com.rssl.auth.csa.wsclient.exceptions;

/**
 * ����������, ����������� ��� ��������� ������ ����������� �� ���������� �������� � ����
 * @author niculichev
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class RestrictionViolatedException extends BackLogicException
{
	public RestrictionViolatedException()
    {
	    super();
    }

    public RestrictionViolatedException(String message)
    {
        super(message);
    }

    public RestrictionViolatedException(Throwable cause)
    {
        super(cause);
    }

    public RestrictionViolatedException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
