package com.rssl.phizic.operations.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.groups.GroupService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.dataaccess.query.QueryParameter;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ListForEmployeeOperation;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 13.11.2006 Time: 15:24:57 To change this template use
 * File | Settings | File Templates.
 */
public class ListGroupPersonsOperation  extends ListForEmployeeOperation implements ListEntitiesOperation
{
	private final static GroupService groupService = new GroupService();
	private final static DepartmentService departmentService = new DepartmentService();
	private Long groupId;
	private Long mailId;
    private String groupName;

	public void initialize(Long groupId, Long mailId) throws BusinessException
	{
		//Пытаемся найти группу из письма, id группы в данном случае не нужно.
		if (mailId != null)
		{
			setMailId(mailId);
			return;
		}

		if (groupId != null)
			setGroupId(groupId);
	}

	public Long getGroupId()
	{
		return groupId;
	}

	private void setGroupId(Long groupId) throws BusinessException
	{
		checkGroupAccess(groupId);
		this.groupId = groupId;
	}

	private void setMailId(Long mailId)
	{
		this.mailId = mailId;
	}

	@QueryParameter
	public Long getMailId()
	{
		return mailId;
	}

	private void checkGroupAccess(Long groupId) throws BusinessException
	{
		Group group = groupService.findGroupById(groupId);
        groupName = group.getName();
		//  Если группа не принадлежит ни одному департаменту тогда отображаем её для всех сотрудников.
		if (group.getDepartment() == null)
			return;

		if (!isAllTbAccess() && !departmentService.isDepartmentAllowed(group.getDepartment(), getEmployeeLoginId()))
			throw new AccessException("Запрошенная группа принадлежит другому департаменту");
	}

	/**
	 * Получить список ТБ, к которым относятся доступные сотруднику подразделения
	 * @throws BusinessException
	 */
	public List<Department> getAllowedTB() throws BusinessException
	{
		return AllowedDepartmentsUtil.getTerbanksByAllowedDepartments();
	}

    public String getGroupName()
    {
        return groupName;
    }
}
