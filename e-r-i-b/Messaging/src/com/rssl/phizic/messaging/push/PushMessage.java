package com.rssl.phizic.messaging.push;

import com.rssl.phizic.logging.push.PushEventType;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.mail.messagemaking.push.PushPrivacyType;
import com.rssl.phizic.messaging.mail.messagemaking.push.PushPublicityType;

/**
 * шаблон push-сообщения
 * @author basharin
 * @ created 09.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PushMessage extends IKFLMessage
{
	private String shortMessage;
	private String smsMessage;
	private PushEventType typeCode;
	private PushPrivacyType privacyType;
	private PushPublicityType publicityType;
	private boolean smsBackup;

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

	public String getShortMessage()
	{
		return shortMessage;
	}

	public void setShortMessage(String shortMessage)
	{
		this.shortMessage = shortMessage;
	}

	public String getFullMessage()
	{
		return getText();
	}

	public void setFullMessage(String fullMessage)
	{
		setText(fullMessage);
	}

	public String getSmsMessage()
	{
		return smsMessage;
	}

	public void setSmsMessage(String smsMessage)
	{
		this.smsMessage = smsMessage;
	}


	public PushEventType getTypeCode()
	{
		return typeCode;
	}

	public void setTypeCode(PushEventType typeCode)
	{
		this.typeCode = typeCode;
	}

}
