package com.rssl.phizic.business.creditcards.products.locale;


import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * Ћокалезависимый кредитный карточный продукт
 * @author komarov
 * @ created 22.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class LocaledCreditCardProduct extends CreditCardProduct
{
	private Set<CreditCardProductResources> resources;

	@Override
	public String getName()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getName();
		return super.getName();
	}

	@Override
	public String getAdditionalTerms()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getAdditionalTerms();
		return super.getAdditionalTerms();
	}
}
