package com.rssl.phizicgate.iqwave.utils;

/**
 * @author osminin
 * @ created 02.02.2011
 * @ $Author$
 * @ $Revision$
 * Объектное представление компоситного идентификатора автоплатежа
 * <curNumber>^<telNumber>^<providerId>
 */
public class AutoPaymentCompositeId
{
	private static final String DELIMETER = "^";

	private String cardNumber; // номер карты
	private String telNumber;  // номер лицевого счета/телефона
	private String providerId; // код сервиса(маршрута)

	public AutoPaymentCompositeId(String externalId)
	{
		String [] args = externalId.split("\\^");
		cardNumber = args[0];
		telNumber = args[1];
		providerId = args[2];
	}

	public AutoPaymentCompositeId(String cardNumber, String telNumber, String providerId)
	{
		this.cardNumber = cardNumber;
		this.telNumber = telNumber;
		this.providerId = providerId;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public String getTelNumber()
	{
		return telNumber;
	}

	public String getProviderId()
	{
		return providerId;
	}

	public String toString()
	{
		StringBuilder compositeId = new StringBuilder();
		compositeId.append(cardNumber).append(DELIMETER);
		compositeId.append(telNumber).append(DELIMETER);
		compositeId.append(providerId);
		return compositeId.toString();
	}
}
