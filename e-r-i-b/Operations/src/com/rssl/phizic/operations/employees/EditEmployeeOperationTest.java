package com.rssl.phizic.operations.employees;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CheckLoginTest;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author Roshka
 * @ created 28.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class EditEmployeeOperationTest extends BusinessTestCaseBase
{
	private static final String ADMINOVICH = "Админович";

	protected void setUp() throws Exception
	{
		super.setUp();
		SecurityService securityService = new SecurityService();
		BankLogin login = securityService.getBankLogin(ADMINOVICH);

		if(login == null)
			return;

		EmployeeService employeeService = new EmployeeService();
		Employee employee = employeeService.findByLogin(login);

		if(employee != null)
			employeeService.remove(employee);
	}

	public void testEditEmployeeOperation() throws Exception
    {
        EditEmployeeOperation operation = new EditEmployeeOperation();

        operation.initialize();
	    com.rssl.phizic.gate.employee.Employee employee = operation.getEntity();
	    employee.getLogin().setUserId("BL_" + CheckLoginTest.generateUID());
	    employee.getLogin().setPassword("123");
	    employee.setFirstName("Админ");
	    employee.setPatrName(ADMINOVICH);
	    employee.setSurName("Админов");

//	    Employee employeeSaved = operation.save();

//	    operation.initialize(employeeSaved.getId());
//	    employeeSaved = operation.getEntity();
//
//        employeeSaved.setFirstName("Админ");
//        employeeSaved.setPatrName(ADMINOVICH);
//        employeeSaved.setSurName("Админов");
//        employeeSaved.setInfo("");
//	    employeeSaved.setSMSFormat("1");
//
//        operation.save();
//        operation.initialize(employeeSaved.getId());
//        employeeSaved = operation.getEntity();
//
//
//	    // проверяем что зписалось правильно
//	    operation = new EditEmployeeOperation();
//	    operation.initialize(employeeSaved.getId());
//
//	    employeeSaved = operation.getEmployee();
//
//	    assertNotNull(employeeSaved.getInfo());
//
//	    assertEquals("1",employeeSaved.getSMSFormat());
//	    RemoveEmployeeOperation removeEmployeeOperation = new RemoveEmployeeOperation();
//	    removeEmployeeOperation.initialize(employeeSaved.getId());
//	    removeEmployeeOperation.remove();
    }
}
