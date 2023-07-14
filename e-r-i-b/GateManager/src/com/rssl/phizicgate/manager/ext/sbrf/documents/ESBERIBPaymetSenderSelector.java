package com.rssl.phizicgate.manager.ext.sbrf.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * @author krenev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 * сендер-селектор.
 * всегда дергает делегатов. правила получения делегата по документу определяется в getDelegate.
 */
public class ESBERIBPaymetSenderSelector extends ESBERIBPaymetDelegateSelector<DocumentSender> implements DocumentSender
{
	public ESBERIBPaymetSenderSelector(GateFactory factory)
	{
		super(factory);
	}

	@Override
	public void setParameters(Map<String, ?> params)
	{
		super.setParameters(params);
		esbDelegate.setParameters(params);
		defaultDelegate.setParameters(params);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate(document).send(document);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate(document).prepare(document);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		getDelegate(document).rollback(document);
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate(document).confirm(document);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate(document).validate(document);
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate(document).repeatSend(document);
	}
}
