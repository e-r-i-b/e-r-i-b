package com.rssl.phizic.business.employees;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CheckBankLoginTest;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeServiceTest extends BusinessTestCaseBase
{
	/**
	 * Создать тестового сотрудника
	 * @return
	 * @throws Exception
	 */
	public static Employee getTestEmployee() throws Exception
	{
		EmployeeService service = new EmployeeService();
		BankLogin testLogin = CheckBankLoginTest.getTestLogin();
		Employee employee = service.findByUserId(testLogin.getUserId());
		if (employee != null)
		{
			service.remove(employee);
			testLogin = CheckBankLoginTest.getTestLogin();
		}

		Employee newEmployee = new EmployeeImpl();
		initializeTestPerson(newEmployee);

		newEmployee.setLogin(testLogin);
		service.add(newEmployee);

		return newEmployee;
	}

	private static void initializeTestPerson(Employee employee)
	{
		employee.setFirstName("Семен");
		employee.setPatrName("Семенович");
		employee.setSurName("Семенов");
		employee.setInfo("info");
	}

	public void testEmployeeService() throws Exception
	{
		EmployeeService service = new EmployeeService();

		final Employee employee = new EmployeeImpl();

		final BankLogin testBankLogin = CheckBankLoginTest.getTestLogin();

		employee.setLogin(testBankLogin);
		initializeTestPerson(employee);
		service.add(employee);

		Employee byId = service.findById(employee.getId());
		assertNotNull(byId);

		service.remove(byId);
	}
}
