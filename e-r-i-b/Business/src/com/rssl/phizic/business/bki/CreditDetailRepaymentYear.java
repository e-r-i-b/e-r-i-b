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
 * Информация по выплатам за год
 */
public class CreditDetailRepaymentYear
{
	/**
	 * Год
	 */
	private int year;

	/**
	 * Список выплат за месяц
	 */
	private List<CreditDetailRepaymentMonth> monthHistory;

	public CreditDetailRepaymentYear(int year, List<CreditDetailRepaymentMonth> monthHistory)
	{
		this.year = year;
		this.monthHistory = monthHistory;
	}

	public int getYear()
	{
		return year;
	}

	public List<CreditDetailRepaymentMonth> getMonthHistory()
	{
		return Collections.unmodifiableList(monthHistory);
	}
}
