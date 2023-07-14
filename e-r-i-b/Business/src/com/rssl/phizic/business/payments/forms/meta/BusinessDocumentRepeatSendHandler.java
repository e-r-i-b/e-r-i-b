package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.*;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.exceptions.*;

/**
 * Хендлер для повторной отправки документа
 * @author niculichev
 * @ created 19.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class BusinessDocumentRepeatSendHandler extends BusinessDocumentHandlerBase
{

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ConvertableToGateDocument))
			throw new DocumentException("Ожидается наследник ConvertableToGateDocument");

		DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
		try
		{
			documentService.repeatSend(((ConvertableToGateDocument)document).asGateDocument());
		}
		catch (TemporalGateException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (RedirectGateLogicException e)
		{
			throw new RedirectDocumentLogicException(e);
		}
		catch (GateTimeOutException e)
		{
			throw new DocumentTimeOutException(e);
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e.getMessage(), e);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
