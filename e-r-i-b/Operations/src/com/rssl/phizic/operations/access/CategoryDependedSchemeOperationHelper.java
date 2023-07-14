package com.rssl.phizic.operations.access;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.operations.scheme.SchemeOperationHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 19.01.2006
 * @ $Author: gololobov $
 * @ $Revision: 50482 $
 */
public class CategoryDependedSchemeOperationHelper implements SchemeOperationHelper
{
	private static final ServiceService serviceService = new ServiceService();

	private List<Service>      services;
	private Map<Long, Service> servicesMap;
	private String             category;
	private boolean            caAdmin = false;

	/**
	 * ctor
	 * @param category категория (AccessCategory) для которой выбираются услуги
	 */
	public CategoryDependedSchemeOperationHelper(String category, boolean isCaAdmin)
	{
		this.category = category;
		this.caAdmin = isCaAdmin;
	}

	public List<Service> getServices() throws BusinessException
	{
		if (services == null)
		{
			initialize();
		}
		return services;
	}

	public Service findById(Long serviceId) throws BusinessException
	{
		if (servicesMap == null)
		{
			initialize();
		}
		return servicesMap.get(serviceId);
	}

	public String getCategory()
	{
		return category;
	}

	public boolean isCaAdmin()
	{
		return caAdmin;
	}

	private void initialize() throws BusinessException
	{
		services = serviceService.findByCategory(category);
		servicesMap = new HashMap<Long, Service>();
		for (Service service : services)
		{
			servicesMap.put(service.getId(), service);
		}
	}
}
