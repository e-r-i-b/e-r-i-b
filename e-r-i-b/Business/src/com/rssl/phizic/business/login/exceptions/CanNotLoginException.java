package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * ���������� ������������� ����� � �������
 *
 * @author khudyakov
 * @ created 10.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class CanNotLoginException extends BusinessLogicException
{
	private static final String MESSAGE = "�� �� ������ ����� � ������� ��������� ������. ����������, ���������� � ����.";

	public CanNotLoginException()
	{
		this(MESSAGE);
	}

	public CanNotLoginException(String message)
	{
		super(message);
	}

	public CanNotLoginException(Throwable cause)
	{
		super(cause);
	}

	public CanNotLoginException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
