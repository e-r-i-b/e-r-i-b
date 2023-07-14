package com.rssl.phizic.web.access;

import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.operations.access.AssignAccessHelper;
import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.web.schemes.SchemeUtils;

import java.util.*;

/**
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: gololobov $
 * @ $Revision: 50482 $
 */

@SuppressWarnings({"JavaDoc", "ReturnOfCollectionOrArrayField", "AssignmentToCollectionOrArrayFieldFromParameter"})
public class AssignAccessSubForm extends ActionFormBase
{
	// Данные, которые вводит пользователь
	// id - схемы прав пользователя или personal или null
	private String                                  accessSchemeId;
	// выбранные сервисы (их id)
	private Long[]                                  selectedServices = new Long[0];
	//Сервисы, доступные только сотрудникам ЦА
	private Long[]                                  caadminServices = new Long[0];
	//Не доступные, но отображаемые для сотрудника сервисы
	private Long[]                                  disabledServices = new Long[0];
	// доступность accessType
	private boolean                                 enabled;
	// параметры политики безопасности
	private Map<String, Object>                     properties       = new HashMap<String, Object>();
	// текущая категория доступа
	private String                                  category;

	// справочные данные
	// политика безопасности ассоциированная с данными настройками прав
	private AccessPolicy                            policy;
	// хелпер для получения данных
	private List<AssignAccessHelper>                helpers;
	// Операции по сервисам
	private Map<Service, List<OperationDescriptor>> operationsByServiceMap;
	// Можно ли назначать Индивидуальные права данному пользователю 
	private boolean                                 denyCustomRights = false;
	// Список схем с доступными сервисами для них	
	private Map<Long, Long[]>                       servicesForSchemes;  

	public void setAccessSchemeId ( String accessSchemeId )
	{
		this.accessSchemeId = accessSchemeId;
	}

	public String getAccessSchemeId ()
	{
		return accessSchemeId;
	}

	public Long[] getSelectedServices ()
	{
		return selectedServices;
	}

    public List<Long> getSelectedServicesList()
    {
        List<Long> selectedServicesList = new ArrayList<Long>(Arrays.asList(selectedServices));
	    if (disabledServices.length > 0)
	    {
		    List<Long> disabledServicesList = new ArrayList<Long>(getDisabledServicesList());
	        selectedServicesList.addAll(disabledServicesList);
	    }
	    return selectedServicesList;
    }

    public void setSelectedServices ( Long[] selectedServices )
	{
		this.selectedServices = selectedServices;
	}

	public Long[] getCaadminServices()
	{
		return caadminServices;
	}

	public void setCaadminServices(Long[] caadminServices)
	{
		this.caadminServices = caadminServices;
	}

	public List<Long> getDisabledServicesList()
	{
		return Arrays.asList(disabledServices);
	}

	public Long[] getDisabledServices()
	{
		return disabledServices;
	}

	public void setDisabledServices(Long[] disabledServices)
	{
		this.disabledServices = disabledServices;
	}

	public boolean getEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
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

	public Comparator<Service> getServicesComparator()
	{
		return SchemeUtils.SERVICES_COMPARATOR;
	}

	public List<AssignAccessHelper> getHelpers()
	{
		return helpers;
	}

	public void setHelpers(List<AssignAccessHelper> helpers)
	{
		this.helpers = helpers;
	}

	public Map<Service, List<OperationDescriptor>> getOperationsByServiceMap()
	{
		return operationsByServiceMap;
	}

	public void setOperationsByServiceMap(Map<Service, List<OperationDescriptor>> operationsByServiceMap)
	{
		this.operationsByServiceMap = operationsByServiceMap;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public boolean getDenyCustomRights()
	{
		return denyCustomRights;
	}

	public void setDenyCustomRights(boolean denyCustomRights)
	{
		this.denyCustomRights = denyCustomRights;
	}

	public Map<Long, Long[]> getServicesForSchemes()
	{
		return servicesForSchemes;
	}

	public void setServicesForSchemes(Map<Long, Long[]> servicesForSchemes)
	{
		this.servicesForSchemes = servicesForSchemes;
	}
}
