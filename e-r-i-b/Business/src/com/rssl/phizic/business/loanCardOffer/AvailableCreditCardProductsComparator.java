package com.rssl.phizic.business.loanCardOffer;

import com.rssl.phizic.business.creditcards.conditions.CreditCardCondition;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.loans.CurrencyRurUsdEuroComparator;

import java.util.Comparator;

/**
 * @author Dorzhinov
 * @ created 05.07.2011
 * @ $Author$
 * @ $Revision$
 * Сортировщик объектов, представляющих из себя массив из 3-х элементов:
 * 1.CreditCardCondition
 * 2.CreditCardProduct
 *
 * по имени продукта и затем в рамках каждого продукта по валюте: рубли, доллары, евро
 */
public class AvailableCreditCardProductsComparator implements Comparator<Object[]>
{
	public int compare(Object array1[], Object array2[])
	{
		// сортировка по имени продукта (по сути из БД у нас приходит отсортировано, но на случай изменений в запросе, сделаем сортировку
		switch(((CreditCardProduct) array1[1]).getName().compareTo(((CreditCardProduct) array2[1]).getName()))
		{
			case 0 :
				// сортировка по валютам: рубли, доллары, евро, все остальное по названию
				CurrencyRurUsdEuroComparator currencyComparator = new CurrencyRurUsdEuroComparator();
				return currencyComparator.compare(((CreditCardCondition) array1[0]).getCurrency(), ((CreditCardCondition) array2[0]).getCurrency());
			case -1 :
				return -1;
			case 1 :
		        return 1;
			default:
				return 0;
		}
	}
}
