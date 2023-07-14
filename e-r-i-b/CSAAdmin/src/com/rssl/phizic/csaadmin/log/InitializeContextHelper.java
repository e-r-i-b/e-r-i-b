package com.rssl.phizic.csaadmin.log;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.departments.Department;
import com.rssl.phizic.csaadmin.business.employee.Employee;
import com.rssl.phizic.csaadmin.business.employee.EmployeeService;
import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.csaadmin.business.session.Session;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author akrenev
 * @ created 26.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер инициализации контекста
 */

public final class InitializeContextHelper
{
	private static final EmployeeService employeeService = new EmployeeService();

	private InitializeContextHelper(){}

	/**
	 * проинициализировать данными из сессии
	 * @param session сессия
	 */
	public static void initializeContext(Session session)
	{
		if (session == null)
			return;

		Login login = session.getLogin();
		LogThreadContext.setSessionId(session.getSid());
		LogThreadContext.setLoginId(login.getId());
		LogThreadContext.setLogin(login.getName());
		LogThreadContext.setUserId(login.getName());

		Employee employee = getEmployee(login);
		if (employee == null)
			return;

		LogThreadContext.setSurName(employee.getSurname());
		LogThreadContext.setPatrName(employee.getPatronymic());
		LogThreadContext.setFirstName(employee.getFirstName());

		LogThreadContext.setDepartmentName(getDepartmentCode(employee.getDepartment()));
	}

	private static String getDepartmentCode(Department department)
	{
		return StringHelper.getEmptyIfNull(department.getTb()).concat("|")
				.concat(StringHelper.getEmptyIfNull(department.getOsb())).concat("|")
				.concat(StringHelper.getEmptyIfNull(department.getVsp()));
	}

	private static Employee getEmployee(Login login)
	{
		try
		{
			return employeeService.findByLogin(login);
		}
		catch (AdminException ignore)
		{
			return null;
		}
	}
}
