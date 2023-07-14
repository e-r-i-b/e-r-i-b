package com.rssl.phizic.common.types.exceptions;

/**
 * @author komarov
 * @ created 07.08.2014
 * @ $Author$
 * @ $Revision$
 * ������ ��������������
 */
public class InvalidSessionException extends RuntimeException
{
	/**
	 * �����������
	 */
	public InvalidSessionException()
	{
		super();
	}

	/**
	 * @param message ���������
	 */
	public InvalidSessionException(String message)
	{
		super(message);
	}

	/**
	 * @param message ���������
	 * @param cause ������� ����������
	 */
	public InvalidSessionException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @param cause ������� ����������
	 */
	public InvalidSessionException(Throwable cause)
	{
		super(cause);
	}
}
