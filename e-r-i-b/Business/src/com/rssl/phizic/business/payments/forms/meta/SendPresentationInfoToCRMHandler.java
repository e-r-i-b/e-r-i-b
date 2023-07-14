package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.documents.payments.LoanOfferClaim;
import com.rssl.phizic.business.documents.payments.ShortLoanClaim;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Moshenko
 * Date: 23.01.15
 * Time: 12:54
 * Отправлям запрос "Презенатция" в CRM для Для LoanOffer и LoanCardClaim
 */
public class SendPresentationInfoToCRMHandler  extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		try
		{
			if ((document instanceof ShortLoanClaim))
			{
				ShortLoanClaim extendedLoanClaim = (ShortLoanClaim) document;
				if (!StringHelper.isEmpty(extendedLoanClaim.getLoanOfferId()))
				{
					OfferId offerId = OfferId.fromString(extendedLoanClaim.getLoanOfferId());
					sendPresentation(offerId);
				}
			}
			else if ((document instanceof LoanOfferClaim))
			{
				LoanOfferClaim loanOffer = (LoanOfferClaim) document;
				if (!StringHelper.isEmpty(loanOffer.getLoan()))
				{
					OfferId offerId = OfferId.fromString(loanOffer.getLoan());
					sendPresentation(offerId);
				}
			}
			else if (document instanceof LoanCardClaim)
			{
				String loanOffer = ((LoanCardClaim) document).getLoan();

				if (!StringHelper.isEmpty(loanOffer))
				{
					OfferId offerId = OfferId.fromString(loanOffer);
					sendCardOfferPresentation(offerId);
				}
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private void sendCardOfferPresentation(OfferId offerId) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<OfferId> offerIds = new ArrayList<OfferId>();
		offerIds.add(offerId);
		personData.sendLoanCardOffersFeedback(offerIds, FeedbackType.PRESENTATION);
	}

	private void sendPresentation(OfferId offerId) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<OfferId> offerIds = new ArrayList<OfferId>();
		offerIds.add(offerId);
		personData.sendLoanOffersFeedback(offerIds, FeedbackType.PRESENTATION);
	}
}
