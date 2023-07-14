package com.rssl.phizic.gate.mail;

/**
 * @author mihaylov
 * @ created 30.05.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� ������������� ���������� ������ ����������
 */
public class RemovedMailListEntity extends MailListEntityBase
{
	private String recipientName;
	private String directionDescription;

	/**
	 * @return �������� ���������� ������.
	 * ���� ������ ������������ ��������, �� �������� ������������ � �������� �������� ������.
	 * ���� �����������, �� ��� �������.
	 */
	public String getRecipientName()
	{
		return recipientName;
	}

	/**
	 * ���������� ������������ ���������� ������
	 * @param recipientName - ������������ ���������� ������
	 */
	public void setRecipientName(String recipientName)
	{
		this.recipientName = recipientName;
	}

	/**
	 * @return �������� ����������� ������(������������/����������)
	 */
	public String getDirectionDescription()
	{
		return directionDescription;
	}

	/**
	 * ���������� �������� ����������� ������(������������/����������)
	 * @param directionDescription - �������� ����������� ������(������������/����������)
	 */
	public void setDirectionDescription(String directionDescription)
	{
		this.directionDescription = directionDescription;
	}
}
