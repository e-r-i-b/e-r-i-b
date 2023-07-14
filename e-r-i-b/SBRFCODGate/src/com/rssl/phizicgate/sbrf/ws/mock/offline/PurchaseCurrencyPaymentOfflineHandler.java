package com.rssl.phizicgate.sbrf.ws.mock.offline;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.bankroll.Account;
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

public class PurchaseCurrencyPaymentOfflineHandler extends CurrencyPaymentBaseOfflineHandler
{
	protected String getSumPath()
	{
		return "/message/purchaseCurrencyDemand_q/sum";
	}

	protected String getPathBase()
	{
		return "/message/purchaseCurrencyDemand_q";
	}

	protected String convertSum(Account account, String sum, Currency currency, boolean isDebet) throws GateException, GateLogicException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		if(currencyService.getNationalCurrency().compare(currency))
		{
			if(isDebet)
			{
				if(account.getCurrency().compare(currencyService.findByAlphabeticCode("USD")))
				{
					return (new BigDecimal(sum).divide(new BigDecimal(26.9),2, RoundingMode.UP)).toPlainString();
				}
				else
				{
					return (new BigDecimal(sum).divide(new BigDecimal(34.25),2, RoundingMode.UP)).toPlainString();
				}
			}
			else return sum;
		}
		else
		{
			if(!isDebet)
			{
				if(currency.compare(currencyService.findByAlphabeticCode("USD")))
				{
					return (new BigDecimal(sum).multiply(new BigDecimal(26.9))).toPlainString();
				}
				else
				{
					return (new BigDecimal(sum).multiply(new BigDecimal(34.25))).toPlainString();
				}
			}
			else return sum;
		}
	}
}
