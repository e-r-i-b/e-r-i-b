package com.rssl.phizic.business.persons.clients;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.gate.clients.User;
import com.rssl.phizic.gate.clients.UserCategory;

/**
 * @author egorova
 * @ created 18.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class UserImpl extends ClientImpl implements User
{
	private static final DepartmentService departmentService = new DepartmentService();

	private UserCategory category;

	public UserCategory getCategory()
	{
		return category;
	}

	public void setCategory(UserCategory category)
	{
		this.category = category;
	}

	/**
	 * «аполнить пользовател€ из персоны
	 * @param person - персона
	 */
	public void fillFromPerson(Person person) throws BusinessException
	{
		this.setCategory(UserCategory.Client);
		this.setId(person.getLogin().getUserId());

		this.setFirstName(person.getFirstName());
		this.setSurName(person.getSurName());
		this.setPatrName(person.getPatrName());
		this.setFullName(person.getFullName());
		this.setOffice(departmentService.findById(person.getDepartmentId()));
	}

	/**
	 * «аполнить пользовател€ из сотрудника
	 * @param employee - сотрудник
	 */
	public void fillFromEmployee(Employee employee) throws BusinessException
	{
		this.setCategory(UserCategory.Employee);
		this.setId(employee.getLogin().getUserId());

		this.setFirstName(employee.getFirstName());
		this.setSurName(employee.getSurName());
		this.setPatrName(employee.getPatrName());
		this.setFullName(employee.getFullName());

		this.setOffice(departmentService.findById(employee.getDepartmentId()));
	}
}
