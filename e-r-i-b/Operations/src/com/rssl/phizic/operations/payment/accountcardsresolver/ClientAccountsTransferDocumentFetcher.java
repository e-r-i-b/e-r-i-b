package com.rssl.phizic.operations.payment.accountcardsresolver;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;

/**
 * @author potehin
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ClientAccountsTransferDocumentFetcher extends AccountsCardsDocumentFetcherBase
{
	public boolean isDocumentSupported(Object document)
	{
		return document instanceof ClientAccountsTransfer;
	}

	public ExternalResourceLink getCreditResourceLink(BusinessDocument document) throws DocumentException
	{
		return null;
	}

	public ExternalResourceLink getDebitResourceLink(BusinessDocument document) throws DocumentException
	{
		ClientAccountsTransfer transfer = (ClientAccountsTransfer) document;
		return findLinkByNumber(document, ResourceType.ACCOUNT, transfer.getReceiverAccount());
	}
}
