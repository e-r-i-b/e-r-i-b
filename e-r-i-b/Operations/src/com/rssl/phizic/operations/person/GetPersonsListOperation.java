package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.query.QueryParameter;
import com.rssl.phizic.operations.ListEntitiesOperation;

import java.util.List;

/** Created by IntelliJ IDEA. User: Roshka Date: 27.09.2005 Time: 15:27:15 */
public class GetPersonsListOperation extends PersonOperationBase implements ListEntitiesOperation<UserRestriction>
{
	public ExtendedDepartment getDepartment() throws BusinessException
	{
		return (ExtendedDepartment) departmentService.findById(EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getDepartmentId());
	}

	/**
	 * Получить список ТБ, к которым относятся доступные сотруднику подразделения
	 * @throws BusinessException
	 */
	public List<Department> getAllowedTB() throws BusinessException
	{
		return AllowedDepartmentsUtil.getTerbanksByAllowedDepartments();
	}

	@QueryParameter
	public boolean isAllTbAccess()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().isAllTbAccess();
	}

	@QueryParameter
	public Long getEmployeeLoginId()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getLogin().getId();
	}

	/**
	 * @return поддерживает ли подразделение сотрудника ESB
	 * @throws BusinessException
	 */
	public boolean isEsbSupported() throws BusinessException
	{
		return getDepartment().isEsbSupported();
	}
}
