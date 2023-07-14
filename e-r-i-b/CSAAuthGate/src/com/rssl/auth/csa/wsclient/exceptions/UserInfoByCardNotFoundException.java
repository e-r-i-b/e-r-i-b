package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author osminin
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� - ���������� � ������������ �� ������ ����� �� �������
 */
public class UserInfoByCardNotFoundException extends BackLogicException
{
	/**
	 * ctor
	 * @param message ��������� �� ������
	 */
	public UserInfoByCardNotFoundException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause ������
	 */
	public UserInfoByCardNotFoundException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param cause ������
	 */
	public UserInfoByCardNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
