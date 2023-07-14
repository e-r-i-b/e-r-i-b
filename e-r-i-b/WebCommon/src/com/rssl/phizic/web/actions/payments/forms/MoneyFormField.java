package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 30.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сложное денежное поле "сумма" + "валюта"
 * Предположительно, сумма или валюта может быть не указана
 */
public class MoneyFormField extends FormFieldBase
{
	private BigDecimal amount;

	private Currency currency;

	///////////////////////////////////////////////////////////////////////////

	public FormFieldType getType()
	{
		return FormFieldType.MONEY;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Money getMoney()
	{
		if (amount != null && currency != null)
			return new Money(amount, currency);
		else return null;
	}
}
