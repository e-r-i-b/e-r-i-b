package com.rssl.auth.csa.wsclient.exceptions;

/**
 * ����������, �����������, ���� ����� ��� �����
 * @author niculichev
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoginAlreadyRegisteredException extends BackLogicException
{
	public LoginAlreadyRegisteredException()
    {
	    super();
    }

	public LoginAlreadyRegisteredException(String message)
	{
		super(message);
	}
}
