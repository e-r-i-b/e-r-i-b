package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.resources.external.comparator.AccountSavingKindComparator;

import java.util.Comparator;
import java.util.Map;

/**
 * @author vagin
 * @ created 19.09.14
 * @ $Author$
 * @ $Revision$
 * Сорс для вкладов копилки.
 */
public class AccountListForMoneyBoxSource extends AccountListSource
{
	public AccountListForMoneyBoxSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public AccountListForMoneyBoxSource(EntityListDefinition definition, AccountFilter accountFilter)
	{
		super(definition, accountFilter);
	}

	public AccountListForMoneyBoxSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	protected Comparator getAccountLinkComparator()
	{
		return new AccountSavingKindComparator();
	}
}
