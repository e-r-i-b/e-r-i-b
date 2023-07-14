package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.impl.MessageHeadImpl;

/**
 * @author Roshka
 * @ created 13.10.2006
 * @ $Author$
 * @ $Revision$
 */
public abstract class CODPaymentSenderBase extends BusinessDocumentHandlerBase
{
	GateMessage createRequest(String requestName) throws GateException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return serviceFacade.createRequest(requestName);
	}

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);

		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			GateMessage gateMessage = buildRequest(personData, (BusinessDocument) document);
			serviceFacade.sendOfflineMessage(gateMessage, new MessageHeadImpl(null, null, null, ((BusinessDocument)document).getId().toString(), null, null));
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	abstract protected GateMessage buildRequest(PersonData personData, BusinessDocument payment) throws GateException;
}
