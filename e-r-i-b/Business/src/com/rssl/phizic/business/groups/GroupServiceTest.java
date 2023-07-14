package com.rssl.phizic.business.groups;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CheckBankLoginTest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentTest;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeImpl;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 09.11.2006 Time: 9:43:39 To change this template use
 * File | Settings | File Templates.
 */
public class GroupServiceTest extends BusinessTestCaseBase
{
	public void testGroupService () throws Exception
    {
	    Long globGroupId = null;
	    GroupService groupService = new GroupService();
	    try
	    {

			Group group = new Group();
			group.setName("Test group");
			group.setDescription("test description");
			group.setDepartment(DepartmentTest.getTestDepartment());
			group.setCategory( AccessCategory.CATEGORY_CLIENT );

			group = groupService.createGroup( group );
		    globGroupId = group.getId();


			assertNotNull( group );
			assertNotNull( group.getId() );

			Group group2 = groupService.findGroupById( group.getId() );
			assertNotNull( group2 );

			group2.setName("Test group2");
			group = groupService.modifyGroup(group2);
			assertTrue(group.getName().equals( group2.getName() ));

			ActivePerson testPerson = PersonServiceTest.getTestPerson();
			groupService.addElementToGroup(group, testPerson.getLogin() );

			List<Group> groupList = groupService.getLoginContainGroup( testPerson.getLogin() );
			assertNotNull( groupList );
			assertTrue( groupList.size() == 1);


			//дублирование пользователя группы, проверяется на уровне базы
			Boolean isException = false;
			try
			{
				groupService.addElementToGroup(group, testPerson.getLogin() );
			}
			catch(BusinessException ex)
			{
				isException = true;
			}

			assertTrue(isException);

			//проверка смены типа группы, если в группе есть элементы
	/*	    isException = false;
			group.setCategory( AccessCategory.CATEGORY_ADMIN );
			try
			{
				groupService.modifyGroup(group);
			}
			catch(BusinessException ex)
			{
				group.setCategory( AccessCategory.CATEGORY_CLIENT);
				isException = true;
			}
			assertTrue(isException);
	*/
		   //проверка добавления элемента не того типа
			Employee empl;
			try
			{
				empl = makeTestEmployee();
				assertNotNull(empl);
			}
			catch(BusinessException ex)
			{
				throw new BusinessException("Проблема с созданием тестого сотрудника",ex);
			}

			isException = false;
			try
			{
				groupService.addElementToGroup(group, empl.getLogin());
			}
			catch(BusinessException ex)
			{
				isException = true;
			}

			deleteTestEmployee(empl);
			assertTrue(isException);


			groupService.deleteElementFromGroup(group, testPerson.getLogin());

			Long groupId = group.getId();
			groupService.deleteGroup(group);
			Group group3 = groupService.findGroupById(groupId);
			assertNull(group3);
	    }
	    finally
	    {
		   Group group = groupService.findGroupById( globGroupId );
		   if(group != null)
		        groupService.deleteGroup( group );
	    }
    }

	private Employee makeTestEmployee() throws Exception
	{
		EmployeeService service = new EmployeeService();

        final Employee employee = new EmployeeImpl();

        final BankLogin testBankLogin = CheckBankLoginTest.getTestLogin();

        employee.setLogin(testBankLogin);
        employee.setFirstName("Семен");
        employee.setPatrName("Семенович");
        employee.setSurName("Семенов");
        employee.setInfo("info");

        service.add(employee);

		return employee;
	}

	private void deleteTestEmployee(Employee employee) throws BusinessException
	{
		EmployeeService service = new EmployeeService();

        service.remove(employee);
	}
}
