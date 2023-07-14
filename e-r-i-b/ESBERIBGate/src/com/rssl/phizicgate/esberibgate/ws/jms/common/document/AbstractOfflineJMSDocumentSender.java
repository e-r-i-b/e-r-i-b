package com.rssl.phizicgate.esberibgate.ws.jms.common.document;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.esberibgate.ws.jms.common.sender.OfflineJMSSender;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 23.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Базовый сендер оффлайн документов в шину через jms
 */

public abstract class AbstractOfflineJMSDocumentSender extends OfflineJMSSender implements DocumentSender
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private Map<String, ?> parameters = new HashMap<String, Object>();

	protected AbstractOfflineJMSDocumentSender(GateFactory factory)
	{
		super(factory);
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
