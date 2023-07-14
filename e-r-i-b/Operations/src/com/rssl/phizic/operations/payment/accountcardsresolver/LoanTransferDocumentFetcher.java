package com.rssl.phizic.operations.payment.accountcardsresolver;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.gate.payments.LoanTransfer;

/**
 * @author gladishev
 * @ created 24.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanTransferDocumentFetcher extends AccountsCardsDocumentFetcherBase
{
	public boolean isDocumentSupported(Object document)
	{
		return document instanceof LoanTransfer;
	}

	public ExternalResourceLink getCreditResourceLink(BusinessDocument document) throws DocumentException
	{
		LoanTransfer transfer = (LoanTransfer) document;
		return findLinkByNumber(document, ResourceType.CARD, transfer.getChargeOffCard());
	}

	public ExternalResourceLink getDebitResourceLink(BusinessDocument document) throws DocumentException
	{
		LoanTransfer transfer = (LoanTransfer) document;
		return findLinkByNumber(document, ResourceType.LOAN, transfer.getAccountNumber());
	}
}
