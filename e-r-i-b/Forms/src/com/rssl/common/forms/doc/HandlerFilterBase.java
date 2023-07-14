package com.rssl.common.forms.doc;

import com.rssl.common.forms.state.StateObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Krenev
 * @ created 14.01.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class HandlerFilterBase<SO extends StateObject> implements HandlerFilter<SO>
{
	private Map<String, String> parameters = new HashMap<String, String>();

	public void setParameters(Map<String, String> parameters)
	{
		this.parameters = parameters;
	}

	public Map<String, String> getParameters()
	{
		return parameters;
	}

	protected String getParameter(String name)
	{
		return parameters.get(name);
	}
}
