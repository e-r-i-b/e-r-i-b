package com.rssl.phizic.operations.asynchSearch;

import com.rssl.phizic.operations.ext.sbrf.payment.ListServicesPaymentSearchOperation;

/**
 * Операция "живого" поиска поставщиков услуг 
 * @ author: Gololobov
 * @ created: 28.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class AsynchSearchServiceProvidersOperation extends AsynchSearchOperationBase
{
	//Навание квери для поиска поставщиков услуг
	private static final String QUERY_NAME = ListServicesPaymentSearchOperation.class.getName() + "." + "asyncSearchProviders";
	//Инстанс БД где будет осуществляться поиск поставщиков услуг
	public static final String SEARCH_INSTANCE_NAME = "AsyncSearch";

	protected String getInstanceName()
	{
		return SEARCH_INSTANCE_NAME;
	}

	protected String getQueryName()
	{
		return QUERY_NAME;
	}
}
