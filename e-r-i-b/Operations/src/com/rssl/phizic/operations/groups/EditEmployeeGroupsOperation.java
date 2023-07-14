package com.rssl.phizic.operations.groups;

import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.business.groups.GroupService;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 16.11.2006 Time: 12:37:33 To change this template use
 * File | Settings | File Templates.
 */
public class EditEmployeeGroupsOperation  extends OperationBase<UserRestriction>
{
	private static final GroupService groupService = new GroupService();
		private final static EmployeeService employeeService = new EmployeeService();

	private Employee employee;

	public void initialize(Long personId) throws BusinessException
	{
		this.employee = employeeService.findById( personId );
	}

	@Transactional
	public void modifyPersonGroups(String[] groupIds) throws BusinessException
	{
		List<Group> groups = getEmployeeGroups();

		for(String groupId: groupIds)
		{
			Boolean exist = false;
			Long id = Long.parseLong(groupId);
			for(Group group: groups)
			{
				if(group.getId().equals(id))
				{
					exist = true;
					groups.remove(group);//можно,так как break
					break;
				}
			}
			if(!exist)
			{
				Group group = groupService.findGroupById( id );
				groupService.addElementToGroup(group, getEmployee().getLogin() );
			}
		}
		for(Group group: groups)
		{
			groupService.deleteElementFromGroup(group, getEmployee().getLogin());
		}

	}

	private List<Group> getEmployeeGroups() throws BusinessException
	{
		return groupService.getLoginContainGroup( getEmployee().getLogin() );
	}

	public Employee getEmployee() throws BusinessException
	{
		checkEmployee();
		return employee;
	}

	private void checkEmployee() throws BusinessException
	{
		if(employee == null)
			throw new BusinessException("Ќе установлен сотрудник");
	}

}