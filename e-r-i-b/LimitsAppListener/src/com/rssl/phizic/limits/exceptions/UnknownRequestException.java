package com.rssl.phizic.limits.exceptions;

/**
 * @author osminin
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� - ����������� ��� �������
 */
public class UnknownRequestException extends Exception
{
	/**
	 * ctor
	 */
	public UnknownRequestException()
	{}

	/**
	 * ctor
	 * @param cause �������
	 */
	public UnknownRequestException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param error ��������� �� ������
	 */
	public UnknownRequestException(String error)
	{
		super(error);
	}

	/**
	 * ctor
	 * @param error ��������� �� ������
	 * @param cause �������
	 */
	public UnknownRequestException(String error, Throwable cause)
	{
		super(error, cause);
	}
}
