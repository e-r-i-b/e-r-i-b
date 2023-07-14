package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonOperationMode;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.person.Person;

import java.util.*;

/**
 * @author Evgrafov
 * @ created 25.07.2006
 * @ $Author: erkin $
 * @ $Revision: 48493 $
 */

public class EditEmpoweredAccessOperation extends PersonOperationBase
{
	private List<Long>              newServiceIds;    // id новых услуг
	private Boolean                 isAllowed;
	private Properties              newProperties;

	private EmpoweredAccessModifier accessModifier; // логика изменения прав

	public void initialize(ActivePerson person, ActivePerson trustingPerson, AccessType accessType) throws BusinessLogicException, BusinessException
	{
		setPersonId(trustingPerson.getId());

		if(!Person.ACTIVE.equals(person.getStatus()))
	        setMode(PersonOperationMode.direct);

		if (!trustingPerson.getId().equals(person.getTrustingPersonId()))
		{
			throw new BusinessException("person не является представителем");
		}

		initializeInternal(person, trustingPerson, accessType);
	}

	public ActivePerson getEmpoweredPerson()
	{
		return accessModifier.getPerson();
	}

	private void initializeInternal(ActivePerson person, ActivePerson trustingPerson, AccessType accessType) throws BusinessException, BusinessLogicException
	{
		accessModifier = new EmpoweredAccessModifier(person, trustingPerson, accessType, getInstanceName());
	}

	public List<Service> getTrustingServices() throws BusinessException, BusinessLogicException
	{
		return accessModifier.getTrustingServices();
	}
	/*
	получит доступные(разрешенные) сервисы
	 */
	public List<Service> getCurrentServices()
	{
		return accessModifier.getCurrentServices();
	}

	public void setNewServices(List<Long> serviceIds)
	{
		newServiceIds = new ArrayList<Long>(serviceIds);
	}

	public Properties getProperties()
	{
		return accessModifier.getProperties();
	}

	public void setProperties(Properties properties)
	{
		this.newProperties = properties;
	}


	public AccessPolicy getAccessPolicy()
	{
		return accessModifier.getPolicy();
	}

	public boolean isCurrentAccessAllowed()
	{
		return accessModifier.isCurrentAccessAllowed();
	}

	public void setCurrentAccessAllowed(Boolean isAllowed)
	{
		this.isAllowed = isAllowed;
	}

	public Map<Service, List<OperationDescriptor>> getServiceOperationDescriptors()
	{
		return accessModifier.getServiceOperationDescriptors();
	}

	public void save() throws BusinessException
	{
		Map<Long, Service> all = new HashMap<Long, Service>();

		for (Service service : accessModifier.getTrustingServices())
		{
			all.put(service.getId(), service);
		}

		final List<Service> newServices = new ArrayList<Service>();

		for (Long serviceId : newServiceIds)
		{
			Service service = all.get(serviceId);
			if(service != null)
				newServices.add(service);
		}

		accessModifier.setInstanceName(getInstanceName());
		accessModifier.change(newServices, isAllowed, newProperties);
	}
}