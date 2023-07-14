package com.rssl.phizic.gate.mail;

/**
 * @author mihaylov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� ������������� ��������� ������ ����������
 */
public class IncomeMailListEntity extends MailListEntityBase
{
	private String senderFIO;
	private Long senderId;

	/**
	 * @return ��� �����������
	 */
	public String getSenderFIO()
	{
		return senderFIO;
	}

	/**
	 * ���������� ��� ������������
	 * @param senderFIO - ��� ������������
	 */
	public void setSenderFIO(String senderFIO)
	{
		this.senderFIO = senderFIO;
	}

	/**
	 * @return ������������� ����������� � �����
	 */
	public Long getSenderId()
	{
		return senderId;
	}

	/**
	 * ���������� ������������� ����������� � �����
	 * @param senderId - ������������� ����������� � �����
	 */
	public void setSenderId(Long senderId)
	{
		this.senderId = senderId;
	}
}
