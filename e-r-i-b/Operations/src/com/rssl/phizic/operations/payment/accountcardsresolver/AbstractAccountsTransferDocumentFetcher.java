package com.rssl.phizic.operations.payment.accountcardsresolver;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;

/**
 * @author potehin
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class AbstractAccountsTransferDocumentFetcher implements AccountsCardsDocumentFetcher
{
	public boolean isDocumentSupported(Object document)
	{
		return document instanceof AbstractAccountsTransfer;
	}

	public ExternalResourceLink getCreditResourceLink(BusinessDocument document) throws DocumentException
	{
		return ((AbstractAccountsTransfer)document).getChargeOffResourceLink();
	}

	public ExternalResourceLink getDebitResourceLink(BusinessDocument document) throws DocumentException
	{
		return ((AbstractAccountsTransfer)document).getDestinationResourceLink();
	}
}
