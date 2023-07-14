package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.person.Person;
import org.hibernate.Filter;
import org.hibernate.Session;

/**
 * @author Evgrafov
 * @ created 24.08.2006
 * @ $Author: mihaylov $
 * @ $Revision: 59224 $
 */

public class DepartmentUserRestriction implements UserRestriction
{
	private static final DepartmentService departmentService = new DepartmentService();
	private Person client;

	public DepartmentUserRestriction()
	{
		PersonDataProvider personDataProvider = PersonContext.getPersonDataProvider();
		if (personDataProvider != null && personDataProvider.getPersonData() != null)
			client = personDataProvider.getPersonData().getPerson();
	}

	public boolean accept(Person person) throws BusinessException
	{
		if (!EmployeeContext.isAvailable())
			return client.getId().equals(person.getId());

		Department department = departmentService.findById(person.getDepartmentId());
		return AllowedDepartmentsUtil.isDepartmentAllowedInNode(department.getCode());
	}

	public void applyFilter(Session session)
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		if(!employeeData.isAllTbAccess())
		{
			Filter filter = session.enableFilter("person_filter_by_department");
			filter.setParameter("employeeLoginId", employeeData.getEmployee().getLogin().getId());
		}
	}
}