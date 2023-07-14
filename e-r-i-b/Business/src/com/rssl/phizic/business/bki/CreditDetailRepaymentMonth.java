package com.rssl.phizic.business.bki;

import com.rssl.phizgate.common.credit.bki.dictionary.BkiAccountPaymentStatus;

/**
 * @author Gulov
 * @ created 21.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Информация по выплатам за месяц
 */
public class CreditDetailRepaymentMonth
{
	/**
	 * Год
	 */
	private final int year;

	/**
	 * Номер месяца
	 */
	private final int monthNumber;

	/**
	 * Название месяца
	 */
	private final String month;

	/**
	 * Статус выплаты (вовремя, неуплата, количество дней неуплаты...)
	 */
	private final BkiAccountPaymentStatus state;

	/**
	 * Остаток задолженности за прошлый (исторический) период
	 */
	private final Money balance;

	/**
	 * Сумма просроченной задолженности
	 */
	private final Money arrears;

	/**
	 * Размер периодического платежа
	 */
	private final Money payment;

	CreditDetailRepaymentMonth(int year, int monthNumber, String month, BkiAccountPaymentStatus state, Money balance, Money arrears, Money payment)
	{
		this.year = year;
		this.monthNumber = monthNumber;
		this.month = month;
		this.state = state;
		this.balance = balance;
		this.arrears = arrears;
		this.payment = payment;
	}

	public int getYear()
	{
		return year;
	}

	public int getMonthNumber()
	{
		return monthNumber;
	}

	public String getMonth()
	{
		return month;
	}

	public BkiAccountPaymentStatus getState()
	{
		return state;
	}

	public Money getBalance()
	{
		return balance;
	}

	public Money getArrears()
	{
		return arrears;
	}

	public Money getPayment()
	{
		return payment;
	}
}
