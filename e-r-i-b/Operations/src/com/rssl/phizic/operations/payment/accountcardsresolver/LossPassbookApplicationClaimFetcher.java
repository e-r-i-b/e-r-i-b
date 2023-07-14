package com.rssl.phizic.operations.payment.accountcardsresolver;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.LossPassbookApplication;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;

/**
 * @author krenev
 * @ created 01.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class LossPassbookApplicationClaimFetcher extends AccountsCardsDocumentFetcherBase
{
	public boolean isDocumentSupported(Object document)
	{
		return document instanceof LossPassbookApplication;
	}

	public ExternalResourceLink getCreditResourceLink(BusinessDocument document) throws DocumentException
	{
		LossPassbookApplication passbookApplication = (LossPassbookApplication) document;
		return findLinkByNumber(document, ResourceType.ACCOUNT, passbookApplication.getDepositAccount());
	}

	public ExternalResourceLink getDebitResourceLink(BusinessDocument document) throws DocumentException
	{
		LossPassbookApplication passbookApplication = (LossPassbookApplication) document;
		if (passbookApplication.getAccountAction() == LossPassbookApplication.TRANSFER_TO_ACCOUNT)
		{
			return findLinkByNumber(document, ResourceType.ACCOUNT, passbookApplication.getReceiverAccount());
		}
		return null;
	}
}
