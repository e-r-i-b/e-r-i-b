package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * @author tisov
 * @ created 26.02.15
 * @ $Author$
 * @ $Revision$
 * Структура для инициализации сендера успешного входа
 */
public class OnLoginSenderInitializationData extends SenderInitializationByEventData
{
	private String cardNumbers;     //список номеров карт
	private String phoneNumbers;    //список телефонов

	public OnLoginSenderInitializationData(ClientDefinedEventType eventType, String cardNumbers, String phoneNumbers)
	{
		super(InteractionType.SYNC, PhaseType.CONTINUOUS_INTERACTION, eventType);

		this.cardNumbers = cardNumbers;
		this.phoneNumbers = phoneNumbers;
	}

	public String getCardNumbers()
	{
		return cardNumbers;
	}

	public void setCardNumbers(String cardNumbers)
	{
		this.cardNumbers = cardNumbers;
	}

	public String getPhoneNumbers()
	{
		return phoneNumbers;
	}

	public void setPhoneNumbers(String phoneNumbers)
	{
		this.phoneNumbers = phoneNumbers;
	}
}
