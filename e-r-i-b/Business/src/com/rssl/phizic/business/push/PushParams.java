package com.rssl.phizic.business.push;

import com.rssl.phizic.messaging.mail.messagemaking.push.PushPrivacyType;
import com.rssl.phizic.messaging.mail.messagemaking.push.PushPublicityType;

/**
 * Часть шаблона push-сообщения. Создана чтоб каждый раз не вытаскивать весь шаблон целиком.
 * @author basharin
 * @ created 15.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PushParams
{
	private Long     id;
	private String   key;
	private int typeCode;
	private PushPrivacyType privacyType;
	private PushPublicityType publicityType;
	private boolean smsBackup;

	public boolean isSmsBackup()
	{
		return smsBackup;
	}

	public void setSmsBackup(boolean smsBackup)
	{
		this.smsBackup = smsBackup;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public int getTypeCode()
	{
		return typeCode;
	}

	public void setTypeCode(int typeCode)
	{
		this.typeCode = typeCode;
	}

	public PushPrivacyType getPrivacyType()
	{
		return privacyType;
	}

	public void setPrivacyType(PushPrivacyType privacyType)
	{
		this.privacyType = privacyType;
	}

	public PushPublicityType getPublicityType()
	{
		return publicityType;
	}

	public void setPublicityType(PushPublicityType publicityType)
	{
		this.publicityType = publicityType;
	}

}
