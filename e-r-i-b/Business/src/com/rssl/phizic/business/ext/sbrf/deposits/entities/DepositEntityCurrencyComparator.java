package com.rssl.phizic.business.ext.sbrf.deposits.entities;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * Компаратор для сортировки валют. Сначала рубли, затем доллары, потом евро.
 * Остальные валюты  сортируются по названию
 *
 * @author EgorovaA
 * @ created 12.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositEntityCurrencyComparator implements Comparator<String>
{
	private static TreeMap<String, Integer> sortOrderMap = new TreeMap<String, Integer>();

	private static DepositEntityCurrencyComparator instance = new DepositEntityCurrencyComparator();

	public static DepositEntityCurrencyComparator getInstance()
	{
		return instance;
	}

	public int compare(String first, String second)
	{
		TreeMap<String, Integer> map = getSortOrder();

		Integer firstCurrencyPrior =  map.get(first);
		Integer secondCurrencyPrior =  map.get(second);

		if(firstCurrencyPrior == null)
			firstCurrencyPrior = map.size() + 1;

		if(secondCurrencyPrior == null)
			secondCurrencyPrior = map.size() + 1;

		int compareResult = firstCurrencyPrior.compareTo(secondCurrencyPrior);
		if (compareResult != 0)
			return compareResult;
		return first.compareTo(second);
	}

	private TreeMap<String, Integer> getSortOrder()
	{
		if (sortOrderMap.isEmpty())
			fillSortOrderMap();
		return sortOrderMap;
	}

	private void fillSortOrderMap()
	{
		sortOrderMap.put("RUB", 0);
		sortOrderMap.put("USD", 1);
		sortOrderMap.put("EUR", 2);
	}
}