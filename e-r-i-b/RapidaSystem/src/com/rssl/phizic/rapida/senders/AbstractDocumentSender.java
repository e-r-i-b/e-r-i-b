package com.rssl.phizic.rapida.senders;

import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Egorova
 * @ created 02.12.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractDocumentSender implements DocumentSender
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

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Возможность отзыва платежа не реализована");
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Возможность подтверждения платежа не реализована");
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Возможность валидации платежа не реализована");
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Возможность повторной отправки платежа не реализована");
	}
}
