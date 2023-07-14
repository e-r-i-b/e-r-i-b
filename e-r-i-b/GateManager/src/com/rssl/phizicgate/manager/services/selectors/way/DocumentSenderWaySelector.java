package com.rssl.phizicgate.manager.services.selectors.way;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * @author akrenev
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * селектор метода доставки сообщений работы с документами во внешнюю систему
 */

public class DocumentSenderWaySelector extends AbstractWaySelector<DocumentSender> implements DocumentSender
{
	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public DocumentSenderWaySelector(GateFactory factory)
	{
		super(factory);
	}

	@Override
	protected void initializeDelegate(DocumentSender delegate, Map<String, ?> params)
	{
		delegate.setParameters(params);
	}

	@Override
	protected Class getServiceType()
	{
		return WaySelectorHelper.DOCUMENT_SENDER;
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		getServiceDelegate(document).send(document);
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		getServiceDelegate(document).repeatSend(document);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		getServiceDelegate(document).prepare(document);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		getServiceDelegate(document).rollback(document);
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		getServiceDelegate(document).confirm(document);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		getServiceDelegate(document).validate(document);
	}
}
