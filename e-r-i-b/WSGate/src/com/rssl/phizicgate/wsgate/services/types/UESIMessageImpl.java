package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizic.gate.mobilebank.UESIMessage;

import java.util.Calendar;

/**
 * @author Jatsky
 * @ created 20.07.15
 * @ $Author$
 * @ $Revision$
 */

public class UESIMessageImpl implements UESIMessage
{
	private Long messageId;
	private Calendar messageTime;
	private String messageType;
	private String messageText;

	public Long getMessageId()
	{
		return messageId;
	}

	public void setMessageId(Long messageId)
	{
		this.messageId = messageId;
	}

	public Calendar getMessageTime()
	{
		return messageTime;
	}

	public void setMessageTime(Calendar messageTime)
	{
		this.messageTime = messageTime;
	}

	public String getMessageType()
	{
		return messageType;
	}

	public void setMessageType(String messageType)
	{
		this.messageType = messageType;
	}

	public String getMessageText()
	{
		return messageText;
	}

	public void setMessageText(String messageText)
	{
		this.messageText = messageText;
	}
}
