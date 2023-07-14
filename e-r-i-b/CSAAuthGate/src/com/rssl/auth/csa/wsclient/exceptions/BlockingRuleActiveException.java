package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author osminin
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ����������, ����������, ��� �������� ������� ���������� - ��������� ����������� �� ���� � �����������
 */
@SuppressWarnings("UnusedDeclaration")
public class BlockingRuleActiveException extends RestrictionViolatedException
{
	private final String date;
	/**
	 * ctor
	 * @param message ��������� �� ������
	 */
	public BlockingRuleActiveException(String message)
	{
		super(message);
		this.date = null;
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param date ���� ������ ����������
	 */
	public BlockingRuleActiveException(String message, String date)
	{
		super(message);
		this.date = date;
	}


	/**
	 * ctor
	 * @param cause ������
	 */
	public BlockingRuleActiveException(Exception cause)
	{
		super(cause);
		this.date = null;
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param cause ������
	 */
	public BlockingRuleActiveException(String message, Exception cause)
	{
		super(message, cause);
		this.date = null;
	}

	/**
	 * @return ���� ������ ����������
	 */
	public String getDate()
	{
		return date;
	}
}
