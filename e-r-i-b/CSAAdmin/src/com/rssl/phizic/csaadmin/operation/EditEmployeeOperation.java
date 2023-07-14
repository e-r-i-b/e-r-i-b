package com.rssl.phizic.csaadmin.operation;


import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.departments.Department;
import com.rssl.phizic.csaadmin.business.employee.Employee;
import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.utils.StringHelper;


import java.util.Calendar;

/**
 * @author akrenev
 * @ created 06.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования сотрудника
 */

public class EditEmployeeOperation extends EmployeeOperationBase
{
	private Login getNewLogin()
	{
		Login login = new Login();
		login.setLastUpdateDate(Calendar.getInstance());
		return login;
	}

	/**
	 * инициализация операции новым сотрудником
	 * @throws AdminException
	 */
	public void initialize() throws AdminException, AdminLogicException
	{
		Employee employee = new Employee();
		employee.setLogin(getNewLogin());
		employee.setDepartment(new Department());
		initialize(employee);
	}

	@Override
	protected void process() throws AdminException, AdminLogicException
	{
		checkAdminCount();
		Employee employee = getEmployee();
		employeeService.save(employee);
		updateLogin();
		if (!getInitiator().getId().equals(employee.getLogin().getId()) && isDepartmentChanged())
			updateAllowedDepartments();
	}

	protected String getConstraintViolationExceptionMessage(String constraintMessage)
	{
		if(StringHelper.getEmptyIfNull(constraintMessage).contains(MANAGER_ID_UNIQUE))
			return "Сотрудник с таким ID менеджера уже существует.";
		return "Сотрудник с таким логином существует.";
	}
}
