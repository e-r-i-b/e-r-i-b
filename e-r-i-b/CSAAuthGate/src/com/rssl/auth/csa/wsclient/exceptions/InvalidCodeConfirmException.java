package com.rssl.auth.csa.wsclient.exceptions;

import com.rssl.phizic.common.types.ConfirmStrategyType;

/**
 * ���������� ��� �������� ���� ������������
 * @author niculichev
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class InvalidCodeConfirmException extends BackLogicException
{
	private final ConfirmStrategyType confirmStrategyType;
	private final Long attempts;
	private final Long time;

	/**
	 * �����������
	 * @param confirmStrategyType ��� �������������
	 * @param attempts ���������� ���������� �������
	 * @param time ���������� ����� �������������
	 */
	public InvalidCodeConfirmException(ConfirmStrategyType confirmStrategyType, Long attempts, Long time)
	{
		this.confirmStrategyType = confirmStrategyType;
		this.attempts = attempts;
		this.time = time;
	}

	protected InvalidCodeConfirmException(InvalidCodeConfirmException cause)
	{
		super(cause);
		this.confirmStrategyType = cause.getConfirmStrategyType();
		this.attempts = cause.getAttempts();
		this.time = cause.getTime();
	}

	/**
	 * @return ��� �������������
	 */
	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	/**
	 * @return ���������� �������
	 */
	public Long getAttempts()
	{
		return attempts;
	}

	/**
	 * @return ���������� �����
	 */
	public Long getTime()
	{
		return time;
	}
}
