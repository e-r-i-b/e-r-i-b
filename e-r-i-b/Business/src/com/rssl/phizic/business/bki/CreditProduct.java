package com.rssl.phizic.business.bki;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Gulov
 * @ created 15.01.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��������� ��������� (�������� � ����)
 */
public abstract class CreditProduct
{
	private static final BigDecimal HUNDRED_PERCENT = new BigDecimal(100);

	/**
	 * ������������� ������� (����� ������� � ������)
	 */
	private final int id;

	/**
	 * ���� ������
	 */
	private Calendar openDate;

	/**
	 * ����� �������
	 */
	private Money amount;

	/**
	 * ��� ����� ������
	 */
	private String bankName;

	/**
	 * ������������ �������
	 */
	private String name;

	/**
	 * ���� �������
	 */
	private Duration duration;

	/**
	 * ������� �� �������. ����� � �������.
	 */
	private Money balance;

	/**
	 * ����� �� ������� �������� ������
	 */
	private String monthRequest;

	/**
	 * ����� ���������� �������
	 */
	private Money instalment;

	/**
	 * ����� ���������
	 */
	private Money arrears;

	/**
	 * ���� �������� �������
	 */
	private Calendar closedDate;

	/**
	 * ������� ��������
	 */
	private String reasonForClosure;

	/**
	 * ������ �����
	 */
	private int width;

	public CreditProduct(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public Calendar getOpenDate()
	{
		return openDate;
	}

	public void setOpenDate(Calendar openDate)
	{
		this.openDate = openDate;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public Money getArrears()
	{
		return arrears;
	}

	public void setArrears(Money arrears)
	{
		this.arrears = arrears;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Duration getDuration()
	{
		return duration;
	}

	public void setDuration(Duration duration)
	{
		this.duration = duration;
	}

	public Money getBalance()
	{
		return balance;
	}

	public void setBalance(Money balance)
	{
		this.balance = balance;
	}

	public String getMonthRequest()
	{
		return monthRequest;
	}

	public void setMonthRequest(String monthRequest)
	{
		this.monthRequest = monthRequest;
	}

	public Money getInstalment()
	{
		return instalment;
	}

	public void setInstalment(Money instalment)
	{
		this.instalment = instalment;
	}

	public void setClosedDate(Calendar closedDate)
	{
		this.closedDate = closedDate;
	}

	public Calendar getClosedDate()
	{
		return closedDate;
	}

	public String getReasonForClosure()
	{
		return reasonForClosure;
	}

	public void setReasonForClosure(String reasonForClosure)
	{
		this.reasonForClosure = reasonForClosure;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getWidth()
	{
		return width;
	}

	/**
	 * @return ����������� ����� ������� (� ��������� �� ����� �����)
	 */
	public int getInformer()
	{
		if (amount == null || amount.getValue() == null || amount.getValue().compareTo(BigDecimal.ZERO) == 0
				|| balance == null || balance.getValue() == null)
			return 0;
		BigDecimal temp = balance.getValue().divide(amount.getValue(), 4, 4).multiply(HUNDRED_PERCENT);
		return HUNDRED_PERCENT.subtract(temp).intValue();
	}
}
