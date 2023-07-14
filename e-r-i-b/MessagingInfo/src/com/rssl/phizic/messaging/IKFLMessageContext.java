package com.rssl.phizic.messaging;

import com.rssl.phizic.business.persons.ActivePerson;

/**
 * @author Erkin
 * @ created 11.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class IKFLMessageContext
{
	private IKFLMessage message;

	private ActivePerson recipient;

	private String mobilePhone;

	private TranslitMode translitMode;

	///////////////////////////////////////////////////////////////////////////

	public IKFLMessage getMessage()
	{
		return message;
	}

	public void setMessage(IKFLMessage message)
	{
		this.message = message;
	}

	public ActivePerson getRecipient()
	{
		return recipient;
	}

	public void setRecipient(ActivePerson recipient)
	{
		this.recipient = recipient;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public TranslitMode getTranslitMode()
	{
		return translitMode;
	}

	public void setTranslitMode(TranslitMode translitMode)
	{
		this.translitMode = translitMode;
	}
}
