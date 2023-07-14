package com.rssl.phizic.operations.addressBook.reports;

import com.rssl.phizic.dataaccess.query.CustomExecutorQuery;
import com.rssl.phizic.dataaccess.query.CustomListExecutor;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author akrenev
 * @ created 11.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Операция постороения отчета "По запросам к сервису"
 */

public class AddressBookRequestsCountReportOperation extends OperationBase implements ListEntitiesOperation
{
	public static final String COUNT_PARAMETER_NAME     = AddressBookRequestsCountReportExecutor.COUNT_PARAMETER_NAME;
	public static final String FROM_DATE_PARAMETER_NAME = AddressBookRequestsCountReportExecutor.FROM_DATE_PARAMETER_NAME;
	public static final String TO_DATE_PARAMETER_NAME   = AddressBookRequestsCountReportExecutor.TO_DATE_PARAMETER_NAME;

	private static final CustomListExecutor EXECUTOR = new AddressBookRequestsCountReportExecutor();

	@Override
	public Query createQuery(String name)
	{
		return new CustomExecutorQuery(this, EXECUTOR);
	}
}
