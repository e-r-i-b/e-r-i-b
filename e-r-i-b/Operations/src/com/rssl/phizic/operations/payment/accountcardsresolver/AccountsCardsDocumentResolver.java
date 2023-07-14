package com.rssl.phizic.operations.payment.accountcardsresolver;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;

import java.util.ArrayList;
import java.util.List;

/**
 * @author potehin
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class AccountsCardsDocumentResolver
{
	private static final List<AccountsCardsDocumentFetcher> fetchers = new ArrayList<AccountsCardsDocumentFetcher>();
	private BusinessDocument document;
	private List<AccountsCardsDocumentFetcher> documentFetchers;

	// ѕор€док обработчиков важен:
	// от более специализированного к более абстрактному
	// TODO: нормальный список обработчиков
	static
	{
		fetchers.add(new AbstractAccountClosingDocumentFetcher());
		fetchers.add(new AbstractDepoAccountClaimFetcher());
		fetchers.add(new LoanTransferDocumentFetcher());
		fetchers.add(new BlockingCardClaimFetcher());
		fetchers.add(new LossPassbookApplicationClaimFetcher());
		fetchers.add(new InternalTransferDocumentFetcher());
		fetchers.add(new ClientAccountsTransferDocumentFetcher());
		fetchers.add(new AbstractAccountsTransferDocumentFetcher());
		fetchers.add(new AbstractTransferDocumentFetcher());
		fetchers.add(new AbstractCardTransferDocumentFetcher());
	}

	public AccountsCardsDocumentResolver(BusinessDocument document)
	{
		this.document = document;
		fillFetchers();
	}

	private void fillFetchers()
	{
		documentFetchers = new ArrayList<AccountsCardsDocumentFetcher>(fetchers.size());

		for (AccountsCardsDocumentFetcher entry : fetchers)
		{
			if (entry.isDocumentSupported(document))
				documentFetchers.add(entry);
		}
	}

	public ExternalResourceLink getCreditResourceLink() throws BusinessException
	{
		try
		{
			for (AccountsCardsDocumentFetcher fetcher : documentFetchers)
			{
				ExternalResourceLink link = fetcher.getCreditResourceLink(document);
				if (link != null)
					return link;
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}

		return null;
	}

	public ExternalResourceLink getDebitResourceLink() throws BusinessException
	{
		try
		{
			for (AccountsCardsDocumentFetcher fetcher : documentFetchers)
			{
				ExternalResourceLink link = fetcher.getDebitResourceLink(document);
				if (link != null)
					return link;
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}

		return null;
	}
}
