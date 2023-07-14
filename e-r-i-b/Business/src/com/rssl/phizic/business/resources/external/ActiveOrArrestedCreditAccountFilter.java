package com.rssl.phizic.business.resources.external;

/**
 * ‘ильтр, провер€ющий что счет кредитный арестован или активен
 * @author basharin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ActiveOrArrestedCreditAccountFilter extends ActiveOrArrestedAccountFilter
{
	@Override
	public boolean accept(AccountLink accountLink)
	{
		return super.accept(accountLink) && accountLink.getAccount().getCreditAllowed();
	}

}
