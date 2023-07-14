package com.rssl.phizic.business.bki;

import java.util.Collections;
import java.util.List;

/**
 * @author Gulov
 * @ created 21.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Блок, который включает историю выплат за годы, состояние выплат и их количество
 */
public class RepaymentHistory
{
	/**
	 * История выплат за годы
	 */
	private final List<CreditDetailRepaymentYear> years;

	/**
	 * Состояние выплат (вовремя, количество дней неуплаты)
	 */
	private final List<PaymentState> states;

	/**
	 * Состояние критических выплат (неуплата, тяжба, списан)
	 */
	private final List<PaymentState> criticalStates;

	public RepaymentHistory(List<CreditDetailRepaymentYear> history, List<PaymentState> states, List<PaymentState> criticalStates)
	{
		this.years = history;
		this.states = states;
		this.criticalStates = criticalStates;
	}

	public List<CreditDetailRepaymentYear> getYears()
	{
		return Collections.unmodifiableList(years);
	}

	public List<PaymentState> getStates()
	{
		return Collections.unmodifiableList(states);
	}

	public List<PaymentState> getCriticalStates()
	{
		return Collections.unmodifiableList(criticalStates);
	}

	public int getStateCount()
	{
		int count = 0;
		for (PaymentState paymentState : states)
			count += paymentState.getCount();
		return count;
	}
}
