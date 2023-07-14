package com.rssl.phizic.business.dictionaries.synchronization.processors.locale.regions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.regions.locale.RegionResources;
import com.rssl.phizic.business.dictionaries.synchronization.processors.locale.ResourcesProcessorBase;

/**
 * @author koptyaev
 * @ created 02.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class RegionResourcesProcessor extends ResourcesProcessorBase<RegionResources>
{
	@Override
	protected Class<RegionResources> getEntityClass()
	{
		return RegionResources.class;
	}

	@Override
	protected RegionResources getNewEntity()
	{
		return new RegionResources();
	}

	@Override
	protected void update(RegionResources source, RegionResources destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
	}
}
