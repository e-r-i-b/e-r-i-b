package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;

/**
 * @author Nady
 * @ created 21.03.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Хендлер, помечающий предодобренное предложение в расширенной заявке отправленным в работу ТМ
 */
public class ExtendedLoanClaimInWaitTMHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof ExtendedLoanClaim))
			throw new DocumentException("Неверный тип платежа id=" + document.getId() + " (Ожидается ExtendedLoanClaim)");

		ExtendedLoanClaim extendedLoanClaim = (ExtendedLoanClaim) document;

		extendedLoanClaim.setInWaitTM(true);
	}
}
