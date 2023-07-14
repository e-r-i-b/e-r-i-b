package com.rssl.phizic.security;

/**
 * ���������� �������� ��� �������� ����� ��� ������. ������� ��� ���������� ������� ����������� ����� ���� ������ �� ��������� ������.
 * @author basharin
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public class SmsErrorLogicException extends SecurityLogicException
{
	private long lastAttemptsCount;

	public SmsErrorLogicException(String message, long lastAttemptsCount)
	{
		super(message);
		this.lastAttemptsCount = lastAttemptsCount;
	}

	public long getLastAttemptsCount()
	{
		return lastAttemptsCount;
	}
}
