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
 * ����, ������� �������� ������� ������ �� ����, ��������� ������ � �� ����������
 */
public class RepaymentHistory
{
	/**
	 * ������� ������ �� ����
	 */
	private final List<CreditDetailRepaymentYear> years;

	/**
	 * ��������� ������ (�������, ���������� ���� ��������)
	 */
	private final List<PaymentState> states;

	/**
	 * ��������� ����������� ������ (��������, �����, ������)
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
