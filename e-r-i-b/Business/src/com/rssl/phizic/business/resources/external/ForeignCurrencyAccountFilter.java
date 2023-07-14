package com.rssl.phizic.business.resources.external;

/**
 * @author Kidyaev
 * @ created 14.11.2005
 * @ $Author$
 * @ $Revision$
 *
 * ѕровер€ет €вл€етс€ ли счет рублевым
 */

public class ForeignCurrencyAccountFilter extends RURAccountFilter
{
    /**
     * ѕроверка валютного (т.е. не рублевого) счета
     */
    public boolean accept(AccountLink accountLink)
    {
	   return !super.accept(accountLink);
    }
}
