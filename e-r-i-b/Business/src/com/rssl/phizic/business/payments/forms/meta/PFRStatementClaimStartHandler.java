package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.business.documents.PFRStatementClaimSendMethod;

/**
 * @author Erkin
 * @ created 10.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class PFRStatementClaimStartHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof PFRStatementClaim))
			throw new DocumentException("Неверный тип платежа. Ожидается PFRStatementClaim");

		PFRStatementClaim claim = (PFRStatementClaim) document;
		PFRStatementClaimHelper.prepareClaim(claim, PFRStatementClaimSendMethod.DEFAULT);
	}
}
