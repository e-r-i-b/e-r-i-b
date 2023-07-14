package com.rssl.phizic.limits.exceptions;

/**
 * @author osminin
 * @ created 22.01.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� - ���������� �� �������
 */
public class TransactionNotFoundException extends Exception
{
	/**
	 * ctor
	 */
	public TransactionNotFoundException()
	{}

	/**
	 * ctor
	 * @param cause �������
	 */
	public TransactionNotFoundException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param error ��������� �� ������
	 */
	public TransactionNotFoundException(String error)
	{
		super(error);
	}

	/**
	 * ctor
	 * @param error ��������� �� ������
	 * @param cause �������
	 */
	public TransactionNotFoundException(String error, Throwable cause)
	{
		super(error, cause);
	}
}
