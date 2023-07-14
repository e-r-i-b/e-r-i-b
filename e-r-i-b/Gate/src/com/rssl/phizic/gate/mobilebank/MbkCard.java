package com.rssl.phizic.gate.mobilebank;

/**
 * Карта клиента в контексте запроса на миграцию
 * @author Puzikov
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */

public class MbkCard
{
	private String number;
	private MigrationCardType cardType;
	private boolean isErmbConnected;

	public MbkCard()
	{
	}

	public MbkCard(String number, MigrationCardType cardType, boolean ermbConnected)
	{
		this.number = number;
		this.cardType = cardType;
		isErmbConnected = ermbConnected;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public MigrationCardType getCardType()
	{
		return cardType;
	}

	public void setCardType(MigrationCardType cardType)
	{
		this.cardType = cardType;
	}

	public void setCardType(String cardType)
	{
		if(cardType == null || cardType.trim().length() == 0)
			return;
		this.cardType = MigrationCardType.fromValue(cardType);
	}

	public boolean isErmbConnected()
	{
		return isErmbConnected;
	}

	public void setErmbConnected(boolean ermbConnected)
	{
		isErmbConnected = ermbConnected;
	}
}
