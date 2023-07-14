package com.rssl.phizic.business.bki;

/**
 * @author Gulov
 * @ created 15.01.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Карта
 */
public class Card extends CreditProduct
{
	/**
	 * Сумма лимита
	 */
	private Money creditLimit;

	public Card(int id)
	{
		super(id);
	}

	public void setCreditLimit(Money creditLimit)
	{
		this.creditLimit = creditLimit;
	}

	public Money getCreditLimit()
	{
		return creditLimit;
	}
}
