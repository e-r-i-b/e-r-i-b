package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.PreapprovedLoanCardClaim;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * Билдер запроса для заявки на открытие карты PreapprovedLoanCardClaim
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeIssuePreApprovedLoanClaimRequestBuilder extends AnalyzeIssueCardClaimRequestBuilderBase<PreapprovedLoanCardClaim>
{
	private PreapprovedLoanCardClaim document;

	@Override
	public AnalyzeIssuePreApprovedLoanClaimRequestBuilder append(PreapprovedLoanCardClaim document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected PreapprovedLoanCardClaim getBusinessDocument()
	{
		return document;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType() throws GateException
	{
		return ClientDefinedEventType.ISSUE_CARD;
	}
}
