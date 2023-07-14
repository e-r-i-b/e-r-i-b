package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author krenev
 * @ created 01.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class RURCardFilter extends CardFilterBase
{
	private static Currency rubCurrency = null;

	static
	{
		try
		{
			rubCurrency = GateSingleton.getFactory().service(CurrencyService.class).findByAlphabeticCode("RUB");
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Проверка рублевого счета
	 */
	public boolean accept(Card card)
	{
		if(card.getCurrency()==null)
			return false;
		return (rubCurrency.getNumber().equals(card.getCurrency().getNumber()));
	}
}
