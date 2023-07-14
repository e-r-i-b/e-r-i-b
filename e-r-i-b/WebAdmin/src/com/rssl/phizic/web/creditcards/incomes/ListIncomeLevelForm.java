package com.rssl.phizic.web.creditcards.incomes;

import com.rssl.phizic.business.creditcards.incomes.IncomeLevel;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dorzhinov
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListIncomeLevelForm extends ListFormBase<IncomeLevel>
{
	List<Currency> currencies = new ArrayList<Currency>();

	public List<Currency> getCurrencies()
	{
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies)
	{
		this.currencies = currencies;
	}
}
