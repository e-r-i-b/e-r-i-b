package com.rssl.phizic.business.services;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.person.Person;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 03.04.2006
 * @ $Author: gladishev $
 * @ $Revision: 61544 $
 */

public class ServiceService extends MultiInstanceServiceService
{

	public void add(Service service) throws BusinessException
	{
		super.add(service, null);
	}

	public List<Service> findByCategory(final String category) throws BusinessException
	{
		return super.findByCategory(category, null);
	}

	public Service findById(final Long serviceId) throws BusinessException
	{
		return super.findById(serviceId, null);
	}

	public Service findByKey(final String key) throws BusinessException
	{
		return super.findByKey(key, null);
	}

	public List<Service> getAll() throws BusinessException
	{
		return super.getAll(null);
	}

	public List<Service> getPersonServices(Person person) throws BusinessException
	{
		return super.getPersonServices(person, null);
	}

	public Boolean isPersonServices(Long loginId,String service) throws BusinessException
	{
		return super.isPersonServices(loginId,service, null);
	}

	public List<ServiceOperationDescriptor> getServiceOperations(final Service service) throws BusinessException
	{
		return super.getServiceOperations(service, null);
	}

	public void remove(final Service service, final boolean ignoreUsages) throws BusinessLogicException, BusinessException
	{
		super.remove(service, ignoreUsages, null);
	}

	public void remove(final Service service) throws BusinessLogicException, BusinessException
	{
		super.remove(service, null);
	}

	public void update(Service service) throws BusinessException
	{
		super.update(service, null);    
	}
}