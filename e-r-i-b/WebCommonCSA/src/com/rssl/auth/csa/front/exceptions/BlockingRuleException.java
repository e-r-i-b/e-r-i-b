package com.rssl.auth.csa.front.exceptions;

/**
 * @author osminin
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� - ��������� ������� ����������, ���� � ����������� ���������
 */
public class BlockingRuleException extends InterruptStageException
{
	private static final String GLOBAL_KEY = "message.global.bloking.login";
	private static final String BLOCKING_RULE_KEY = "blocking.rule.message";
	private final String date;

	/**
	 * ctor
	 * @param message ��������� �� ������
	 */
	public BlockingRuleException(String message)
	{
		super(message);
		this.date = null;
	}

	/**
	 * ctor
	 * @param cause ������
	 */
	public BlockingRuleException(Throwable cause)
	{
		super(cause);
		this.date = null;
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param cause ������
	 */
	public BlockingRuleException(String message, Throwable cause)
	{
		super(message, cause);
		this.date = null;
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param date ���� ������ ����������
	 * @param cause ������
	 */
	public BlockingRuleException(String message, String date, Throwable cause)
	{
		super(message, cause);
		this.date = date;
	}

	/**
	 * @return ���� ������ ����������
	 */
	public String getDate()
	{
		return date;
	}

	/**
	 * @return ���� ���������
	 */
	public String getErrorKey()
	{
		return date == null ? GLOBAL_KEY : BLOCKING_RULE_KEY;
	}
}
