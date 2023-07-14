package com.rssl.auth.csa.back.servises.operations.confirmations;

/**
 * @author akrenev
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������������� ������� �������
 */

public class IPasConfirmationInfo implements ConfirmationInfo
{
	private String passwordNo;
	private String receiptNo;
	private Integer passwordsLeft;
	private Integer lastAtempts;

	/**
	 * �����������
	 * @param passwordNo ����� ������
	 * @param receiptNo ����� �����
	 * @param passwordsLeft ������� ��������
	 * @param lastAtempts ���������� ���������� �������
	 */
	public IPasConfirmationInfo(String passwordNo, String receiptNo, Integer passwordsLeft, Integer lastAtempts)
	{
		this.passwordNo = passwordNo;
		this.receiptNo = receiptNo;
		this.passwordsLeft = passwordsLeft;
		this.lastAtempts = lastAtempts;
	}

	/**
	 * @return ����� ������ �� ����
	 */
	public String getPasswordNo()
	{
		return passwordNo;
	}

	/**
	 * @return ����� ����
	 */
	public String getReceiptNo()
	{
		return receiptNo;
	}

	/**
	 * @return ���������� ���������� ������� �� ����
	 */
	public Integer getPasswordsLeft()
	{
		return passwordsLeft;
	}

	/**
	 * @return ���������� ���������� ������� (�������� ������ � ������ ���������� ������)
	 */
	public Integer getLastAtempts()
	{
		return lastAtempts;
	}
}
