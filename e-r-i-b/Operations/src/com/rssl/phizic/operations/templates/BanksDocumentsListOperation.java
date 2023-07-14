package com.rssl.phizic.operations.templates;

import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;

/**
 * User: Novikov_A
 * Date: 01.02.2007
 * Time: 19:52:11
 */
public class BanksDocumentsListOperation extends OperationBase implements ListEntitiesOperation
{
	//TODO переделать на рестрикшенс.
	public Long getCurrentDepartment()
	{
		if(EmployeeContext.getEmployeeDataProvider() != null && EmployeeContext.getEmployeeDataProvider().getEmployeeData() != null)
		{
			EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
	        return employeeData.getEmployee().getDepartmentId();
		}
		else{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			return personData.getPerson().getDepartmentId();
		}
	}
}
