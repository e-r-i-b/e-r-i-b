package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.business.web.AccountBlockWidget;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.operations.account.GetAccountAbstractOperation;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author gulov
 * @ created 20.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class AccountBlockWidgetOperation extends ProductBlockWidgetOperationBase<AccountBlockWidget>
{
	private GetAccountsOperation accountsOperation;
	private GetAccountAbstractOperation accountAbstractOperation;
	private List<AccountLink> allAccountLinks;
	private List<AccountLink> mainAccountLinks;

	@Override
	protected void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize();
		
		OperationFactory factory = getOperationFactory();

		accountsOperation = factory.create(GetAccountsOperation.class);
		allAccountLinks = accountsOperation.getAccounts();
		mainAccountLinks = accountsOperation.getShowsActiveAndClosedAccounts();
		accountAbstractOperation = factory.create(GetAccountAbstractOperation.class);
		List<AccountLink> showOperationAccountLinks = accountsOperation.getShowOperationLinks(allAccountLinks);
		accountAbstractOperation.initialize(showOperationAccountLinks);

		for (AccountLink link : mainAccountLinks)
		{
			if (link.getAccount() instanceof AbstractStoredResource)
			{
				setUseStoredResource(true);
				break;
			}
		}

		actualizeHiddenProducts(allAccountLinks);
	}

	public List<AccountLink> getAccountLinks()
	{
		return Collections.unmodifiableList(allAccountLinks);
	}

	public List<AccountLink> getMainAccountLinks()
	{
		return Collections.unmodifiableList(mainAccountLinks);
	}

	public boolean isAllAccountDown()
	{
		return CollectionUtils.isEmpty(mainAccountLinks) && (accountAbstractOperation.isBackError() || accountsOperation.isBackError());
	}

	public Map<AccountLink, AccountAbstract> getAccountAbstract(Long count)
	{
		return accountAbstractOperation.getAccountAbstract(count);
	}

	@Override
	public boolean checkUseStoredResource()
	{
		if (super.checkUseStoredResource())
		{
			throw new InactiveExternalSystemException(StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) allAccountLinks.get(0).getAccount()));
		}

		return false;
	}
}
