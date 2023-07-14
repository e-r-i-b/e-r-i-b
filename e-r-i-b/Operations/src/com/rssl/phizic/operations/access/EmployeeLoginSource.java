package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.BusinessException;

/**
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: kosyakov $
 * @ $Revision: 3436 $
 */
public class EmployeeLoginSource implements LoginSource
{
	private static final EmployeeService employeeService = new EmployeeService();

	private Employee employee;

	/**
	 * @param employeeId ID сотрудника банка
	 */
	public EmployeeLoginSource(Long employeeId) throws BusinessException
	{
		employee = employeeService.findById(employeeId);

		if (employee == null)
			throw new BusinessException("Администратор с ID:" + employeeId + " не найден");
	}

	public CommonLogin getLogin()
	{
		return employee.getLogin();
	}
}
