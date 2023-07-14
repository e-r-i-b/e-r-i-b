package com.rssl.phizic.operations.calendar;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.dictionaries.synchronization.ListDictionaryEntityOperationBase;

/**
 * @author Gainanov
 * @ created 24.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class ListCalendarOperation extends ListDictionaryEntityOperationBase
{

	public boolean isCAAdmin()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isCAAdmin();
	}

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}
}
