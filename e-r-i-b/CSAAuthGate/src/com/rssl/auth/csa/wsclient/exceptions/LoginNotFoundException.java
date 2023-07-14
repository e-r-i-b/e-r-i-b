package com.rssl.auth.csa.wsclient.exceptions;

/**
 * ���������, ����������� � ������, ���� ����� �� ������
 * @author niculichev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoginNotFoundException extends BackLogicException
{
	public LoginNotFoundException()
    {
	    super();
    }

    public LoginNotFoundException(String message)
    {
        super(message);
    }

    public LoginNotFoundException(Throwable cause)
    {
        super(cause);
    }

    public LoginNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
