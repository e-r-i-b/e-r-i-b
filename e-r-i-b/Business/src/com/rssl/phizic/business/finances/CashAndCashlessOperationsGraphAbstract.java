package com.rssl.phizic.business.finances;

/**
 * Операция для построения графика на странице "операции" (наличные и безналичные)
 *
 * @author lepihina
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class CashAndCashlessOperationsGraphAbstract extends CardOperationAbstract
{
	private boolean cash; // Наличная ли операция

	public boolean getCash()
	{
		return cash;
	}

	public void setCash(boolean cash)
	{
		this.cash = cash;
	}
}
