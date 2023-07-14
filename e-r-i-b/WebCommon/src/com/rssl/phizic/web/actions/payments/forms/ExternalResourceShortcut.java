package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;

/**
 * @author Erkin
 * @ created 25.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class ExternalResourceShortcut
{
	private String code;

	private String name;

	private String number;

	private Currency currency;

	private Money availableLimit;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ¬озвращает код внешнего ресурса в виде "тип:внутренний_ID"
	 * Ќапример, дл€ карты: "card:385"
	 * @return код внешнего ресурса
	 */
	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Money getAvailableLimit()
	{
		return availableLimit;
	}

	public void setAvailableLimit(Money availableLimit)
	{
		this.availableLimit = availableLimit;
	}

	public int hashCode()
	{
		return code.hashCode();
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ExternalResourceShortcut shortcut = (ExternalResourceShortcut) o;

		return code.equals(shortcut.code);
	}
}
