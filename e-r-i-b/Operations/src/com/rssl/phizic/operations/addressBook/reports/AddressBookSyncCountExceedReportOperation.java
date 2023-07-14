package com.rssl.phizic.operations.addressBook.reports;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author mihaylov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 *
 * Операция для построения отчета "Оповещения о превышении порога обращения к сервису"
 */
public class AddressBookSyncCountExceedReportOperation extends OperationBase implements ListEntitiesOperation
{
	@Override
	protected String getInstanceName()
	{
		return Constants.DB_INSTANCE_NAME;
	}
}
