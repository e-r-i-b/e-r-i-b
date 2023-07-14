package com.rssl.phizic.operations.payment.accountcardsresolver;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.AccountClosingPayment;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;

/**
 *
 * @author Balovtsev
 * @created 24.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class AbstractAccountClosingDocumentFetcher implements AccountsCardsDocumentFetcher
{
	public boolean isDocumentSupported(Object document)
	{
		return document instanceof AccountClosingPayment;
	}

	public ExternalResourceLink getCreditResourceLink(BusinessDocument document) throws DocumentException
	{
		AccountClosingPayment payment = (AccountClosingPayment) document;
		switch (payment.getChargeOffResourceType())
		{
			case ACCOUNT:
				return payment.getChargeOffResourceLink();
			default:
				return null;
		}
	}

	public ExternalResourceLink getDebitResourceLink(BusinessDocument document) throws DocumentException
	{
		AccountClosingPayment payment = (AccountClosingPayment) document;
		switch (payment.getDestinationResourceType())
		{
			case ACCOUNT:
			case CARD:
				return payment.getDestinationResourceLink();
			default:
				return null;
		}
	}
}
