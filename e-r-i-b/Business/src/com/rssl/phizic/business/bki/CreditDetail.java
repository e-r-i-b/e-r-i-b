package com.rssl.phizic.business.bki;

import java.util.Calendar;

/**
 * @author Gulov
 * @ created 21.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Детальная информация по кредиту
 */
public class CreditDetail
{
	/**
	 * Дата-время получения отчёта
	 */
	private final Calendar lastReport;

	/**
	 * Кредит
	 */
	private final CreditProduct credit;

	/**
	 * История выплат
	 */
	private final RepaymentHistory repaymentHistory;

	/**
	 * Контракт кредита
	 */
	private final CreditContract creditContract;

	public CreditDetail(Calendar lastReport, CreditProduct product, RepaymentHistory repaymentHistory, CreditContract creditContract) {
		this.lastReport = lastReport;
		this.credit = product;
		this.repaymentHistory = repaymentHistory;
		this.creditContract = creditContract;
	}

	public Calendar getLastReport()
	{
		return lastReport;
	}

	public CreditProduct getCredit()
	{
		return credit;
	}

	public RepaymentHistory getRepaymentHistory()
	{
		return repaymentHistory;
	}

	public CreditContract getCreditContract()
	{
		return creditContract;
	}
}
