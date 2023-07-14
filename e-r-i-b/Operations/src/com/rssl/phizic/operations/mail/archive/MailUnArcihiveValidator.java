package com.rssl.phizic.operations.mail.archive;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author mihaylov
 * @ created 13.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Данный класс необходим для проверки соответствия восстановленного письма параметрам разархивации
 */
public class MailUnArcihiveValidator
{
	private static final PersonService personService = new PersonService();
	private static final EmployeeService employeeService = new EmployeeService();

	private Map<String,Object> parameters; // параметры фильтра разархивации писем: дата письма, статус, тема etc.


	public MailUnArcihiveValidator(Map<String, Object> parameters)
	{
		this.parameters = parameters;
	}

	public Boolean validate(Mail mail) throws BusinessException
	{
		if(!checkMailByPeriod(mail, (Date)parameters.get("fromPeriod"), (Date)parameters.get("toPeriod")))
			return false;

		//проверяем тему письма на соответствие фильтру
		String subject = (String)parameters.get("subject");
		if(!checkMatches(mail.getSubject(),subject))
			return false;

		//проверяем тип письма на соответствие фильтру
		String type = (String)parameters.get("type");
		if(!checkMatches(mail.getType().toString(),type))
			return false;

		//проверяем наличие вложенных файлов на соответствие фильтру
		String isAttach = (String)parameters.get("isAttach");
		if(!StringHelper.isEmpty(isAttach) && Boolean.valueOf(isAttach).equals(mail.getData() == null))
			return false;

		//проверяем ФИО клиента на соответствие фильтру
		String userFirstName = (String)parameters.get("userFirstName");
		String userSurName = (String)parameters.get("userSurName");
		String userPatrName = (String)parameters.get("userPatrName");
		if(!StringHelper.isEmpty(userFirstName) || !StringHelper.isEmpty(userSurName) || !StringHelper.isEmpty(userPatrName))
		{
			if(mail.getDirection() == MailDirection.ADMIN)
			{
				Person person = personService.findByLoginId(mail.getSender().getId());
				if(!checkPersonByFilter(person,userFirstName,userSurName,userPatrName))
					return false;
			}
		}

		//проверяем ФИО и логин сотрудника на соответствие фильтру
		String employeeFirstName = (String)parameters.get("employeeFirstName");
		String employeeSurName = (String)parameters.get("employeeSurName");
		String employeePatrName = (String)parameters.get("employeePatrName");
		String employeeLogin = (String)parameters.get("employeeLogin");

		if(!StringHelper.isEmpty(employeeFirstName) || !StringHelper.isEmpty(employeeSurName) ||
				!StringHelper.isEmpty(employeePatrName) || !StringHelper.isEmpty(employeeLogin))
		{
			if(mail.getDirection() == MailDirection.ADMIN)
			{
				if(!checkMatches(mail.getEmployee().getUserId(),employeeLogin))
					return false;
				Employee employee = employeeService.findByUserId(mail.getEmployee().getUserId());
				if(!checkEmployeeByFilter(employee,employeeFirstName,employeeSurName,employeePatrName))
					return false;
			}
			if(mail.getDirection() == MailDirection.CLIENT)
			{
				if(!checkMatches(mail.getSender().getUserId(),employeeLogin))
					return false;
				Employee employee = employeeService.findByUserId(mail.getSender().getUserId());
				if(!checkEmployeeByFilter(employee,employeeFirstName,employeeSurName,employeePatrName))
					return false;
			}
		}

		return true;
	}

	//проверяем что письмо попадает в период фильтра, заданный пользователем
	private boolean checkMailByPeriod(Mail mail, Date fromPeriod, Date toPeriod)
	{
		Date mailDate = mail.getDate().getTime();
		if(fromPeriod != null && mailDate.before(fromPeriod))
			return false;
		if(toPeriod != null && mailDate.after(toPeriod))
			return false;
		return true;
	}

	private boolean checkPersonByFilter(Person person, String firstName, String surName, String patrName)
	{
		//если не нашли отправителя, то не можем проверить соответствует ли данное письмо фильтру -> из архива не восстанавливаем
		if(person == null)
			return false;
		if(!checkMatches(person.getFirstName(),firstName))
			return false;
		if(!checkMatches(person.getSurName(),surName))
			return false;
		if(!checkMatches(person.getPatrName(),patrName))
			return false;
		return true;
	}

	private boolean checkEmployeeByFilter(Employee employee, String firstName, String surName, String patrName)
	{
		//если не нашли отправителя, то не можем проверить соответствует ли данное письмо фильтру -> из архива не восстанавливаем
		if(employee == null)
			return false;
		if(!checkMatches(employee.getFirstName(),firstName))
			return false;
		if(!checkMatches(employee.getSurName(),surName))
			return false;
		if(!checkMatches(employee.getPatrName(),patrName))
			return false;
		return true;
	}

	//функция проверяющая на совпадение
	// возвращает true, если matched пусто или содержится в str
	private boolean checkMatches(String str, String matched)
	{
		return StringHelper.isEmpty(matched) || str.indexOf(matched)!=-1;
	}
}
