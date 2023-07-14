package com.rssl.phizic.operations.payment.accountcardsresolver;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;

/**
 * @author Erkin
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */
abstract class AccountsCardsDocumentFetcherBase implements AccountsCardsDocumentFetcher
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	protected <T extends ExternalResourceLink> T findLinkByNumber(BusinessDocument document, ResourceType linkType, String number) throws DocumentException
	{
		try
		{
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			return externalResourceService.<T>findLinkByNumber(documentOwner.getLogin(), linkType, number);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
