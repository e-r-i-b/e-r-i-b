package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author gladishev
 * @ created 16.12.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� - �� ������� ���������� � ������������
 */
public class UserNotFoundException extends BackException
{
	/**
	 * ctor
	 * @param message ��������� �� ������
	 */
	public UserNotFoundException(String message)
	{
		super(message);
	}
}
