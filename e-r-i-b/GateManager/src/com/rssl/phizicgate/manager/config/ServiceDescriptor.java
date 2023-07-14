package com.rssl.phizicgate.manager.config;

/**
 * @author Krenev
 * @ created 20.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class ServiceDescriptor
{
	private String interfaceClass;
	private String realisationClass;

	public ServiceDescriptor(String interfaceClass, String realisationClass)
	{
		this.interfaceClass = interfaceClass;
		this.realisationClass = realisationClass;
	}

	public String getInterfaceClass()
	{
		return interfaceClass;
	}

	public String getRealisationClass()
	{
		return realisationClass;
	}

	public String addParameter(String name, String value)
	{
		return null;//TODO realize
	}
}
