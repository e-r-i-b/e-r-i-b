package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.loanclaim.type.LoanRate;

/**
 * Билдер во ФМ для заявки на открыти кредита (тип analyze)
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeETSMLoanClaimRequestBuilder extends LoanClaimRequestBuilderBase<ExtendedLoanClaim>
{
	private ExtendedLoanClaim document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(ExtendedLoanClaim document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected ExtendedLoanClaim getBusinessDocument()
	{
		return document;
	}

	@Override
	protected Long getLoanPeriod()
	{
		return document.getLoanPeriod();
	}

	@Override
	protected LoanRate getLoanRate()
	{
		return document.getLoanRate();
	}
}
