package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.person.Person;

/**
 * @author Gulov
 * @ created 05.08.2010
 * @ $Authors$
 * @ $Revision$
 * Helper для работы с логинами
 */
public class LoginHelper
{
	private static final PersonService personService = new PersonService();

	/**
	 * @param login логин клиента
	 * @return Подразделение пользователя с заданным логином
	 * @throws BusinessException
	 */
	public static Department getDepartmentByLogin(Login login) throws BusinessException
	{
		if (login == null)
		{
			return null;
		}
		return personService.getDepartmentByLoginId(login.getId());
	}

	/**
	 * @return Уникальный код логина текущего пользователя
	 * @throws BusinessException
	 */
	public static CommonLogin getCurrentUserLoginId()
	{
		if (PersonContext.isAvailable())
			return PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		else
			return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getLogin();
	}
}
