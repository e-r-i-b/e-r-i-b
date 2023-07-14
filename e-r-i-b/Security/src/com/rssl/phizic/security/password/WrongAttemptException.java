package com.rssl.phizic.security.password;

/**
 * @author Evgrafov
 * @ created 23.01.2006
 * @ $Author: emakarov $
 * @ $Revision: 8089 $
 */

public class WrongAttemptException extends PasswordValidationException
{
	private final long attemptsLeft;
	private boolean needMessage;

	/**
	 * @param attemptsLeft ���������� ���������� �������
	 */
	public WrongAttemptException(long attemptsLeft)
	{
		super("�������� ������. �������� �������:"+attemptsLeft);
		this.attemptsLeft = attemptsLeft;
		this.needMessage  = true;
	}

	/**
	 * @return ���������� ���������� �������
	 */
	public long getAttemptsLeft()
	{
		return attemptsLeft;
	}

	/**
	 * @param attemptsLeft ���������� ���������� �������
	 * @param needMessage �������� �� ��������� �� ���������� ��������.
	 */
	public WrongAttemptException(long attemptsLeft, boolean needMessage)
	{
		super("�������� ������. �������� �������:"+attemptsLeft);
		this.attemptsLeft = attemptsLeft;
		this.needMessage = needMessage;
	}

	public boolean isNeedMessage()
	{
		return needMessage;
	}
}