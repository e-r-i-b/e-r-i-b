package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author EgorovaA
 * @ created 18.03.15
 * @ $Author$
 * @ $Revision$
 */
public class AccountCurrencyListSource extends AccountListSource
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final String ACCOUNT_ID = "accountId";

	public AccountCurrencyListSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public AccountCurrencyListSource(EntityListDefinition definition, AccountFilter accountFilter)
	{
		super(definition, accountFilter);
	}

	public AccountCurrencyListSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	protected AccountLink getAccountLinkToCompare(Map<String, String> params) throws BusinessException
	{
		String accountId = params.get(ACCOUNT_ID);
		if (!StringHelper.isEmpty(accountId))
		{
			return externalResourceService.findLinkById(AccountLink.class, AccountLink.getIdFromCode(accountId));
		}
		return super.getAccountLinkToCompare(params);
	}

	protected boolean compareCurrencies(AccountLink accountLink, AccountLink accountLinkToCompare) throws BusinessException
	{
		return getAccountLinkComparator().compare(accountLink, accountLinkToCompare) == 0;
	}
}
