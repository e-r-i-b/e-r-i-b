package com.rssl.phizic.esb.ejb.federal.documents;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.RequestPrivateEarlyRepaymentRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;

import javax.jms.Message;

/**
 * обработчик оффлайн ответа на запрос досрочного погашения кредита
 * @author basharin
 * @ created 07.07.15
 * @ $Author$
 * @ $Revision$
 */

public class EarlyLoanRepaymentProcessor extends ProcessorUpdateDocument<RequestPrivateEarlyRepaymentRs>
{
	private final SimpleService simpleService = new SimpleService();

	@Override
	protected String getExternalId(RequestPrivateEarlyRepaymentRs message, Message source)
	{
		return ExternalIdGenerator.generateExternalId(message);
	}

	@Override
	protected StatusType getStatus(RequestPrivateEarlyRepaymentRs message)
	{
		return message.getStatus();
	}

	@Override
	protected String getType(RequestPrivateEarlyRepaymentRs message)
	{
		return message.getClass().getSimpleName();
	}

	@Override
	protected String getId(RequestPrivateEarlyRepaymentRs message)
	{
		return message.getRqUID();
	}

	@Override
	protected void updateDocument(SynchronizableDocument document, RequestPrivateEarlyRepaymentRs message) throws GateLogicException, GateException
	{
		if (document == null)
			return;

		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		updateDocumentService.createDocumentOrder(document.getExternalId(), document.getId(), message.getPrivateEarlyTerminationResult().getProdId());
	}
}
