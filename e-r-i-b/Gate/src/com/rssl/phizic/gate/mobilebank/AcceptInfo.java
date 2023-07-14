package com.rssl.phizic.gate.mobilebank;

import java.util.Calendar;

/**
 * ��� � ����������� �� ������� �������� -������.
 *
 * @author bogdanov
 * @ created 24.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class AcceptInfo
{
	/**
	 * ������������� �������.
	 */
	private Long messageId;

	/**
	 * ���� � ����� ��������� �������.
	 */
	private Calendar receiptTime;

	public Long getMessageId()
	{
		return messageId;
	}

	public void setMessageId(Long messageId)
	{
		this.messageId = messageId;
	}

	public Calendar getReceiptTime()
	{
		return receiptTime;
	}

	public void setReceiptTime(Calendar receiptTime)
	{
		this.receiptTime = receiptTime;
	}
}
