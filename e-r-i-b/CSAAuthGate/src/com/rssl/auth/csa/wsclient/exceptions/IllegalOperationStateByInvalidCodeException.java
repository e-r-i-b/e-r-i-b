package com.rssl.auth.csa.wsclient.exceptions;

/**
 * ����������, �������������� � ���, ��� ��������� �������� � ��������� � ������ ��������� ����������
 * ���� ��������� ����������� ���������� ����� ������������� ���� �������������
 * @author niculichev
 * @ created 18.11.13
 * @ $Author$
 * @ $Revision$
 */
public class IllegalOperationStateByInvalidCodeException extends IllegalOperationStateException
{
	public IllegalOperationStateByInvalidCodeException()
	{
		super();
	}

	public IllegalOperationStateByInvalidCodeException(Throwable cause)
	{
		super(cause);
	}
}
