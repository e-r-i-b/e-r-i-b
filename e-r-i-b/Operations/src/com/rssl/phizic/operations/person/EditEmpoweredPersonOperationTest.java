package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.EmployeeImpl;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.test.MockEmployeeDataProvider;

/**
 * @author Evgrafov
 * @ created 24.07.2006
 * @ $Author: hudyakov $
 * @ $Revision: 10675 $
 */

public class EditEmpoweredPersonOperationTest extends BusinessTestCaseBase
{
	public void testEditEmpoweredPersonOperation() throws Exception
	{
		PersonService personService = new PersonService();
		ActivePerson trustingPerson = PersonServiceTest.getTestPerson();
		EditEmpoweredPersonOperation operation = new EditEmpoweredPersonOperation();

		try
		{
			operation.initialize(trustingPerson.getId(),trustingPerson.getId());
			assertTrue("Ошибка - тестовый пользователь не представитель", false);
		}
		catch(Throwable t)
		{

		}

        EmployeeImpl employee = new EmployeeImpl();
        employee.setDepartmentId( trustingPerson.getDepartmentId() );
        EmployeeContext.setEmployeeDataProvider(new MockEmployeeDataProvider());
		operation.initializeNew(trustingPerson.getId(),null);

		try
		{
			ActivePerson person = operation.getEntity();
			PersonServiceTest.initializeTestPerson(person);

			operation.save();
		}
		finally
		{
			ActivePerson person = operation.getEntity();

			if(person.getId() != null)
				personService.markDeleted(person);

			try
			{
				personService.markDeleted(trustingPerson);
			}
			catch (BusinessException e)
			{
				e.printStackTrace();
			}
		}
	}
}
