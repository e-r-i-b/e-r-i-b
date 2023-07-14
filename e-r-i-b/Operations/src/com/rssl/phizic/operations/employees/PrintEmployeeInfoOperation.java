package com.rssl.phizic.operations.employees;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;

/**
 * @author Roshka
 * @ created 14.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class PrintEmployeeInfoOperation extends OperationBase implements ViewEntityOperation
{
	private static final EmployeeService employeeService = new EmployeeService();

	private Employee employee;

	public void initialize(Long employeeId ) throws BusinessException
	{
		employee = employeeService.findById(employeeId);
		if ( employee == null )
			throw new BusinessException("Сотрудник с id [" + employeeId + "] не найден.");
	}

	public Employee getEntity()
	{
		return employee;
	}
}