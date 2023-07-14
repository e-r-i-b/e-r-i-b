package com.rssl.phizic.gate.impl.documents;

import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * @author Krenev
 * @ created 27.11.2007
 * @ $Author$
 * @ $Revision$
 */
public class NullDocumentSender implements DocumentSender
{
	public void setParameters(Map<String, ?> params)
	{
		//nothing to do;
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do;
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do;
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		//nothing to do;
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do;
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do;
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do;
	}
}
