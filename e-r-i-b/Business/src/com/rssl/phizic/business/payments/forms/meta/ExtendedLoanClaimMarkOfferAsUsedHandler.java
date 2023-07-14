package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Nady
 * @ created 21.03.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Хендлер помечающий предодобренное предложение в расширенной заявке, как использованное
 */
public class ExtendedLoanClaimMarkOfferAsUsedHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof ExtendedLoanClaim))
			throw new DocumentException("Неверный тип платежа id=" + document.getId() + " (Ожидается ExtendedLoanClaim)");

		ExtendedLoanClaim extendedLoanClaim = (ExtendedLoanClaim) document;
		String offerIdAsString = extendedLoanClaim.getLoanOfferId();

		if (StringHelper.isNotEmpty(offerIdAsString))
		{
			OfferId offerId = OfferId.fromString(offerIdAsString);
			try
			{
				PersonContext.getPersonDataProvider().getPersonData().updateLoanOfferAsUsed(offerId);
			}
			catch (BusinessException e)
			{
				log.error("Ошибка при обработке предодобренного предложения - " + offerIdAsString, e);
			}
		}
	}
}
