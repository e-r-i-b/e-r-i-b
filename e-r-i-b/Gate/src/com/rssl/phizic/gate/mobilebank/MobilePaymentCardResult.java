package com.rssl.phizic.gate.mobilebank;

/**
 * @author Gulov
 * @ created 23.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� �� ������ ����������� ����� �� ������ �������� � �������� (P2P).
 */
public class MobilePaymentCardResult
{
	/**
	 * �� ������� (���������� �� ��������� dbo.ERMB_MobilePaymentCardRequests)
	 */
	private int id;

	/**
	 * ����� �����-���������� ��������. ����� ���� ������, ���� �����-���������� ���������� �� �������.
	 */
	private String cardNumber;

	/**
	 * ��� �������� � ������ ����� ������� ��������� �����-���������� �������. ����� ���� ������.
	 * ���������� ��������� ���� ���� ����� ��� ���������� � �������� ��������. ����� ����� ������ ����� ������� �� �����������.
	 */
	private String clientName;

	/**
	 * ��� ��������������� ��������� ����������� �����-����������.
	 * 0 � ������ ���.
	 * -1 � ���������� ������� �� ����������� ������� ����
	 * -2 � ����� ���������� �� ����� ���� ����������
	 * -3 � ��� �����, �������� ���� � ������� �� ������ WAY4
	 * -4 � �� ����� �������
	 * -5 � �� ����� ��� ���������
	 */
	private int resultCode;

	/**
	 * ������������ �����, �� ���������� ����, ���������� resultCode
	 */
	private String comment;

	//��� ���-�������
	public MobilePaymentCardResult()
	{
	}

	public MobilePaymentCardResult(int id, String cardNumber, String clientName, int resultCode, String comment)
	{
		this.id = id;
		this.cardNumber = cardNumber;
		this.clientName = clientName;
		this.resultCode = resultCode;
		this.comment = comment;
	}

	public int getId()
	{
		return id;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public String getClientName()
	{
		return clientName;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public String getComment()
	{
		return comment;
	}
}
