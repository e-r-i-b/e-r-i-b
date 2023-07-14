package com.rssl.phizic.business.dictionaries.currencies;

import com.rssl.phizic.common.types.Currency;

import java.util.Comparator;

/**
 * @author Omeliyanchuk
 * @ created 18.09.2006
 * @ $Author$
 * @ $Revision$
 */

/**
 * Данный компаратор используется только для сортировки элементов коллекции валют,
 * в частности при загрузке справочника валют сортируются элементы, считанные из currencies.xml
 */
public class CurrencyComparator implements Comparator
{
	public int compare(Object o1, Object o2)
	{
		Currency currency2 = (Currency)o2;
		Currency currency1 = (Currency)o1;

		int i;

		if ((i = currency1.getExternalId().compareTo(currency2.getExternalId())) != 0) return i;

		if ((i = currency1.getCode().compareTo(currency2.getCode())) != 0) return i;

		if ((i = currency1.getNumber().compareTo(currency2.getNumber())) != 0) return i;

		if ((i = currency1.getName().compareTo(currency2.getName())) != 0) return i;

		return 0;
	}
}
