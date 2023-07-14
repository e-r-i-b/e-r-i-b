package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.loans.products.Publicity;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import org.apache.commons.collections.Predicate;

/**
 * @author Jatsky
 * @ created 31.01.14
 * @ $Author$
 * @ $Revision$
 */

public class LoanCardClaimSourceFilter implements Restriction, Predicate
{
	public boolean evaluate(Object object)
	{
		if (object instanceof CreditCardProduct)
		{
			return accept((CreditCardProduct) object);
		}
		else
			return false;
	}

	public boolean accept(CreditCardProduct creditCardProduct)
	{
		return creditCardProduct.getPublicity() == Publicity.PUBLISHED && creditCardProduct.getUseForPreapprovedOffers();
	}
}
