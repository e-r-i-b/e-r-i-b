package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.utils.MockHelper;

/**
 * ‘ильтр, провер€ющий что счет кредитный арестован или активен и имеет валюту rur
 * @author basharin
 * @ created 05.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ActiveOrArrestedRurCreditAccountFilter extends RURAccountFilter
{
	public boolean accept(AccountLink accountLink)
	{
		if(!MockHelper.isMockObject(accountLink.getAccount()))
		{
			return (new ActiveOrArrestedAccountFilter().accept(accountLink)) && super.accept(accountLink) && accountLink.getAccount().getCreditAllowed();
		}
		else return false;
	}
}
