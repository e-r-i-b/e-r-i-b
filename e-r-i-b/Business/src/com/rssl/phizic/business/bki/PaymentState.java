package com.rssl.phizic.business.bki;

import com.rssl.phizgate.common.credit.bki.dictionary.BkiAccountPaymentStatus;

/**
 * @author Gulov
 * @ created 25.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������� � ���������� ���������
 */
public class PaymentState
{
	/**
	 * ���������
	 */
	private final BkiAccountPaymentStatus state;

	/**
	 * ����������
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
