package com.rssl.phizic.operations.regions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderRegionService;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;

/**
 * Операция для проверки доступности текущему региону поставщика
 * @author niculichev
 * @ created 12.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class CheckProviderRegionOperation extends OperationBase
{
	private static final ServiceProviderRegionService serviceProviderRegionService = new ServiceProviderRegionService();

	/**
	 * доступен ли поставщик для текущего региона
	 * @param providerId - id поставщика
	 * @return - true - доступен
	 */
	public boolean allowedAnyRegions(Long providerId) throws BusinessException
	{
		if(providerId == null)
			throw new BusinessException("Не задан идентификатор поставщика услуг");

		//получаем текущий регион
		Region region = PersonContext.getPersonDataProvider().getPersonData().getCurrentRegion();
		return region == null ? true : serviceProviderRegionService.providerAllowedInRegion(providerId, region.getId());
	}
}
