package com.rssl.phizic.operations.payment.accountcardsresolver;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.gate.cms.claims.CardBlockingClaim;

/**
 * @author krenev
 * @ created 01.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class BlockingCardClaimFetcher extends AccountsCardsDocumentFetcherBase
{
	public boolean isDocumentSupported(Object document)
	{
		return document instanceof CardBlockingClaim;
	}

	public ExternalResourceLink getCreditResourceLink(BusinessDocument document) throws DocumentException
	{
		CardBlockingClaim claim = (CardBlockingClaim) document;
		return findLinkByNumber(document, ResourceType.CARD, claim.getCardNumber());
	}

	public ExternalResourceLink getDebitResourceLink(BusinessDocument document) throws DocumentException
	{
		return null;
	}
}
