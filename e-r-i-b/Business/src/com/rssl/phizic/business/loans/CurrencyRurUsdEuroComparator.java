package com.rssl.phizic.business.loans;

import com.rssl.phizic.common.types.Currency;

import java.util.Comparator;

/**
 * @author Mescheryakova
 * @ created 10.06.2011
 * @ $Author$
 * @ $Revision$
 *
 *  * Сортируем список условий кредита по валюте: рубли, доллары, евро, остальные по алфавиту
 */

public class CurrencyRurUsdEuroComparator implements Comparator<Currency>
{
public int compare(Currency currency1, Currency currency2)
	{
		if (currency2 == null || currency1 == null)
			return 0;

		// строим в цепочку Рубли, Доллары, Евро
		if (currency2.getNumber().equals("810") || currency2.getNumber().equals("643"))
			return 1;
		if (currency1.getNumber().equals("810") || currency1.getNumber().equals("643"))
			return -1;
		if (currency2.getNumber().equals("840"))
			return 1;
		if (currency1.getNumber().equals("840"))
			return -1;
		if (currency2.getNumber().equals("978"))
			return 1;
		if (currency1.getNumber().equals("978"))
			return -1;

		// дальеш сортируем по алфавиту
		return currency1.getName().compareTo(currency2.getName());
	}
}
