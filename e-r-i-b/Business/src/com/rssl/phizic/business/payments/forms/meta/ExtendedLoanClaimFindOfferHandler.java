package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.OfferCondition;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.context.PersonContext;

import java.util.List;

/**
 * @author Nady
 * @ created 10.02.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * ’ендлер по поиску предодобренного предложени€ у клиента, по выбранным параметрам кредита на общих услови€х
 */
public class ExtendedLoanClaimFindOfferHandler extends BusinessDocumentHandlerBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExtendedLoanClaim))
			throw new DocumentException("Ќеверный тип платежа id=" + document.getId() + " (ќжидаетс€ ExtendedLoanClaim)");

		ExtendedLoanClaim extendedLoanClaim = (ExtendedLoanClaim) document;

		if (PersonContext.getPersonDataProvider().getPersonData().isGuest())
			return;

		if (!extendedLoanClaim.isUseLoanOffer())
		{
			try
			{
				List<LoanOffer> loanOffers = PersonContext.getPersonDataProvider().getPersonData().getLoanOfferByPersonData(null, null);
				for (LoanOffer offer : loanOffers)
				{
					for (OfferCondition condition : offer.getConditions())
					{
						if (condition.getPeriod() == extendedLoanClaim.getLoanPeriod())
						{
							int result = condition.getAmount().compareTo(extendedLoanClaim.getLoanAmount().getDecimal());
							if (result == 1 || result == 0)
							{
								extendedLoanClaim.setLoanSubProductCode(offer.getSubProductCode());
								businessDocumentService.addOrUpdate(extendedLoanClaim);
								break;
							}
						}
					}
				}
			}
			catch (BusinessException e)
			{
				return;
			}
		}

	}
}
