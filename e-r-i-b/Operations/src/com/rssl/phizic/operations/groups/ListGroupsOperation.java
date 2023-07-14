package com.rssl.phizic.operations.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ListForEmployeeOperation;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 10.11.2006 Time: 13:07:31 To change this template use
 * File | Settings | File Templates.
 */
public abstract class ListGroupsOperation extends ListForEmployeeOperation implements ListEntitiesOperation
{

	protected Long getCurrentDepartmentId() throws BusinessException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
	    return employeeData.getEmployee().getDepartmentId();
	}
}
