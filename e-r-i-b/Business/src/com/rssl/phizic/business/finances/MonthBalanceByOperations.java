package com.rssl.phizic.business.finances;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * �������� ��� ���������� �������� �� ������ �� �������� "��������" (����� � ������)
 *
 * @author lepihina
 * @ created 09.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class MonthBalanceByOperations extends BalanceByOperations
{
	BigDecimal income; // �����
	BigDecimal outcome; // ������

	public MonthBalanceByOperations(Calendar date, BigDecimal income, BigDecimal outcome)
	{
		this.date = date;
		this.income = income;
		this.outcome = outcome;
	}

	public BigDecimal getOutcome()
	{
		return outcome;
	}

	public void setOutcome(BigDecimal outcome)
	{
		this.outcome = outcome;
	}

	public BigDecimal getIncome()
	{
		return income;
	}

	public void setIncome(BigDecimal income)
	{
		this.income = income;
	}
}
