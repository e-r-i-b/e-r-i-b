package com.rssl.phizic.gate.mobilebank;

import java.util.Calendar;

/**
 * @author Gulov
 * @ created 09.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����������� �� ��� �������. ������������ �� �������� ��.
 */
public class DisconnectedPhoneResult
{
	/**
	 * ������������� ������
	 */
	private int id;

	/**
	 * ����� ��������, � ������� ��� ������� ������������ � 11 ������� �������. ������ ���������� �� 7.
	 */
	private String phoneNumber;

	/**
	 * ������� ���������� ��������� ���
	 * 1 � ����� ��������� ������ MSISDN
	 * 2 � �������� ������� �� ���������� ���
	 * 3 � ����������� ��������� �������� � ���
	 * 4 � ����� ��������� MSISDN c ���.���� �� ���.����
	 * 5 � ����� ��������� MSISDN � ���. ���� �� ��.���� � � ��.���� �� ���.����
	 */
	private PhoneDisconnectionReason reason;
	/**
	 * �������� ������ �������� ���������� �� ����������:
	 * 1 � ������ �������
	 * 2 � ������ ���
	 */
	private int disconnectedPhoneSource;

	/**
	 * ���� �������� �� ��� ������ � ������� dbo.ERMB_DisconnectedPhones
	 */
	private Calendar createdAt;

	/**
	 * ����� ���������� �� ���������� ����� ��������� �� ���. �� ��� ���� ���������� ��� ���������� ����.
	 */
	private Calendar receivedAt;

	/**
	 * 1 � ���������� ����
	 * 0 � �� ���������� ����
	 * ��������������� ����.
	 */
	private boolean state;

	/**
	 * ����� ��������� ������ ����. ��������������� ���� ��� ��������� �������.
	 */
	private Calendar processedTime;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public PhoneDisconnectionReason getReason()
	{
		return reason;
	}

	public void setReason(PhoneDisconnectionReason reason)
	{
		this.reason = reason;
	}

	public void setReason(String reason)
	{
		if(reason == null || reason.trim().length() == 0)
			return;
		this.reason = Enum.valueOf(PhoneDisconnectionReason.class, reason);
	}

	public int getDisconnectedPhoneSource()
	{
		return disconnectedPhoneSource;
	}

	public void setDisconnectedPhoneSource(int disconnectedPhoneSource)
	{
		this.disconnectedPhoneSource = disconnectedPhoneSource;
	}

	public Calendar getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt)
	{
		this.createdAt = createdAt;
	}

	public Calendar getReceivedAt()
	{
		return receivedAt;
	}

	public void setReceivedAt(Calendar receivedAt)
	{
		this.receivedAt = receivedAt;
	}

	public boolean isState()
	{
		return state;
	}

	public void setState(boolean state)
	{
		this.state = state;
	}

	public Calendar getProcessedTime()
	{
		return processedTime;
	}

	public void setProcessedTime(Calendar processedTime)
	{
		this.processedTime = processedTime;
	}
}
