package com.rssl.phizic.business.payments.forms.meta;

/**
 * @author Nady
 * @ created 24.04.2015
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.GetPersonOffers;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;

/**
 * Хендлер получения предложений по задаче "Кредитование УКО"
 * Отправляет запрос за предодобренными предложениями по кредитам / кредитным картам [в CRM].
 */
public class ExtendedLoanClaimGetOffersHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof ExtendedLoanClaim))
			throw new DocumentException("Неверный тип платежа id=" + document.getId() + " (Ожидается ExtendedLoanClaim)");
		GetPersonOffers getPersonOffers = new GetPersonOffers();
		getPersonOffers.execute();
	}
}
