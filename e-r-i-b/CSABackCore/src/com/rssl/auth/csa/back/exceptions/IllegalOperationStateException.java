package com.rssl.auth.csa.back.exceptions;

/**
 * @author krenev
 * @ created 14.09.2012
 * @ $Author$
 * @ $Revision$
 * ����������, �������������� � ���, ��� ��������� �������� � ��������� � ������ ��������� ����������.
 * �.�. �������� �� ������������� ��� �������� ���������. ��������, ��������� ���������� ��� ������������� ��� ����������� ������. ��� ���������� ����������������.
 */
public class IllegalOperationStateException extends IllegalStateException
{
	public IllegalOperationStateException(String message)
	{
		super(message);
	}
}
