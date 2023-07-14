package com.rssl.phizic.business.resources.external;

/**
 * ‘ильтр, провер€ющий что карта не кредитна€ имеет валюту rur и арестован или активен
 * @author basharin
 * @ created 05.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ActiveOrArrestedRurNotCreditCardFilter extends CardFilterConjunction
{
	public ActiveOrArrestedRurNotCreditCardFilter()
	{
		addFilter(new RURCardFilter());
		addFilter(new NotCreditCardFilter());
		addFilter(new ActiveCardWithArrestedAccountFilter());
	}
}
