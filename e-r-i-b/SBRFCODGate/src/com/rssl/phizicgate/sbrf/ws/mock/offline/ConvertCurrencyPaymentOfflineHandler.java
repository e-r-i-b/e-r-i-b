package com.rssl.phizicgate.sbrf.ws.mock.offline;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.common.types.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Omeliyanchuk
 * @ created 07.05.2008
 * @ $Author$
 * @ $Revision$
 */

public class ConvertCurrencyPaymentOfflineHandler extends CurrencyPaymentBaseOfflineHandler
{
	private static final String CURRENCY_DEMAND_SUM = "/message/convertCurrencyDemand_q/sum";

	protected String getSumPath()
	{
		return CURRENCY_DEMAND_SUM;
	}

	protected String getPathBase()
	{
		return "/message/convertCurrencyDemand_q";
	}

	protected String convertSum(Account account, String sum, Currency currency, boolean isDebet) throws GateException, GateLogicException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		if(isDebet)
		{
			//передаеться сумма в валюте счета списания
			return sum;
		}
		else
		{
			if(currency.compare(currencyService.findByAlphabeticCode("USD")))
			{
				return (new BigDecimal(sum).multiply(new BigDecimal(0.78))).toPlainString();
			}
			else
			{
				return (new BigDecimal(sum).multiply(new BigDecimal(1.27))).toPlainString();
			}
		}
	}
}
