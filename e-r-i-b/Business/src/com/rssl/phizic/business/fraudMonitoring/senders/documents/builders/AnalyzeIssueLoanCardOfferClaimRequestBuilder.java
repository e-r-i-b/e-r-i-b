package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.LoanCardOfferClaim;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * Билдер запроса для заявки на открытие карты LoanCardOfferClaim
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeIssueLoanCardOfferClaimRequestBuilder extends AnalyzeIssueCardClaimRequestBuilderBase<LoanCardOfferClaim>
{
	private LoanCardOfferClaim document;

	@Override
	public AnalyzeIssueLoanCardOfferClaimRequestBuilder append(LoanCardOfferClaim document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected LoanCardOfferClaim getBusinessDocument()
	{
		return document;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType() throws GateException
	{
		return ClientDefinedEventType.ISSUE_CARD;
	}
}
