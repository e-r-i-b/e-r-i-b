package com.rssl.phizic.web.common.client.ima;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * Форма для списка ОМС для открытия
 * @author Pankin
 * @ created 27.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListIMAProductForm extends ListFormBase
{
	private Map<Currency, CurrencyRate> currencyRates;
	private String category;

	public Map<Currency, CurrencyRate> getCurrencyRates()
	{
		return currencyRates;
	}

	public void setCurrencyRates(Map<Currency, CurrencyRate> currencyRates)
	{
		this.currencyRates = currencyRates;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}
}
