package com.rssl.phizic.business.mail;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CheckBankLoginTest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentTest;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeImpl;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.groups.GroupService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Gainanov
 * @ created 27.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class MailTest extends BusinessTestCaseBase
{
	public void testCreateMail() throws Exception
	{
		Long mailId = null;
		Group group = new Group();
		Employee empl;
		try
		{
			empl = makeTestEmployee();
			assertNotNull(empl);
		}
		catch (BusinessException ex)
		{
			throw new BusinessException("Проблема с созданием тестого сотрудника", ex);
		}
		MailService mailService = new MailService();
		GroupService groupService = new GroupService();
		try
		{
			Mail mail = new Mail();
			mail.setBody("Test body");
			mail.setSubject("Test mail");
			mail.setDate(Calendar.getInstance());
			mail.setDirection(MailDirection.CLIENT);

			List<Recipient> recipients = new ArrayList<Recipient>();

			Recipient recipient1 = new Recipient();
			ActivePerson testPerson1 = PersonServiceTest.getTestPerson();
			recipient1.setRecipientId(testPerson1.getLogin().getId());
			recipient1.setRecipientName(testPerson1.getFullName());
			recipient1.setRecipientType(RecipientType.PERSON);
			recipient1.setState(RecipientMailState.NEW);

			Recipient recipient2 = new Recipient();
			ActivePerson testPerson2 = PersonServiceTest.getTestPerson();

			recipient2.setRecipientId(testPerson2.getLogin().getId());
			recipient2.setRecipientName(testPerson1.getFullName());
			recipient2.setRecipientType(RecipientType.PERSON);
			recipient2.setState(RecipientMailState.NEW);

			recipients.add(recipient1);
			recipients.add(recipient2);

			//mail.setRecipients(recipients);

			mail.setSender(empl.getLogin());
			mail.setType(MailType.OTHER);

			mail = mailService.addOrUpdate(mail);
			assertNotNull(mail);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		finally
		{
			groupService.deleteGroup(group);
			deleteTestEmployee(empl);
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

	private Group makeTestGroup(ActivePerson testPerson1, ActivePerson testPerson2) throws Exception
	{
		GroupService groupService = new GroupService();
		Group group = new Group();
		group.setName("Test group31");
		group.setDescription("test description");
		group.setDepartment(DepartmentTest.getTestDepartment());
		group.setCategory(AccessCategory.CATEGORY_CLIENT);
		group = groupService.createGroup(group);
		groupService.addElementToGroup(group, testPerson1.getLogin());
		groupService.addElementToGroup(group, testPerson2.getLogin());
		return group;
	}

	private void deleteTestEmployee(Employee employee) throws BusinessException
	{
		EmployeeService service = new EmployeeService();
		service.remove(employee);
	}
}
