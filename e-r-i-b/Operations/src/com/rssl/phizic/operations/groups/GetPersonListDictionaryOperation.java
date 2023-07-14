package com.rssl.phizic.operations.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.groups.GroupService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.query.QueryParameter;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ListForEmployeeOperation;

import java.util.List;

public class GetPersonListDictionaryOperation extends ListForEmployeeOperation implements ListEntitiesOperation
{
	//������
	private Group group;
	private Employee employee;

	private static final GroupService groupService = new GroupService();
	private final static DepartmentService departmentService = new DepartmentService();

	//�������������
	public void initialize(Long groupId) throws BusinessException
	{
		if  (groupId == null)
			throw new BusinessException("�� ���������� ������������� ������");
		Group group = groupService.findGroupById(groupId);
		if (group == null)
			throw new BusinessException("�� ���������� ������ "+groupId);
		this.group = group;
		employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
	}

	/**
	 * @return id ������
	 */
	public Long getGroupId()
	{
		return this.group.getId();
	}

	/**
	 * @return id ������������ ����������
	 */
	public Long getDepartmentId()
	{
		return employee.getDepartmentId();
	}

	/**
	 * @return ������
	 */
	public Group getGroup()
	{
		return group;
	}

	@QueryParameter
	public String getGroupTB()
	{
		return this.group.getDepartment() != null ? this.group.getDepartment().getRegion() : null;
	}

	@QueryParameter
	public String getGroupOSB()
	{
		return this.group.getDepartment() != null ? this.group.getDepartment().getOSB() : null;
	}

	@QueryParameter
	public String getGroupOffice()
	{
		return this.group.getDepartment() != null ? this.group.getDepartment().getVSP() : null;
	}


	/**
	 * �������� ������ ��, � ������� ��������� ��������� ���������� �������������
	 * @throws BusinessException
	 */
	public List<Department> getAllowedTB() throws BusinessException
	{
		return AllowedDepartmentsUtil.getTerbanksByAllowedDepartments();
	}
}
