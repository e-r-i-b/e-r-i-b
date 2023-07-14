package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * ���������� ������ �����������
 *
 * @author khudyakov
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationErrorException extends BusinessLogicException
{
	private static final String MESSAGE = "� ���������, ������ ����������� �� ����� ���� ���������. ����������, ��������� ������� �����.";

	public RegistrationErrorException()
	{
		super(MESSAGE);
	}

	public RegistrationErrorException(String message)
	{
		super(message);
	}

	public RegistrationErrorException(Throwable cause)
	{
		super(cause);
	}

	public RegistrationErrorException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
