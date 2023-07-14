package com.rssl.phizic.operations.payment.accountcardsresolver;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.AbstractDepoAccountClaim;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;

/**
 * @author mihaylov
 * @ created 20.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class AbstractDepoAccountClaimFetcher extends AccountsCardsDocumentFetcherBase
{
	public boolean isDocumentSupported(Object document)
	{
		return document instanceof AbstractDepoAccountClaim;
	}

	public ExternalResourceLink getCreditResourceLink(BusinessDocument document) throws DocumentException
	{
		AbstractDepoAccountClaim claim = (AbstractDepoAccountClaim) document;
		return claim.getDepoLink();
	}

	public ExternalResourceLink getDebitResourceLink(BusinessDocument document) throws DocumentException
	{
		return null;
	}
}
