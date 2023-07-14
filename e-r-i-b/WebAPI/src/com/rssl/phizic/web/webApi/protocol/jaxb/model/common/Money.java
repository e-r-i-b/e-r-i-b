package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Balovtsev
 * @since 12.05.2014
 */
@XmlType(propOrder = {"amount", "currency"})
public class Money
{
	private String   amount;
	private Currency currency;

	/**
	 */
	public Money()
	{
	}

	/**
	 * @param amount ���������� ������� �� ������
	 * @param currency ������
	 */
	public Money(String amount, Currency currency)
	{
		this.amount = amount;
		this.currency = currency;
	}

	/**
	 * �������� ����������� �������� �� com.rssl.phizic.common.types.Money
	 * @param money com.rssl.phizic.common.types.Money
	 */
	public Money(com.rssl.phizic.common.types.Money money)
	{
		if (money == null)
		{
			return;
		}
		else
		{
			this.amount = money.getDecimal().toPlainString();
			this.currency = new Currency(money.getCurrency());
		}
	}

	/**
	 * ������������ �������
	 * @return ���������� ������� �� ������
	 */
	@XmlElement(name = "amount", required = true)
	public String getAmount()
	{
		return amount;
	}

	/**
	 * ������������ �������
	 * @return ������
	 */
	@XmlElement(name = "currency", required = true)
	public Currency getCurrency()
	{
		return currency;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}
}
