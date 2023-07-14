package com.rssl.phizicgate.esberibgate.ws.jms.common.document;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.mapping.ServiceMapping;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.esberibgate.ws.jms.common.sender.OnlineJMSSender;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 15.06.2015
 * @ $Author$
 * @ $Revision$
 *
 *  Базовый калькулятор комиссии
 */

public abstract class AbstractJMSCommissionCalculator extends OnlineJMSSender implements CommissionCalculator
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private Map<String, ?> parameters = new HashMap<String, Object>();

	protected AbstractJMSCommissionCalculator(GateFactory factory)
	{
		super(factory);
	}

	protected final String getServiceName(GateDocument source)
	{
		return ServiceMapping.getServiceName(source, ServiceMapping.COMMISSION_CALCULATOR);
	}

	public void setParameters(Map<String, ?> params)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		parameters = params;
	}

	protected Object getParameter(String name)
	{
		return parameters.get(name);
	}
}
