package com.rssl.phizic.business.bki;

import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.util.MoneyFunctions;

import java.math.BigDecimal;

/**
 * @author Gulov
 * @ created 15.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Деньга для кредитного отчета
 */
public class Money
{
	private BigDecimal value;
	private String currency;
	//костыль для BUG088565 - CHG086810
	//@see /scripts/credit.detail.scroller.js:36
	private boolean mandatoryField;

	public Money(BigDecimal value, String currency)
	{
		this.value = value;
		this.currency = currency;
	}

	public BigDecimal getValue()
	{
		return value;
	}

	public void setValue(BigDecimal value)
	{
		this.value = value;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public void setMandatoryField(boolean mandatoryField)
	{
		this.mandatoryField = mandatoryField;
	}

	@Override
	public String toString()
	{
		if (value == null || StringHelper.isEmpty(currency))
			return getEmptyValue();
		String currencySign = CurrencyUtils.getCurrencySign(currency);
		if (StringHelper.isEmpty(currencySign))
			return getEmptyValue();
		return MoneyFunctions.formatAmountRound(value, true) + " " + currencySign;
	}

	private String getEmptyValue()
	{
		return mandatoryField ? "\u00A0" : "";
	}
}
