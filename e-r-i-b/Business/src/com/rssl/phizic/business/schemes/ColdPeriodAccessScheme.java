package com.rssl.phizic.business.schemes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * схема прав для холодного периода
 * @author basharin
 * @ created 23.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ColdPeriodAccessScheme extends PersonalAccessScheme
{
	private List<Service> coldPeriodAvailableServices;

	public ColdPeriodAccessScheme(AccessScheme scheme) throws BusinessException
	{
		List<String> bannedServiceKeys = ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).getColdPeriodBannedServiceKeys();
		coldPeriodAvailableServices = new ArrayList<Service>();
		coldPeriodAvailableServices.addAll(scheme.getServices());
		for (String key : bannedServiceKeys)
		{
			Service findService = null;
			for (Service service : coldPeriodAvailableServices)
				if (key.equals(service.getKey()))
					findService = service;
			if (findService != null)
				coldPeriodAvailableServices.remove(findService);
		}
	}

	public List<Service> getServices()
	{
	    return coldPeriodAvailableServices;
	}
}
