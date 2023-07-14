package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Evgrafov
 * @ created 03.01.2007
 * @ $Author: krenev $
 * @ $Revision: 4239 $
 */

public class PasswordCardWrongAttemptException extends SecurityLogicException
{
	private final long attemptsLeft;

	/**
	 * @param attemptsLeft ���������� ���������� �������
	 */
	public PasswordCardWrongAttemptException(long attemptsLeft)
	{
		super("�������� ������. �������� �������:"+attemptsLeft);
		this.attemptsLeft = attemptsLeft;
	}

	/**
	 * @return ���������� ���������� �������
	 */
	public long getAttemptsLeft()
	{
		return attemptsLeft;
	}
}