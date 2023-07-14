package com.rssl.phizic.operations.loanclaim;

import com.rssl.phizic.operations.asynchSearch.AsynchSearchOperationBase;

/**
 * Операция предназначена для получения адресных данных клиента использующихся
 * для живого поиска в расширенной заявке на кредит.
 *
 * @author Balovtsev
 * @since 27.05.2014
 */
public class AsyncSearchClientAddressOperation extends AsynchSearchOperationBase
{
    private static final int    MAX_QUERY_RESULT    = 500;
    private static final String QUERY_TEMPLATE_NAME = AsyncSearchClientAddressOperation.class.getName() + ".%s";

    private String queryName;

    @Override
	protected int getQueryMaxResults()
	{
		return MAX_QUERY_RESULT;
	}

	@Override
	protected String getQueryName()
	{
		return String.format(QUERY_TEMPLATE_NAME, queryName);
	}

    public void setQueryName(String queryName)
    {
        this.queryName = queryName;
    }
}
