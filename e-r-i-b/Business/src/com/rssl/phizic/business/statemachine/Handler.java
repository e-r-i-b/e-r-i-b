package com.rssl.phizic.business.statemachine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hudyakov
 * @ created 26.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class Handler
{
	private String className;
	private Map<String, String> parameters = new HashMap<String, String>();
	private EnabledSource enabledSource;

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public void setParameter(String name, String value)
	{
		parameters.put(name, value);
	}

	public String getParameter(String name)
	{
		return parameters.get(name);
	}

	public Map<String, String> getParameters()
	{
		return parameters;
	}
	public void setParameters(Map<String, String> parameters)
	{
		this.parameters = parameters;
	}

	public EnabledSource getEnabledSource()
	{
		return enabledSource;
	}

	public void setEnabledSource(EnabledSource enabledSource)
	{
		this.enabledSource = enabledSource;
	}
}
