package com.rssl.phizic.business.resources.external.comparator;

import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.utils.StringHelper;

import java.util.Comparator;

/**
 @author: Egorovaa
 @ created: 29.08.2012
 @ $Author$
 @ $Revision$
 */

/**
 * Данный компаратор используется для сортировки IMAccountLink согласно приоритету их металлов (золото, серебро, платина, палладий)
 */
public class IMACurrencyComparator implements Comparator<IMAccountLink>
{
	//приоритет сортировки по металлам
	private static final String[] currencies = {"A98", "A99", "A76", "A33"};

	public int compare(IMAccountLink imAccount1, IMAccountLink imAccount2)
	{
		if (imAccount1 == null && imAccount2 == null)
		{
			return 0;
		}
		else if (imAccount1 == null)
			return -1;
		else if (imAccount2 == null)
			return 1;


		Currency currency1 = imAccount1.getCurrency();
		Currency currency2 = imAccount2.getCurrency();


		if (StringHelper.equalsNullIgnore(currency1.getCode(), currency2.getCode()))
			return 0;//они равны
		else if (currency1 == null)
			return -1;
		else if (currency2 == null)
			return 1;
		else if (getPriority(currency1) < getPriority(currency2))
			return -1;//хотя бы у одного элемента металл есть в массиве приоритета
		else if (getPriority(currency1) > getPriority(currency2))
			return 1;//хотя бы у одного элемента металл есть в массиве приоритета 
		else
			return currency1.getCode().compareToIgnoreCase(currency2.getCode());
	}

	private int getPriority(Currency currency)
	{
		for (int i = 0; i < currencies.length; ++i)
			if (currency != null && currency.getCode() != null
					&& currency.getCode().compareToIgnoreCase(currencies[i]) == 0)
				return i;
		return currencies.length; //его нет в списке. самый низкий приоритет.
	}

}
