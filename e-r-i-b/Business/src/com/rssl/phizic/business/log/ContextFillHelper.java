package com.rssl.phizic.business.log;

import com.rssl.auth.csa.back.servises.UserLogonType;
import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.business.persons.LightPerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.context.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collection;

/**
 * @author Omeliyanchuk
 * @ created 23.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class ContextFillHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static EmployeeService employeeService = new EmployeeService();
	private static PersonService personService = new PersonService();
	private static DepartmentService departmentService = new DepartmentService();
	private static final String DELIMETER = "|";

	/**
	 * заполняет контекст логирования по логину клиента или сотрудника
	 * ВАЖНО!!! Глушит все исключения
	 * @param commonLogin логин клиента или сотрудника
	 */
	public static void fillContextByLogin(CommonLogin commonLogin)
	{
		if(commonLogin==null)
			return;

		try
		{
			if (commonLogin instanceof BankLogin)
			{
				LogThreadContext.setLoginId(commonLogin.getId());
				Employee employee;
				if(EmployeeContext.getEmployeeDataProvider()!=null && EmployeeContext.getEmployeeDataProvider().getEmployeeData()!=null && EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee()!=null)
				{
					employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
					if(employee.getLogin()!= null && employee.getLogin().getId()!= null && employee.getLogin().getId().equals(commonLogin.getId()))
					{
						employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
						fillDepartment(EmployeeContext.getEmployeeDataProvider().getEmployeeData().getDepartment());
					}
					else
					{
						employee = employeeService.findByLogin((BankLogin) commonLogin);
						fillDepartment(employee.getDepartmentId());
					}
				}
				else
				{
					employee = employeeService.findByLogin((BankLogin) commonLogin);
					fillDepartment(employee.getDepartmentId());
				}

				LogThreadContext.setSurName(employee.getSurName());
				LogThreadContext.setPatrName(employee.getPatrName());
				LogThreadContext.setFirstName(employee.getFirstName());
				LogThreadContext.setPersonId(employee.getId());
				LogThreadContext.setUserId(commonLogin.getUserId());
			}
			else if(commonLogin instanceof GuestLogin)
			{
				GuestLogin guestLogin = (GuestLogin)commonLogin;
				LogThreadContext.setGuestPhoneNumber(guestLogin.getAuthPhone());
				LogThreadContext.setGuestCode(guestLogin.getGuestCode());
				PersonData guestData = PersonContext.getPersonDataProvider().getPersonData();
				if (PersonContext.isAvailable() && guestData.getPerson() != null)
					fillPersonLogContext(guestLogin, guestData.getPerson());
			}
			else
			{
				LogThreadContext.setLoginId(commonLogin.getId());
				if(PersonContext.getPersonDataProvider()!=null && PersonContext.getPersonDataProvider().getPersonData()!=null && PersonContext.getPersonDataProvider().getPersonData().getPerson()!=null)
				{
					PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
					Person person = personData.getPerson();

					if(person.getLogin()!= null && person.getLogin().getId()!= null && person.getLogin().getId().equals(commonLogin.getId()))
					{
						fillDepartment(personData.getDepartment());
						fillPersonLogContext(commonLogin, person);
						return;
					}
				}

				fillPersonLogContext(commonLogin);
        	}
		}
		catch (Exception ex)
		{//ловим все и не падаем, т.к. нужно только для логов
			log.error(ex);
		}
	}

	/**
	 * Заполняет контекст клиента по персоне из БД
	 * @param person персона
	 */
	public static void fillContextByPerson(Person person)
	{
		if (person == null || person.getLogin() == null)
			return;

		try
		{
			LogThreadContext.setLoginId(person.getLogin().getId());
			fillDepartment(person.getDepartmentId());
			fillPersonLogContext(person.getLogin(), person);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private static void fillPersonLogContext(CommonLogin commonLogin) throws BusinessException
	{
		LightPerson person = personService.getLightPersonByLogin(commonLogin.getId());

		LogThreadContext.setSurName(person.getSurName());
		LogThreadContext.setPatrName(person.getPatrName());
		LogThreadContext.setFirstName(person.getFirstName());
		LogThreadContext.setPersonId(person.getId());
		LogThreadContext.setUserId(commonLogin.getUserId());
		LogThreadContext.setBirthday(person.getBirthDay());

		fillDepartment(person.getDepartmentId());
		fillPersonLogContext(personService.getPersonDocuments(person.getId()));
	}

	private static void fillPersonLogContext(CommonLogin commonLogin, Person person)
	{
		LogThreadContext.setSurName(person.getSurName());
		LogThreadContext.setPatrName(person.getPatrName());
		LogThreadContext.setFirstName(person.getFirstName());
		if (!(person instanceof GuestPerson))
			LogThreadContext.setPersonId(person.getId());
		LogThreadContext.setUserId(commonLogin.getUserId());
		LogThreadContext.setBirthday(person.getBirthDay());

		fillPersonLogContext(person.getPersonDocuments());
	}

	private static void fillPersonLogContext(Collection<PersonDocument> docs)
	{
		StringBuilder series = new StringBuilder();
		StringBuilder number = new StringBuilder();
		for (PersonDocument doc : docs)
		{
			if (!StringHelper.isEmpty(doc.getDocumentSeries()))
			{
				series.append(doc.getDocumentSeries());
				series.append(DELIMETER);
			}
			if (!StringHelper.isEmpty(doc.getDocumentNumber()))
			{
				number.append(doc.getDocumentNumber());
				series.append(DELIMETER);
			}
		}
		LogThreadContext.setSeries(series.toString());
		LogThreadContext.setNumber(number.toString());
	}

	private static void fillDepartment(Department department)
	{
		if (department != null)
		{
			LogThreadContext.setDepartmentName(department.getName());
			LogThreadContext.setDepartmentRegion(department.getRegion());
			LogThreadContext.setDepartmentOSB(department.getOSB());
			LogThreadContext.setDepartmentVSP(department.getVSP());
		}
	}

	private static void fillDepartment(Long departmentId) throws BusinessException
	{
		if (departmentId != null)
		{
			fillDepartment(departmentService.findById(departmentId));
		}
	}
}
