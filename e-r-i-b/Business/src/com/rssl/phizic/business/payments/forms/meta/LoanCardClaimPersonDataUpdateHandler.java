package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

/**
 * ’ендлер, обновл€ющий персон-дату
 * @author Rtischeva
 * @ created 14.05.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanCardClaimPersonDataUpdateHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!PersonContext.isAvailable())
			return;

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		personData.setLoanCardClaimAvailable(false);
	}
}
