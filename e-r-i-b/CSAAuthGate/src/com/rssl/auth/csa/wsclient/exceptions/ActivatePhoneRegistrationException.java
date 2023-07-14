package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author osminin
 * @ created 03.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� - ������ ��������� ���������� (��������� �� ������ ��� ������� ����� ������ ���������� �� ������ ��������)
 */
public class ActivatePhoneRegistrationException extends BackLogicException
{
	/**
	 * ctor
	 * @param message ��������� �� ������
	 */
	public ActivatePhoneRegistrationException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause ������
	 */
	public ActivatePhoneRegistrationException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param cause ������
	 */
	public ActivatePhoneRegistrationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
