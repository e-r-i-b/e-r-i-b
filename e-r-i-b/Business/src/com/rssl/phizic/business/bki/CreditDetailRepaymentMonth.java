package com.rssl.phizic.business.bki;

import com.rssl.phizgate.common.credit.bki.dictionary.BkiAccountPaymentStatus;

/**
 * @author Gulov
 * @ created 21.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� �� �������� �� �����
 */
public class CreditDetailRepaymentMonth
{
	/**
	 * ���
	 */
	private final int year;

	/**
	 * ����� ������
	 */
	private final int monthNumber;

	/**
	 * �������� ������
	 */
	private final String month;

	/**
	 * ������ ������� (�������, ��������, ���������� ���� ��������...)
	 */
	private final BkiAccountPaymentStatus state;

	/**
	 * ������� ������������� �� ������� (������������) ������
	 */
	private final Money balance;

	/**
	 * ����� ������������ �������������
	 */
	private final Money arrears;

	/**
	 * ������ �������������� �������
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
