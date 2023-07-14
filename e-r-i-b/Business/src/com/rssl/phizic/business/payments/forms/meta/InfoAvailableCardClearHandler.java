package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.RurPayment;

/**
 * @author basharin
 * @ created 26.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class InfoAvailableCardClearHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof RurPayment))
			return;

		RurPayment rurPayment = (RurPayment) document;
		if (RurPayment.OUR_ACCOUNT_TYPE_VALUE.equals(rurPayment.getReceiverSubType())
				&& rurPayment.getReceiverAccount().indexOf("40817") == 0
				&& !rurPayment.isLongOffer())
		{
			rurPayment.setInfoAvailableCardWasShow(false);
		}
	}
}
