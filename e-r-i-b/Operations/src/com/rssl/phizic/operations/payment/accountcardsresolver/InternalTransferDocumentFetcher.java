package com.rssl.phizic.operations.payment.accountcardsresolver;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;

/**
 * @author potehin
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class InternalTransferDocumentFetcher extends AccountsCardsDocumentFetcherBase
{
	public boolean isDocumentSupported(Object document)
	{
		return document instanceof InternalTransfer;
	}

	public ExternalResourceLink getCreditResourceLink(BusinessDocument document) throws DocumentException
	{
		return ((InternalTransfer)document).getChargeOffResourceLink();
	}

	public ExternalResourceLink getDebitResourceLink(BusinessDocument document) throws DocumentException
	{
		return ((InternalTransfer)document).getDestinationResourceLink();
	}
}
