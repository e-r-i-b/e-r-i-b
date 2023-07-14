package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;

/**
 * хендлер, который устанавливает что произошла конверсионная операция.
 *
 * @author bogdanov
 * @ created 06.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class SetConversionOperationHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
	    AbstractAccountsTransfer transfer = (AbstractAccountsTransfer) document;
		transfer.setIsConversionOperation(true);
	}
}
