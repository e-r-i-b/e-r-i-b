package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.LoanCardProductClaim;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * Билдер запроса для заявки на открытие карты LoanCardProductClaim
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeIssueLoanCardProductClaimRequestBuilder extends AnalyzeIssueCardClaimRequestBuilderBase<LoanCardProductClaim>
{
	private LoanCardProductClaim document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(LoanCardProductClaim document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected LoanCardProductClaim getBusinessDocument()
	{
		return document;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType() throws GateException
	{
		return ClientDefinedEventType.ISSUE_CARD;
	}
}
