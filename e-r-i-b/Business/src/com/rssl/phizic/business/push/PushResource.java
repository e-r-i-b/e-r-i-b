package com.rssl.phizic.business.push;

import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.messaging.mail.messagemaking.push.PushPrivacyType;
import com.rssl.phizic.messaging.mail.messagemaking.push.PushPublicityType;

/**
 * шаблон push-сообщения
 * @author basharin
 * @ created 04.10.13
 * @ $Author$
 * @ $Revision$
 *
 * @see PushConfirmationResource
 * @see PushInformingResource
 */

public abstract class PushResource extends MessageResource
{
	private String shortMessage;
	private Long priority;
	private int typeCode;
	private PushPrivacyType privacyType;
	private PushPublicityType publicityType;
	private boolean smsBackup;
	private String attributes;

	public PushPublicityType getPublicityType()
	{
		return publicityType;
	}

	public void setPublicityType(PushPublicityType publicityType)
	{
		this.publicityType = publicityType;
	}

	public PushPrivacyType getPrivacyType()
	{
		return privacyType;
	}

	public void setPrivacyType(PushPrivacyType privacyType)
	{
		this.privacyType = privacyType;
	}

	public boolean isSmsBackup()
	{
		return smsBackup;
	}

	public void setSmsBackup(boolean smsBackup)
	{
		this.smsBackup = smsBackup;
	}

	public int getTypeCode()
	{
		return typeCode;
	}

	public void setTypeCode(int typeCode)
	{
		this.typeCode = typeCode;
	}

	public String getFullMessage()
	{
		return getText();
	}

	public void setFullMessage(String fullMessage)
	{
		setText(fullMessage);
	}

	public String getShortMessage()
	{
		return shortMessage;
	}

	public void setShortMessage(String shortMessage)
	{
		this.shortMessage = shortMessage;
	}

	public Long getPriority()
	{
		return priority;
	}

	public void setPriority(Long priority)
	{
		this.priority = priority;
	}

	public String getAttributes()
	{
		return attributes;
	}

	public void setAttributes(String attributes)
	{
		this.attributes = attributes;
	}
}
