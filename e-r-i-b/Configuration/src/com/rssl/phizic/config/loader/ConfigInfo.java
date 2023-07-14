package com.rssl.phizic.config.loader;

import com.rssl.phizic.common.types.Application;

import java.util.Map;
import java.util.HashMap;

/**
 * ���������� � �������.
 *
 * @author bogdanov
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class ConfigInfo
{
	/**
	 * ������� �������.
	 */
	private String instance;
	/**
	 * ���������� �� ���������.
	 */
	private ImplementationInfo defaultImplementation;
	/**
	 * ���������� ��� ����������� ����������.
	 */
	private final Map<Application, ImplementationInfo> implementations = new HashMap<Application, ImplementationInfo>();

	public ImplementationInfo getDefaultImplementation()
	{
		return defaultImplementation;
	}

	public void setDefaultImplementation(ImplementationInfo defaultImplementation)
	{
		this.defaultImplementation = defaultImplementation;
	}

	public ImplementationInfo getImplementation(Application application)
	{
		return implementations.get(application);
	}

	public void addImplementations(Application application, ImplementationInfo implementation)
	{
		implementations.put(application, implementation);
	}

	public String getInstance()
	{
		return instance;
	}

	public void setInstance(String instance)
	{
		this.instance = instance;
	}
}
