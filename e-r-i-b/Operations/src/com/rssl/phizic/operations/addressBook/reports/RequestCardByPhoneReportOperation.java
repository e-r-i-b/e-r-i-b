package com.rssl.phizic.operations.addressBook.reports;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author lukina
 * @ created 10.11.2014
 * @ $Author$
 * @ $Revision$
 * Операция для построения отчета «По запросам информации по номеру телефона»
 */
public class RequestCardByPhoneReportOperation extends OperationBase implements ListEntitiesOperation
{
	@Override
	protected String getInstanceName()
	{
		return Constants.DB_INSTANCE_NAME;
	}
}

