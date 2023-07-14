package com.rssl.phizic.business.cardProduct.locale;

import com.rssl.phizic.business.cardProduct.CardProduct;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * Локалезависимый карточный продукт
 * @author komarov
 * @ created 22.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class LocaledCardProduct extends CardProduct
{
	private Set<CardProductResources> resources;

	@Override
	public String getName()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getName();
		return super.getName();
	}
}
