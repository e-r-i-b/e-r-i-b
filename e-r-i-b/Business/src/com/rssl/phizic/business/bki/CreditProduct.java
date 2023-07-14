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
 * Предок кредитных продуктов (кредитов и карт)
 */
public abstract class CreditProduct
{
	private static final BigDecimal HUNDRED_PERCENT = new BigDecimal(100);

	/**
	 * Идентификатор кредита (номер кредита в списке)
	 */
	private final int id;

	/**
	 * Дата начала
	 */
	private Calendar openDate;

	/**
	 * Сумма кредита
	 */
	private Money amount;

	/**
	 * Кто выдал кредит
	 */
	private String bankName;

	/**
	 * Наименование кредита
	 */
	private String name;

	/**
	 * Срок кредита
	 */
	private Duration duration;

	/**
	 * Остаток по кредиту. Всего к выплате.
	 */
	private Money balance;

	/**
	 * Месяц на которой присланы данные
	 */
	private String monthRequest;

	/**
	 * Сумма ближайшего платежа
	 */
	private Money instalment;

	/**
	 * Сумма просрочки
	 */
	private Money arrears;

	/**
	 * Дата закрытия кредита
	 */
	private Calendar closedDate;

	/**
	 * Причина закрытия
	 */
	private String reasonForClosure;

	/**
	 * Ширина блока
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
	 * @return выплаченная часть кредита (в процентах от общей суммы)
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
