package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.bankroll.Account;

import java.util.Map;

/**
 *
 * Разрешает использование оффлайн вкладов в качестве источника данных
 *
 * User: Balovtsev
 * Date: 26.11.2012
 * Time: 16:34:27
 */
public class StoredAccountListSource extends AccountListSource
{

	public StoredAccountListSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public StoredAccountListSource(EntityListDefinition definition, AccountFilter accountFilter)
	{
		super(definition, accountFilter);
	}

	public StoredAccountListSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	@Override
	protected boolean skipStoredResource(Account account)
	{
		return true;
	}
}
