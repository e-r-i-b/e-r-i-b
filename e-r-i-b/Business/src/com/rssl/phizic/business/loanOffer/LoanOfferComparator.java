package com.rssl.phizic.business.loanOffer;

import com.rssl.phizic.business.loans.CurrencyRurUsdEuroComparator;
import com.rssl.phizic.business.offers.LoanOffer;

import java.util.Comparator;

/**
 * @author Mescheryakova
 * @ created 10.06.2011
 * @ $Author$
 * @ $Revision$
 *
 * Сортирует список предодобренных кредитов по валюте для каждого типа,
 * а для каждой валюты еще и по сумме от меньшего значения к большему
 * предполагается что список уже отсортирован по типу 
 */

public class LoanOfferComparator implements Comparator<LoanOffer>
{
	public int compare(LoanOffer loanOffer1, LoanOffer loanOffer2)
	{
		CurrencyRurUsdEuroComparator currencyComparator = new CurrencyRurUsdEuroComparator();

		// в рамках каждого продукта сортируем по валюте, а в рамках каждой валюты сортируем по сумме 
		if (loanOffer1.getProductName().equals(loanOffer2.getProductName()))
		{
			if (loanOffer1.getMaxLimit().getCurrency().getCode().equals(loanOffer2.getMaxLimit().getCurrency().getCode()))
				return (loanOffer1.getMaxLimit().getDecimal().floatValue() >   loanOffer2.getMaxLimit().getDecimal().floatValue()) ? 1 : -1;
			else
				return currencyComparator.compare(loanOffer1.getMaxLimit().getCurrency(), loanOffer2.getMaxLimit().getCurrency());
		}
		return 0;
	}
}
