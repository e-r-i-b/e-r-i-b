package com.rssl.phizic.business.bki;

import com.rssl.phizgate.common.credit.bki.dictionary.BkiAccountPaymentStatus;

/**
 * @author Gulov
 * @ created 25.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Состояние выплаты и количество состояний
 */
public class PaymentState
{
	/**
	 * Состояние
	 */
	private final BkiAccountPaymentStatus state;

	/**
	 * Количество
	 */
	private int count;

	public PaymentState(BkiAccountPaymentStatus state, int count)
	{
		this.state = state;
		this.count = count;
	}

	public BkiAccountPaymentStatus getState()
	{
		return state;
	}

	public void add()
	{
		++count;
	}

	public int getCount()
	{
		return count;
	}
}
