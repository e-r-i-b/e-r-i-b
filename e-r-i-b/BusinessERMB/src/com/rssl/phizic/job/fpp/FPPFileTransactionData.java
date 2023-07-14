package com.rssl.phizic.job.fpp;

import java.math.BigDecimal;

/**
 * Транзакция ФПП-файла
 * @author Rtischeva
 * @ created 09.06.14
 * @ $Author$
 * @ $Revision$
 */
class FPPFileTransactionData
{
	private final BigDecimal chargeAmount;

	private final String cardNumber;

	private final String cardOSB;

	private final String cardVSP;

	FPPFileTransactionData(BigDecimal chargeAmount, String cardNumber, String cardOSB, String cardVSP)
	{
		this.chargeAmount = chargeAmount;
		this.cardNumber = cardNumber;
		this.cardOSB = cardOSB;
		this.cardVSP = cardVSP;
	}

	public BigDecimal getChargeAmount()
	{
		return chargeAmount;
	}

	String getCardNumber()
	{
		return cardNumber;
	}

	String getCardOSB()
	{
		return cardOSB;
	}

	String getCardVSP()
	{
		return cardVSP;
	}
}
