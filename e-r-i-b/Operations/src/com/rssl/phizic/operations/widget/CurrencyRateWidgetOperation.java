package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.web.CurrencyRateWidget;

import java.util.Set;

/**
 * Операция виджета "Курсы"
 * @author gulov
 * @ created 18.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class CurrencyRateWidgetOperation extends WidgetOperation<CurrencyRateWidget>
{
	public Set<String> getAllCurrencyCodes()
	{
		return CurrencyRateWidget.getAllCurrencyCodes();
	}

	public Set<String> getAllImaCodes()
	{
		return CurrencyRateWidget.getAllImaCodes();
	}
}
