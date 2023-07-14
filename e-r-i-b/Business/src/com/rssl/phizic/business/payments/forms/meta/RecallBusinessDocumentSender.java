package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.DocumentTimeOutException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;

/**
 * @author malafeevsky
 * @ created 01.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class RecallBusinessDocumentSender extends BusinessDocumentHandlerBase
{
	private static final DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ConvertableToGateDocument))
			throw new DocumentException("Ожидается наследник ConvertableToGateDocument");
		try
		{
			documentService.recall((GateDocument) document);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (GateWrapperTimeOutException e)
		{
			throw new DocumentTimeOutException(e);
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e.getMessage());
		}
	}
}
