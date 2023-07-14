package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 18.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class PFRStatementClaimFinishHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof PFRStatementClaim))
			throw new IllegalArgumentException("Ожидается заявка на выписку из ПФР (PFRStatementClaim)");

		PFRStatementClaim claim = (PFRStatementClaim) document;

		String error = PFRStatementClaimHelper.getClaimErrorText(claim);
		if (!StringHelper.isEmpty(error))
			claim.setRefusingReason(error);
	}
}
