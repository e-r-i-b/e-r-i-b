package com.rssl.phizic.business.advertising.locale;

import com.rssl.phizic.business.advertising.AdvertisingButton;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * @author mihaylov
 * @ created 19.09.14
 * @ $Author$
 * @ $Revision$
 *
 *  нопка баннера с локалезависимыми текстовками
 */
public class LocaledAdvertisingButton extends AdvertisingButton
{
	private Set<AdvertisingButtonResources> resources;

	@Override
	public String getTitle()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getTitle();
		return super.getTitle();
	}
}
