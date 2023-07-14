package com.rssl.common.forms.doc;

import com.rssl.common.forms.state.StateObject;

import java.util.Map;

/**
 *
 * @author khudyakov
 * @ created 03.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class NullHandlerFilter<SO extends StateObject> implements HandlerFilter<SO>
{
	public boolean isEnabled(StateObject stateObject)
	{
		return true;
	}

	public Map<String, String> getParameters()
	{
		return null;
	}

	public void setParameters(Map<String, String> parameters)
	{

	}
}
