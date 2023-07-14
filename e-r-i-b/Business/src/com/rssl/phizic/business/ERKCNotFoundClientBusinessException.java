package com.rssl.phizic.business;

/**
 * ���������� ��� ����������� ������ ������� � ���������� � �������, � ������� �������� ��������� ����
 * @author lepihina
 * @ created 22.08.13
 * @ $Author$
 * @ $Revision$
 */
public class ERKCNotFoundClientBusinessException extends BusinessException
{
	public ERKCNotFoundClientBusinessException(String message)
	{
		super(message);
	}

	public ERKCNotFoundClientBusinessException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ERKCNotFoundClientBusinessException(Throwable cause)
	{
		super(cause);
	}
}
