package com.rssl.auth.csa.back.servises.operations.confirmations;

/**
 * @author akrenev
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������������� ���-�������
 */

public class SMSConfirmationInfo implements ConfirmationInfo
{
	private long lastAtempts;
	private long confirmTimeOut;

	/**
	 * �����������
	 * @param lastAtempts ���������� ���������� �������
	 * @param confirmTimeOut ���������� ����� ����� ������
	 */
	public SMSConfirmationInfo(long lastAtempts, long confirmTimeOut)
	{
		this.lastAtempts = lastAtempts;
		this.confirmTimeOut = confirmTimeOut;
	}

	/**
	 * @return ���������� ���������� �������
	 */
	public long getLastAtempts()
	{
		return lastAtempts;
	}

	/**
	 * @return ���������� ����� ����� ������
	 */
	public long getConfirmTimeOut()
	{
		return confirmTimeOut;
	}
}
