package com.rssl.phizic.business.statemachine;

import java.util.Map;

/**
 * @author hudyakov
 * @ created 03.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class EnabledSource
{
	private String className;
	private Map<String, String> parameters;

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public Map<String, String> getParameters()
	{
		return parameters;
	}

	public void setParameters(Map<String, String> parameters)
	{
		this.parameters = parameters;
	}
}
