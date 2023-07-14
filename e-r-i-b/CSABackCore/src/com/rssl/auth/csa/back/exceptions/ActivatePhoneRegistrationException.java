package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author osminin
 * @ created 03.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� - ������ ��������� ���������� (��������� �� ������ ��� ������� ����� ������ ���������� �� ������ ��������)
 */
public class ActivatePhoneRegistrationException extends LogicException
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
