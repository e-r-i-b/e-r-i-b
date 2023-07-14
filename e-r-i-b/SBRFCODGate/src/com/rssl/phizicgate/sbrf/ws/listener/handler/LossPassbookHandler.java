package com.rssl.phizicgate.sbrf.ws.listener.handler;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.sbrf.ws.listener.ConfirmOfflineRequestHandler;
import com.rssl.phizicgate.sbrf.ws.listener.InternalMessageInfoContainer;

import java.util.HashMap;

/**
 * @author Gainanov
 * @ created 29.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class LossPassbookHandler implements ConfirmOfflineRequestHandler
{
	public boolean handleMessage(InternalMessageInfoContainer messageInfoContainer, Object object) throws GateException, GateLogicException
	{
		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		updateDocumentService.update((SynchronizableDocument)object, getUpdateCommand(messageInfoContainer));
		return true;
	}

	public DocumentCommand getUpdateCommand(InternalMessageInfoContainer messageInfoContainer)
	{
		return new DocumentCommand(DocumentEvent.EXECUTE, new HashMap<String, Object>());
	}

	public void fillPaymentData(AbstractAccountTransfer document, String messageText) throws GateException
	{
		//nothing
	}
}
