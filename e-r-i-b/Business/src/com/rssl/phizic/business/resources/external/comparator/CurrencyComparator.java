package com.rssl.phizic.business.resources.external.comparator;

import com.rssl.phizic.common.AbstractCompatator;
import com.rssl.phizic.business.resources.external.ExternalResourceLinkBase;

import java.util.Comparator;

/**
 *
 *
 * @author basharin
 * @ created 25.01.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Данный компаратор используется для сортировки ExternalResourceLinkBase согласно приоритету их валют (см. ENH034237)
 */
public class CurrencyComparator extends AbstractCompatator implements Comparator
{
	//приоритет сортировки валют
	private static final String[] orderOfCurrency = {"RUB", "RUR", "USD", "EUR"};

	public int compare(Object o1, Object o2)
	{
		if (o1 == null && o2 == null)
		{
			return 0;
		}
		if (o1 == null || o2 == null)
		{
			return -1;
		}

		ExternalResourceLinkBase linkBase1 = (ExternalResourceLinkBase) o1;
		ExternalResourceLinkBase linkBase2 = (ExternalResourceLinkBase) o2;

		if (isObjectsEquals(linkBase1.getCurrency(), linkBase2.getCurrency()))
			return 0;//они равны
		else if (getPriority(linkBase1) < getPriority(linkBase2))
			return -1;//хотя бы у одного элемента валюта есть в массиве приоритета валют
		else if (getPriority(linkBase1) > getPriority(linkBase2))
			return 1;//хотя бы у одного элемента валюта есть в массиве приоритета валют
		else if (linkBase1.getCurrency() == null)
			return -1;
		else if (linkBase2.getCurrency() == null)
		    return 1;
		else
			return linkBase1.getCurrency().getCode().compareToIgnoreCase(linkBase2.getCurrency().getCode()); //у обоих элементов валют нет в таблице. сравниваем лексикографически названия валют
	}

	/**
	 * возвращает позицию в массиве orderOfCurrency, которую занимает валюта счета
	 * @param linkBase линк на наш счет
	 * @return позиция
	 */
	private int getPriority(ExternalResourceLinkBase linkBase)
	{
		for (int i = 0; i < orderOfCurrency.length; ++i)
			if (linkBase.getCurrency() != null && linkBase.getCurrency().getCode() != null
					&& linkBase.getCurrency().getCode().compareToIgnoreCase(orderOfCurrency[i]) == 0)
				return i;
		return orderOfCurrency.length; //его нет в списке. самый низкий приоритет.
	}
}
