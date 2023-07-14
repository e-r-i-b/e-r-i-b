package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.MockHelper;

/**
 * @author Evgrafov
 * @ created 14.11.2005
 * @ $Author$
 * @ $Revision$
 *
 * Проверяет является ли счет рублевым
 */

public class RURAccountFilter implements AccountFilter
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
    public boolean accept(AccountLink accountLink)
    {
	    if(!MockHelper.isMockObject(accountLink.getAccount()))
	    {
			return ( rubCurrency.getNumber().equals(accountLink.getAccount().getCurrency().getNumber()));
	    }
	    else return false;
    }
}
