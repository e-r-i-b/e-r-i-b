package com.rssl.phizic.business.dictionaries.currencies.locale;

import com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * Курсы валют с локализованными ресурсами
 * @author lepihina
 * @ created 09.06.15
 * $Author$
 * $Revision$
 */
public class LocaledCurrencyImpl extends CurrencyImpl
{
	private Set<CurrencyImplResources> resources;

	public String getName()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getName();
		return super.getName();
	}
}
