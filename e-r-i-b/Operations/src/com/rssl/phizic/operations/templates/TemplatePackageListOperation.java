package com.rssl.phizic.operations.templates;

import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * User: Novikov_A
 * Date: 01.02.2007
 * Time: 19:52:11
 */
public class TemplatePackageListOperation extends OperationBase implements ListEntitiesOperation
{
	//TODO переделать на рестрикшенс.
	public Long getCurrentDepartment()
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		return employeeData.getEmployee().getDepartmentId();
	}
}