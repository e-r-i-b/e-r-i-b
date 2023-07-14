package com.rssl.phizic.business.dictionaries.synchronization.processors.locale.service.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.locale.ServiceProviderResources;
import com.rssl.phizic.business.dictionaries.synchronization.processors.locale.ResourcesProcessorBase;

/**
 * @author komarov
 * @ created 07.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderResourcesProcessor extends ResourcesProcessorBase<ServiceProviderResources>
{
	@Override
	protected Class<ServiceProviderResources> getEntityClass()
	{
		return ServiceProviderResources.class;
	}

	@Override
	protected ServiceProviderResources getNewEntity()
	{
		return new ServiceProviderResources();
	}
	@Override
	protected void update(ServiceProviderResources source, ServiceProviderResources destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
		destination.setLegalName(source.getLegalName());
		destination.setAlias(source.getAlias());
		destination.setBankName(source.getBankName());
		destination.setDescription(source.getDescription());
		destination.setTipOfProvider(source.getTipOfProvider());
		destination.setCommissionMessage(source.getCommissionMessage());
		destination.setNameOnBill(source.getNameOnBill());
	}

}
