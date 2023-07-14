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
 * Операция постороения отчета "По количеству контактов"
 */

public class AddressBookContactsCountReportOperation extends OperationBase implements ListEntitiesOperation
{
	public static final String COUNT_PARAMETER_NAME = AddressBookContactsCountReportExecutor.COUNT_PARAMETER_NAME;
	public static final String LOGIN_ID_PARAMETER_NAME = AddressBookContactsCountReportExecutor.LOGIN_ID_PARAMETER_NAME;

	private static final CustomListExecutor EXECUTOR = new AddressBookContactsCountReportExecutor();

	@Override
	public Query createQuery(String name)
	{
		return new CustomExecutorQuery(this, EXECUTOR);
	}
}
