package com.rssl.phizic.web.client.component;

import com.rssl.phizic.web.component.WidgetForm;

import java.util.Set;

/**
 * Форма виджета "Курсы"
 * @ author Rtischeva
 * @ created 12.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateWidgetForm extends WidgetForm
{
	private Set<String> currencyCodes;

	private	Set<String> imaCodes;

	public Set<String> getCurrencyCodes()
	{
		return currencyCodes;
	}

	public void setCurrencyCodes(Set<String> currencyCodes)
	{
		this.currencyCodes = currencyCodes;
	}

	public Set<String> getImaCodes()
	{
		return imaCodes;
	}

	public void setImaCodes(Set<String> imaCodes)
	{
		this.imaCodes = imaCodes;
	}
}
