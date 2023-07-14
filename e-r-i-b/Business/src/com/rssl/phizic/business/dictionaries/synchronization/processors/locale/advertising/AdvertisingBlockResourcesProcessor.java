package com.rssl.phizic.business.dictionaries.synchronization.processors.locale.advertising;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.advertising.locale.AdvertisingBlockResources;
import com.rssl.phizic.business.dictionaries.synchronization.processors.locale.ResourcesProcessorBase;

/**
 * @author komarov
 * @ created 01.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class AdvertisingBlockResourcesProcessor extends ResourcesProcessorBase<AdvertisingBlockResources>
{
	@Override
	protected Class<AdvertisingBlockResources> getEntityClass()
	{
		return AdvertisingBlockResources.class;
	}

	@Override
	protected AdvertisingBlockResources getNewEntity()
	{
		return new AdvertisingBlockResources();
	}

	@Override
	protected void update(AdvertisingBlockResources source, AdvertisingBlockResources destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setTitle(source.getTitle());
		destination.setText(source.getText());
	}
}
