package com.rssl.auth.csa.back.integration.ipas;

/**
 * @author akrenev
 * @ created 07.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ������������� ����������� �������
 */

public class IPasPasswordInformation
{
	private String SID;
	private String passwordNo;
	private String receiptNo;
	private Integer passwordsLeft;
	private Integer lastAtempts;

	/**
	 * ����������� (��� ���������)
	 */
	public IPasPasswordInformation()
	{}

	/**
	 * �����������
	 * @param SID ���
	 * @param passwordNo ����� ������
	 * @param receiptNo ����� �����
	 * @param passwordsLeft ������� ��������
	 */
	public IPasPasswordInformation(String SID, String passwordNo, String receiptNo, Integer passwordsLeft)
	{
		this.SID = SID;
		this.passwordNo = passwordNo;
		this.receiptNo = receiptNo;
		this.passwordsLeft = passwordsLeft;
	}

	/**
	 * @return ���
	 */
	public String getSID()
	{
		return SID;
	}

	/**
	 * ������ ��� (��� ���������)
	 * @param SID ���
	 */
	public void setSID(String SID)
	{
		this.SID = SID;
	}

	/**
	 * @return ����� ������ �� ����
	 */
	public String getPasswordNo()
	{
		return passwordNo;
	}

	/**
	 * ������ ����� ������ �� ���� (��� ���������)
	 * @param passwordNo ����� ������ �� ����
	 */
	public void setPasswordNo(String passwordNo)
	{
		this.passwordNo = passwordNo;
	}

	/**
	 * @return ����� ����
	 */
	public String getReceiptNo()
	{
		return receiptNo;
	}

	/**
	 * ������ ����� ���� (��� ���������)
	 * @param receiptNo ����� ����
	 */
	public void setReceiptNo(String receiptNo)
	{
		this.receiptNo = receiptNo;
	}

	/**
	 * @return ���������� ���������� ������� �� ����
	 */
	public Integer getPasswordsLeft()
	{
		return passwordsLeft;
	}

	/**
	 * ������ ���������� ���������� ������� �� ���� (��� ���������)
	 * @param passwordsLeft ���������� ���������� ������� �� ����
	 */
	public void setPasswordsLeft(Integer passwordsLeft)
	{
		this.passwordsLeft = passwordsLeft;
	}

	/**
	 * @return ���������� ���������� ������� (�������� ������ � ������ ���������� ������)
	 */
	public Integer getLastAtempts()
	{
		return lastAtempts;
	}

	/**
	 * ������ ���������� ���������� �������
	 * @param lastAtempts ���������� ���������� �������
	 */
	public void setLastAtempts(Integer lastAtempts)
	{
		this.lastAtempts = lastAtempts;
	}
}

