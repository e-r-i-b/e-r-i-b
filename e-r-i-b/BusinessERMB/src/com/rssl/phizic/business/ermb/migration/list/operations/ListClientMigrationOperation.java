package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * ќпераци€ просмотра списка клиентов при миграции
 * @author Puzikov
 * @ created 10.12.13
 * @ $Author$
 * @ $Revision$
 */

public class ListClientMigrationOperation extends OperationBase implements ListEntitiesOperation
{
	@Override
	protected String getInstanceName()
	{
		return "Migration";
	}
}
