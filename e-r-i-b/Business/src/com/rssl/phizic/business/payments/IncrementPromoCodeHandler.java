package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.DocumentHelper;

/**
 * @author Jatsky
 * @ created 01.07.15
 * @ $Author$
 * @ $Revision$
 */

public class IncrementPromoCodeHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + (document).getId() + " (Ожидается AccountOpeningClaim)");
		}

		AccountOpeningClaim claim = (AccountOpeningClaim) document;
		try
		{
			DocumentHelper.incrementPromoCode(claim);
			claim.setIncrementedPromoCode(true);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
