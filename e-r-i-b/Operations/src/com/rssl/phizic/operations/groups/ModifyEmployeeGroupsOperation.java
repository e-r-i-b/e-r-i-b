package com.rssl.phizic.operations.groups;

import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.groups.GroupService;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.auth.CommonLogin;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 15.11.2006 Time: 15:48:13 To change this template use
 * File | Settings | File Templates.
 */
public class ModifyEmployeeGroupsOperation  extends OperationBase<UserRestriction> implements RemoveEntityOperation<Group, UserRestriction>
{
	private final static GroupService groupService = new GroupService();
	private final static EmployeeService employeeService = new EmployeeService();
	private Employee employee;
	private Group group;

	public void initialize(Long id) throws BusinessException
	{
		Group temp = groupService.findGroupById( id );
		if( temp == null )
			throw new BusinessException("Не установлена группа, для работы со списком сотрудников");

		group = temp;
	}

	public void setDeletedEmployee(Long id) throws BusinessException
	{
		Employee temp = employeeService.findById(id);
		if( temp == null )
			throw new BusinessException("Сотрудник с id " + id + " не найден");

		employee = temp;
	}

	public Group getEntity()
	{
		return group;
	}

	@Transactional
	public void addEmloyee(Long id) throws BusinessException
	{
		Employee employee = employeeService.findById(id);
		if (employee == null)
			throw new BusinessException("Сотрудник с id = " + id + " не найден");

		groupService.addElementToGroup(group, employee.getLogin());
	}

	@Transactional
	public void remove() throws BusinessException
	{
		groupService.deleteElementFromGroup(group, employee.getLogin());
	}

	private Set<CommonLogin> formLoginsList( List<Long> employeeIds ) throws BusinessException
	{
		Set<CommonLogin> logins = new HashSet<CommonLogin>();
		for(Long id: employeeIds)
		{

			Employee employee = employeeService.findById(id);
			logins.add( employee.getLogin() );
		}
		return logins;
	}
}

