package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.logging.LogThreadContext;

/**
 * @author Balovtsev
 * @since 23.04.2015.
 */
public class LoanCardClaimFillHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		BusinessDocument businessDocument = (BusinessDocument) document;
		businessDocument.setSessionId(LogThreadContext.getSessionId());
		businessDocument.setConfirmStrategyType(ConfirmStrategyType.none);
	}
}
