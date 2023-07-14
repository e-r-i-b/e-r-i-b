package com.rssl.phizic.operations.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.groups.GroupService;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 16.11.2006 Time: 12:33:12 To change this template use
 * File | Settings | File Templates.
 */
public class ListEmployeeGroupsOperation  extends OperationBase<UserRestriction> implements ListEntitiesOperation<UserRestriction>
{
	private static final GroupService groupService = new GroupService();
	private final static EmployeeService employeeService = new EmployeeService();

	private Employee employee;

	public void initialize(Long employeeId) throws BusinessException
	{
		this.employee = employeeService.findById( employeeId );
	}

	public Employee getEmployee() throws BusinessException
	{
		checkEmployee();
		return employee;
	}

	public List<Group> getPersonGroups() throws BusinessException
	{
		return groupService.getLoginContainGroup( getEmployee().getLogin() );
	}

	public List<Group> getAllGroups() throws BusinessException
	{
		return groupService.getDepartmentGroups( getCurrentDepartmentId(), AccessCategory.CATEGORY_ADMIN);
	}

	private void checkEmployee() throws BusinessException
	{
		if(employee == null)
			throw new BusinessException("Не установлен сотрудник");
	}

	private Long getCurrentDepartmentId() throws BusinessException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
	    return employeeData.getEmployee().getDepartmentId();
	}
}

