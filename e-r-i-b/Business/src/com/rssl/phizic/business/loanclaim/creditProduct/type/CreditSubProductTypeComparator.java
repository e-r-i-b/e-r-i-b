package com.rssl.phizic.business.loanclaim.creditProduct.type;

import com.rssl.phizic.utils.StringHelper;

import java.util.Comparator;

/**
 * @author Moshenko
 * @ created 05.01.2014
 * @ $Author$
 * @ $Revision$
 *  Компаратор типа кредитного суб продукта
 */
public class CreditSubProductTypeComparator  implements Comparator
{
	public int compare(Object o1, Object o2)
	{
		CreditSubProductType type1 = (CreditSubProductType) o1;
		CreditSubProductType type2 = (CreditSubProductType) o2;
		int comareTb = type1.getTerbank().compareTo(type2.getTerbank());
		String currCode = type1.getCurrency().getCode();
		boolean comareCurrency = StringHelper.equals(type1.getCurrency().getCode(), type2.getCurrency().getCode());
		//Если это один тб, и одинаковая валюта.
		if ( comareTb == 0 && comareCurrency)
			return 0;
			//Если это один тб, и разная валюта.
		else if (comareTb == 0 && ("RUB".equals(currCode) || "RUR".equals(currCode)))
			return 3;
		else if (comareTb == 0 && "USD".equals(currCode))
			return 2;
		else if (comareTb == 0 && "EUR".equals(currCode))
			return 1;
			//Если разные тб.
		else
			return -1;
	}
}
