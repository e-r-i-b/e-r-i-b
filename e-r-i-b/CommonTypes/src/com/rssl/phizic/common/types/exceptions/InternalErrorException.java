package com.rssl.phizic.common.types.exceptions;

/**
 * @author Erkin
 * @ created 07.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����������� ����������.
 * ������ ������������� ��� ��������, ����������� ���������� ���������� ������� ������.
 * �� ������������ �������-������ �������������� ��������� (�.�. ����������������).
 * ������ ���� ����������� �� "������� ������" � �������� � ��������� ������.
 * ��� ������������ ���������� �������� ��� "�������� �������� ����������".
 * �������:
 * - ������ ����� � �����
 * - ������ � SQL-�������
 */
public class InternalErrorException extends RuntimeException
{
	/**
	 * ctor
	 */
	public InternalErrorException()
	{
	}

	/**
	 * ctor
	 * @param message
	 */
	public InternalErrorException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause
	 */
	public InternalErrorException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message
	 * @param cause
	 */
	public InternalErrorException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
