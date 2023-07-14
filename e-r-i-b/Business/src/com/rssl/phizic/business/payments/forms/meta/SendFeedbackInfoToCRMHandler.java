package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.ChangeCreditLimitClaim;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lukina
 * @ created 18.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class SendFeedbackInfoToCRMHandler extends BusinessDocumentHandlerBase
	{
		public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
		{
			try
			{
				if ((document instanceof ChangeCreditLimitClaim))
				{
					ChangeCreditLimitClaim extendedLoanClaim = (ChangeCreditLimitClaim) document;
					if (!StringHelper.isEmpty(extendedLoanClaim.getOfferId()))
					{
						OfferId offerId = OfferId.fromString(extendedLoanClaim.getOfferId());
						String feedbackType = extendedLoanClaim.getFeedbackType();
						sendCardOfferPresentation(offerId, FeedbackType.valueOf(feedbackType));
					}
				}
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
		}

	private void sendCardOfferPresentation(OfferId offerId, FeedbackType feedbackType) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<OfferId> offerIds = new ArrayList<OfferId>();
		offerIds.add(offerId);
		personData.sendLoanCardOffersFeedback(offerIds, feedbackType);
	}
}
