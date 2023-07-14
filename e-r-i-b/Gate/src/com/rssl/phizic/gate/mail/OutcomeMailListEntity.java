package com.rssl.phizic.gate.mail;

/**
 * @author mihaylov
 * @ created 29.05.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� ������������� ���������� ������ ����������
 */
public class OutcomeMailListEntity extends MailListEntityBase
{
	private Long   recipientId;
	private String recipientFIO;

	/**
	 * @return ������������� ����������
	 */
	public Long getRecipientId()
	{
		return recipientId;
	}

	/**
	 * ���������� ������������� ����������
	 * @param recipientId - ������������� ����������
	 */
	public void setRecipientId(Long recipientId)
	{
		this.recipientId = recipientId;
	}

	/**
	 * @return ��� ����������
	 */
	public String getRecipientFIO()
	{
		return recipientFIO;
	}

	/**
	 * ���������� ��� ����������
	 * @param recipientFIO - ��� ����������
	 */
	public void setRecipientFIO(String recipientFIO)
	{
		this.recipientFIO = recipientFIO;
	}
}
