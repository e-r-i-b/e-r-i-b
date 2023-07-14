package com.rssl.phizic.csaadmin.operation;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.employee.Employee;


/**
 * @author akrenev
 * @ created 14.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция удаления сотрудника
 */

public class RemoveEmployeeOperation extends EmployeeOperationBase
{
	@Override
	protected void process() throws AdminException, AdminLogicException
	{
		Employee employee = getEmployee();
		employeeService.delete(employee);
		loginService.delete(employee.getLogin());
	}

	protected String getConstraintViolationExceptionMessage(String constraintMessage)
	{
		return "Навозможно удалить сотрудника.";
	}
}
