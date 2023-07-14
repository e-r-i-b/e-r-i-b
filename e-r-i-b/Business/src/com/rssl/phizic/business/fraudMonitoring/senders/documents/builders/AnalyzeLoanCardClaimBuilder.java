package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * Created with IntelliJ IDEA.
 * User: tisov
 * Date: 20.08.15
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class AnalyzeLoanCardClaimBuilder  extends AnalyzeIssueCardClaimRequestBuilderBase<LoanCardClaim>
{

	private LoanCardClaim document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(LoanCardClaim document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected LoanCardClaim getBusinessDocument()
	{
		return this.document;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType() throws GateException
	{
		return ClientDefinedEventType.ISSUE_CARD;
	}
}
