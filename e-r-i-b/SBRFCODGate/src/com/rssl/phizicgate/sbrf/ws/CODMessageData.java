package com.rssl.phizicgate.sbrf.ws;

import com.rssl.phizgate.common.messaging.StringMessageData;

import java.util.Calendar;

/**
 * ������ ��������� ���, ���
 *
 * @author Roshka
 * @ created 05.10.2006
 * @ $Author$
 * @ $Revision$
 */

public class CODMessageData extends StringMessageData
{
	private Calendar date;
	private String abonent;
	private String   parentMessageId;
	private Calendar parentMessageDate;
	private String   parentFromAbonent;

	/**
	 * @return ������� ������������� ���������
	 */
	public String getParentFromAbonent()
	{
		return parentFromAbonent;
	}

	public void setParentFromAbonent(String parentFromAbonent)
	{
		this.parentFromAbonent = parentFromAbonent;
	}

	/**
	 * @return ���� ������������� ���������
	 */
	public Calendar getParentMessageDate()
	{
		return parentMessageDate;
	}

	public void setParentMessageDate(Calendar parentMessageDate)
	{
		this.parentMessageDate = parentMessageDate;
	}

	/**
	 * @return ������������� ������������� ���������
	 */
	public String getParentMessageId()
	{
		return parentMessageId;
	}

	public void setParentMessageId(String parentMessageId)
	{
		this.parentMessageId = parentMessageId;
	}

	public String getAbonent()
	{
		return abonent;
	}

	public void setAbonent(String abonent)
	{
		this.abonent = abonent;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}
}