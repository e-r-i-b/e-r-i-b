package com.rssl.phizic.business.documents.payments.stateMachine.handlers;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.ext.sbrf.mobilebank.CheckChargeOffCardToMobilBankHandler;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author khudyakov
 * @ created 10.11.14
 * @ $Author$
 * @ $Revision$
 */
public class P2PCheckChargeOffCardToMobilBankHandler extends CheckChargeOffCardToMobilBankHandler
{
	protected void showMessage(AbstractLongOfferDocument document, StateMachineEvent stateMachineEvent)
	{
		if (!document.isAlreadyShowInactiveMBKWarning())
		{
			super.showMessage(document, stateMachineEvent);
			document.setAlreadyShowInactiveMBKWarning(true);
		}
	}
}
