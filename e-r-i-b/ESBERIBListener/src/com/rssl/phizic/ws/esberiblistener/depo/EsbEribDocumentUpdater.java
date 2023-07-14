package com.rssl.phizic.ws.esberiblistener.depo;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.phizic.business.documents.AbstractDepoAccountClaim;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.w3c.dom.Document;

/**
 * @author gladishev
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EsbEribDocumentUpdater extends EsbEribDocumentUpdaterBase<SynchronizableDocument>
{
	public EsbEribDocumentUpdater(Document request)
	{
		super(request);
	}

	@Override
	protected SynchronizableDocument getDocument(String documentNumber) throws GateException, GateLogicException
	{
		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		return updateDocumentService.find(documentNumber);
	}

	@Override
	protected void updateDocument(SynchronizableDocument document, DocumentCommand command) throws GateException, GateLogicException
	{
		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		updateDocumentService.update(document, command);
	}

	@Override
	protected boolean checkDocument(SynchronizableDocument document)
	{
		return document.getState().equals(new State("DISPATCHED")) && (document instanceof AbstractDepoAccountClaim);
	}
}
