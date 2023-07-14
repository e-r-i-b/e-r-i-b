package com.rssl.phizicgate.esberibgate.ws.jms.common.document;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DocumentSender;
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
 * @ created 19.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Базовый сендер онлайн документов в шину через jms
 */

public abstract class AbstractOnlineJMSDocumentSender extends OnlineJMSSender implements DocumentSender
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private Map<String, ?> parameters = new HashMap<String, Object>();

	protected AbstractOnlineJMSDocumentSender(GateFactory factory)
	{
		super(factory);
	}

	protected final String getServiceName(GateDocument document)
	{
		return ServiceMapping.getServiceName(document, ServiceMapping.DOCUMENT_SENDER);
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
