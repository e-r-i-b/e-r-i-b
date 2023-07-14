package com.rssl.phizic.business.dictionaries.currencies;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.rates.BDCurrencyRateService;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * ѕеред удалением валюты провер€ем есть ли курсы дл€ нее. ≈сли есть то не трогаем
 * @author basharin
 * @ created 14.08.14
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyRepoDestination extends CurrencyDestination
{
	private static final BDCurrencyRateService bdCurrencyService = new BDCurrencyRateService();

	@Override
	public void remove(DictionaryRecord oldValue) throws GateException
	{
		if (oldValue instanceof CurrencyImpl)
		{
			try
			{
				if (!bdCurrencyService.rateExistByCurrency((CurrencyImpl) oldValue))
				{
					super.remove(oldValue);
				}
			}
			catch (BusinessException e)
			{
				throw new GateException(e);
			}
		}
		else
		{
			super.remove(oldValue);
		}
	}
}
