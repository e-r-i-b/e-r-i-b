package com.rssl.auth.csa.wsclient.exceptions;

/**
 * ����������, �������������� � ���, ��� ��������� �������� � ��������� � ������ ��������� ����������.
 * @author niculichev
 * @ created 25.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class IllegalOperationStateException extends BackLogicException
{
	public IllegalOperationStateException()
	{
		super();
	}

	public IllegalOperationStateException(Throwable cause)
	{
		super(cause.getMessage(), cause);
	}
}
