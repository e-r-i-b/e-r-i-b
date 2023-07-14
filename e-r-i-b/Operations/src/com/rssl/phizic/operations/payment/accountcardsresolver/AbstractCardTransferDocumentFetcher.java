package com.rssl.phizic.operations.payment.accountcardsresolver;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.gate.documents.AbstractCardTransfer;

/**
 * @author potehin
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class AbstractCardTransferDocumentFetcher extends AccountsCardsDocumentFetcherBase
{
	public boolean isDocumentSupported(Object document)
	{
		return document instanceof AbstractCardTransfer;
	}

	public ExternalResourceLink getCreditResourceLink(BusinessDocument document) throws DocumentException
	{
		AbstractCardTransfer cardTransfer = (AbstractCardTransfer) document;
		return findLinkByNumber(document, ResourceType.CARD, cardTransfer.getChargeOffCard());
	}

	public ExternalResourceLink getDebitResourceLink(BusinessDocument document) throws DocumentException
	{
		return null;
	}
}
