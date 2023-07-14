package com.rssl.phizic.business.bki;

/**
 * @author Gulov
 * @ created 15.01.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * �����
 */
public class Card extends CreditProduct
{
	/**
	 * ����� ������
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
