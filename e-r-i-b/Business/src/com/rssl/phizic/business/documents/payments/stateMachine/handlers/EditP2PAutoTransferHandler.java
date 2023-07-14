package com.rssl.phizic.business.documents.payments.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.P2PAutoTransferClaimBase;

/**
 * ’ендлер выполн€ющийс€ при редактировании P2P автоперевода
 *
 * @author khudyakov
 * @ created 10.11.14
 * @ $Author$
 * @ $Revision$
 */
public class EditP2PAutoTransferHandler extends BusinessDocumentHandlerBase<P2PAutoTransferClaimBase>
{
	public void process(P2PAutoTransferClaimBase autoTransfer, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		autoTransfer.setExternalId(null);
		autoTransfer.setAlreadyShowInactiveMBKWarning(false);
	}
}
