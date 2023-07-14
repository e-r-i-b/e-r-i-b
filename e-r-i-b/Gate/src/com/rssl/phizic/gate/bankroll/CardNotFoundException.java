package com.rssl.phizic.gate.bankroll;

/**
 * �� ������ ����
 *
 * @author Evgrafov
 * @ created 08.09.2006
 * @ $Author: Roshka $
 * @ $Revision: 2418 $
 */
public class CardNotFoundException extends com.rssl.phizic.gate.exceptions.GateLogicException
{
	private String cardNumber;

	/**
	 *
	 * @param cardNumber
	 */
	public CardNotFoundException(String cardNumber)
	{
		super("����� � " + cardNumber + " �� �������");
		this.cardNumber = cardNumber;
	}

	public String getAccountNumber()
	{
		return cardNumber;
	}
}