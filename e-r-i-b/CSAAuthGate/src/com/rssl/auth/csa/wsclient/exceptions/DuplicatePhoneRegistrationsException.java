package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author osminin
 * @ created 03.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� - ������ ��������� ��� ����������������
 */
public class DuplicatePhoneRegistrationsException extends BackLogicException
{
	/**
	 * ctor
	 * @param message ��������� �� ������
	 */
	public DuplicatePhoneRegistrationsException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause ������
	 */
	public DuplicatePhoneRegistrationsException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param cause ������
	 */
	public DuplicatePhoneRegistrationsException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
