package com.rssl.phizic.operations.dictionaries.synchronization;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.dataaccess.query.QueryParameter;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author komarov
 * @ created 23.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Базовая операция получения списка записей справочников
 */
public class ListDictionaryEntityOperationBase<T extends Restriction> extends OperationBase<T> implements ListEntitiesOperation<T>
{
	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	@QueryParameter
	public boolean isAllTbAccess()
	{
		return MultiBlockModeDictionaryHelper.getEmployeeData().isAllTbAccess();
	}

	@QueryParameter
	public Long getEmployeeLoginId()
	{
		return MultiBlockModeDictionaryHelper.getEmployeeData().getLoginId();
	}
}