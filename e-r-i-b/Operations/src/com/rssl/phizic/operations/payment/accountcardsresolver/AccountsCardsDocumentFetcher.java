package com.rssl.phizic.operations.payment.accountcardsresolver;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;

/**
 * @author potehin
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */
public interface AccountsCardsDocumentFetcher
{
	public boolean isDocumentSupported(Object document);

	/**
	 *
	 * @param document
	 * @return линк-на-источник списания
	 */
	public ExternalResourceLink getCreditResourceLink(BusinessDocument document) throws DocumentException;

	/**
	 * @param document
	 * @return линк-на-источник зачисления
	 */
	public ExternalResourceLink getDebitResourceLink(BusinessDocument document) throws DocumentException;
}
