package com.rssl.phizgate.common.payments;

import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.DocumentUpdater;
import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Egorova
 * @ created 25.11.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractDocumentSender implements DocumentSender, DocumentUpdater
{
	private Map<String, ?> parameters = new HashMap<String, Object>();

	public void setParameters(Map<String, ?> params)
	{
		parameters = params;
	}

	protected Object getParameter(String name)
	{
		return parameters.get(name);
	}

	public StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Проверка обновлений документа не поддерживается ");
	}
}
