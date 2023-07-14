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
 * ���������� �� �������� �� ���
 */
public class CreditDetailRepaymentYear
{
	/**
	 * ���
	 */
	private int year;

	/**
	 * ������ ������ �� �����
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
