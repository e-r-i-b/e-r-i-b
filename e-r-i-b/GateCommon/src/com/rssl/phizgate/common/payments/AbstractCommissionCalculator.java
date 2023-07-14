package com.rssl.phizgate.common.payments;

import com.rssl.phizic.gate.documents.CommissionCalculator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 21.03.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractCommissionCalculator implements CommissionCalculator
{
	private Map<String, ?> parameters = new HashMap<String, Object>();

	public void setParameters(Map<String, ?> parameters)
	{
		this.parameters = parameters;
	}

	protected Object getParameter(String name)
	{
		return parameters.get(name);
	}
}
