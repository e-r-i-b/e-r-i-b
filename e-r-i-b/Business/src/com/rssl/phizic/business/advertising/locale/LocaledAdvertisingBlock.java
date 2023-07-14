package com.rssl.phizic.business.advertising.locale;

import com.rssl.phizic.business.advertising.AdvertisingBlock;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * @author mihaylov
 * @ created 19.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Баннер с локалезависимыми текстовками
 */

public class LocaledAdvertisingBlock extends AdvertisingBlock
{
	private Set<AdvertisingBlockResources> resources;

	@Override
	public String getTitle()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getTitle();
		return super.getTitle();
	}

	@Override
	public String getText()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getText();
		return super.getText();
	}
}
