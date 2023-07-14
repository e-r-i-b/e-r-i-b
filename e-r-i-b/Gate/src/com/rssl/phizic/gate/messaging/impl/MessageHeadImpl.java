package com.rssl.phizic.gate.messaging.impl;

import com.rssl.phizic.gate.messaging.MessageHead;

import java.util.Calendar;

/**
 * @author niculichev
 * @ created 19.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class MessageHeadImpl implements MessageHead
{
	private String messageId;
	private Calendar messageDate;
	private String fromAbonent;

	private String parentMessageId;
	private String parentMessageDate;
	private String parentFromAbonent;

	public MessageHeadImpl()
	{
	}

	public MessageHeadImpl(String messageId, Calendar messageDate, String fromAbonent, String parentMessageId, String parentMessageDate,  String parentFromAbonent)
	{
		this.messageId = messageId;
		this.messageDate = messageDate;
		this.fromAbonent = fromAbonent;
		this.parentMessageId = parentMessageId;
		this.parentMessageDate = parentMessageDate;
		this.parentFromAbonent = parentFromAbonent;
	}

	public String getMessageId()
	{
		return messageId;
	}

	public void setMessageId(String messageId)
	{
		this.messageId = messageId;
	}

	public Calendar getMessageDate()
	{
		return messageDate;
	}

	public void setMessageDate(Calendar messageDate)
	{
		this.messageDate = messageDate;
	}

	public String getFromAbonent()
	{
		return fromAbonent;
	}

	public void setFromAbonent(String fromAbonent)
	{
		this.fromAbonent = fromAbonent;
	}

	public String getParentMessageId()
	{
		return parentMessageId;
	}

	public void setParentMessageId(String parrentMessageId)
	{
		this.parentMessageId = parrentMessageId;
	}

	public String getParentMessageDate()
	{
		return parentMessageDate;
	}

	public void setParentMessageDate(String parentMessageDate)
	{
		this.parentMessageDate = parentMessageDate;
	}

	public String getParentFromAbonent()
	{
		return parentFromAbonent;
	}

	public void setParentFromAbonent(String parentFromAbonent)
	{
		this.parentFromAbonent = parentFromAbonent;
	}
}
