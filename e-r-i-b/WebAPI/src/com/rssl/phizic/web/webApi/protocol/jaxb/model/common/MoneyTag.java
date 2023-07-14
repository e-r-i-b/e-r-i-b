package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import com.rssl.phizic.common.types.Money;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Тэг типа Money
 * @author Jatsky
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"amount", "currency"})
@XmlRootElement
public class MoneyTag
{
	private String amount;
	private CurrencyTag currency;

	@XmlElement(name = "amount", required = true)
	public String getAmount()
	{
		return amount;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	@XmlElement(name = "currency", required = true)
	public CurrencyTag getCurrency()
	{
		return currency;
	}

	public void setCurrency(CurrencyTag currency)
	{
		this.currency = currency;
	}

	public MoneyTag()
	{
	}

	public MoneyTag(Money money)
	{
		this.setAmount(money.getDecimal().toString());
		this.setCurrency(new CurrencyTag(money.getCurrency()));
	}

	public MoneyTag(Money money, String sign)
	{
		this.setAmount(sign + money.getDecimal().toString());
		this.setCurrency(new CurrencyTag(money.getCurrency()));
	}

	public MoneyTag(String amount, CurrencyTag currency)
	{
		this.amount = amount;
		this.currency = currency;
	}
}
