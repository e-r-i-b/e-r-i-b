package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.web.schemes.SchemeUtils;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

/**
 * @author Omeliyanchuk
 * @ created 15.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class AccessEmpoweredPersonSubForm
{
	// выбранные сервисы (их id)
	private Long[]                                  selectedServices = new Long[0];
	// доступность accessType
	private boolean                                 enabled;
	// параметры политики безопасности
	private Map<String, Object> properties       = new HashMap<String, Object>();
	// справочные данные
	// политика безопасности ассоциированна€ с данными настройками прав
	private AccessPolicy policy;
	// ќперации по сервисам
	private Map<Service, List<OperationDescriptor>> operationsByServiceMap;
	// Cервисы
	private List<Service> services;

	public Comparator<Service> getServicesComparator()
	{
		return SchemeUtils.SERVICES_COMPARATOR;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public Map<Service, List<OperationDescriptor>> getOperationsByServiceMap()
	{
		return operationsByServiceMap;
	}

	public void setOperationsByServiceMap(Map<Service, List<OperationDescriptor>> operationsByServiceMap)
	{
		this.operationsByServiceMap = operationsByServiceMap;
	}

	public AccessPolicy getPolicy()
	{
		return policy;
	}

	public void setPolicy(AccessPolicy policy)
	{
		this.policy = policy;
	}

	public Map<String, Object> getProperties()
	{
		return properties;
	}

	public void setProperties(Map<String, Object> properties)
	{
		this.properties = properties;
	}

	public Long[] getSelectedServices()
	{
		return selectedServices;
	}

	public void setSelectedServices(Long[] selectedServices)
	{
		this.selectedServices = selectedServices;
	}

	public List<Service> getServices()
	{
		return services;
	}

	public void setServices(List<Service> services)
	{
		this.services = services;
	}
}
