package com.rssl.phizic.business.services;

import com.rssl.phizic.business.operations.OperationDescriptor;

/**
 * Связка описателя операции и услуги.
 *
 * @author Evgrafov
 * @ created 05.04.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 4221 $
 */

public class ServiceOperationDescriptor
{
	private Long id;
	private Service service;
	private OperationDescriptor operationDescriptor;
	private boolean spread = true;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public OperationDescriptor getOperationDescriptor()
	{
		return operationDescriptor;
	}

	public void setOperationDescriptor(OperationDescriptor operationDescriptor)
	{
		this.operationDescriptor = operationDescriptor;
	}

	public Service getService()
	{
		return service;
	}

	public void setService(Service service)
	{
		this.service = service;
	}

	public boolean isSpread()
	{
		return spread;
	}

	public void setSpread(boolean spread)
	{
		this.spread = spread;
	}
}