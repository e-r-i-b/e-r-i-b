package com.rssl.phizicgate.manager.config;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 20.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateDescription
{
	private List<ServiceDescriptor> services = new ArrayList<ServiceDescriptor>();

	public List<ServiceDescriptor> getServices()
	{
		return services;
	}

	public void addServiceDescriptor(ServiceDescriptor serviceDescriptor)
	{
		services.add(serviceDescriptor);
	}
}
