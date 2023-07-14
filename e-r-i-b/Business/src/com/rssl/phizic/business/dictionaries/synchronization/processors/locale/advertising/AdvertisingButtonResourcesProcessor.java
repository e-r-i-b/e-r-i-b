package com.rssl.phizic.business.dictionaries.synchronization.processors.locale.advertising;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.advertising.locale.AdvertisingButtonResources;
import com.rssl.phizic.business.dictionaries.synchronization.processors.locale.ResourcesProcessorBase;

/**
 * @author komarov
 * @ created 01.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class AdvertisingButtonResourcesProcessor extends ResourcesProcessorBase<AdvertisingButtonResources>
{
	@Override
	protected Class<AdvertisingButtonResources> getEntityClass()
	{
		return AdvertisingButtonResources.class;
	}

	@Override
	protected AdvertisingButtonResources getNewEntity()
	{
		return new AdvertisingButtonResources();
	}

	@Override protected void update(AdvertisingButtonResources source, AdvertisingButtonResources destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setTitle(source.getTitle());
	}
}
