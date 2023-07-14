package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.bankroll.Account;

import java.util.Map;

/**
 * User: Balovtsev
 * Date: 27.11.2012
 * Time: 13:56:58
 */
public class StoredFullAccessAccountListSource extends FullAccessAccountListSource
{
	public StoredFullAccessAccountListSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public StoredFullAccessAccountListSource(EntityListDefinition definition, AccountFilter accountFilter)
	{
		super(definition, accountFilter);
	}

	public StoredFullAccessAccountListSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	@Override
	protected boolean skipStoredResource(Account account)
	{
		return true;
	}
}