package com.rssl.phizic.operations.asynchSearch;

import com.rssl.phizic.operations.ext.sbrf.payment.ListServicesPaymentSearchOperation;

/**
 * �������� "������" ������ ����������� ����� 
 * @ author: Gololobov
 * @ created: 28.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class AsynchSearchServiceProvidersOperation extends AsynchSearchOperationBase
{
	//������� ����� ��� ������ ����������� �����
	private static final String QUERY_NAME = ListServicesPaymentSearchOperation.class.getName() + "." + "asyncSearchProviders";
	//������� �� ��� ����� �������������� ����� ����������� �����
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
