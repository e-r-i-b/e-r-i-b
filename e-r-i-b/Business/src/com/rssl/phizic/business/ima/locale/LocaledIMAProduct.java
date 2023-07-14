package com.rssl.phizic.business.ima.locale;

import com.rssl.phizic.business.ima.IMAProduct;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * Локализованное описание обезличенного металлического счета
 * @author lepihina
 * @ created 12.06.15
 * $Author$
 * $Revision$
 */
public class LocaledIMAProduct extends IMAProduct
{
	private Set<IMAProductResources> resources;

	@Override
	public String getName()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getName();
		return super.getName();
	}
}
