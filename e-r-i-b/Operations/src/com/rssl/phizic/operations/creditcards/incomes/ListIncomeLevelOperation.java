package com.rssl.phizic.operations.creditcards.incomes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dorzhinov
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListIncomeLevelOperation extends OperationBase implements ListEntitiesOperation
{
	
	private List<Currency> currencies = new ArrayList<Currency>();

	public void initialize() throws BusinessException
	{
		initCurrencies();
	}

	public List<Currency> getCurrencies()
	{
		return currencies;
	}

	private void initCurrencies() throws BusinessException
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			currencies.add(currencyService.findByAlphabeticCode("RUB"));
			currencies.add(currencyService.findByAlphabeticCode("USD"));
			currencies.add(currencyService.findByAlphabeticCode("EUR"));
		}
		catch(GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
