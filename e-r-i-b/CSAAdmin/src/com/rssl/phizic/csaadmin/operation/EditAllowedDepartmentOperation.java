package com.rssl.phizic.csaadmin.operation;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.departments.AllowedDepartmentService;
import com.rssl.phizic.csaadmin.business.departments.Department;
import com.rssl.phizic.csaadmin.business.login.Login;

import java.util.List;

/**
 * @author akrenev
 * @ created 06.11.13
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������������� ��������� �������������
 */

public class EditAllowedDepartmentOperation extends EmployeeOperationBase
{
	private static final AllowedDepartmentService allowedDepartmentService = new AllowedDepartmentService();

	private List<Department> addDepartments;
	private List<Department> deleteDepartments;

	/**
	 * ������������� �� ������
	 * @param loginName �����
	 * @throws AdminException
	 */
	public void initialize(String loginName) throws AdminException, AdminLogicException
	{
		Login login = loginService.findByName(loginName);
		initialize(employeeService.findByLogin(login));
	}

	/**
	 * ������ ������ ����������� �������������
	 * @param addDepartments ������ ����������� �������������
	 */
	public void setAddDepartments(List<Department> addDepartments)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.addDepartments = addDepartments;
	}

	/**
	 * ������ ������ ��������� �������������
	 * @param deleteDepartments ������ ��������� �������������
	 */
	public void setDeleteDepartments(List<Department> deleteDepartments)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.deleteDepartments = deleteDepartments;
	}

	/**
	 * @return ������ ��������� ��������������
	 * @throws AdminException
	 */
	public List<Department> getAllowedDepartment() throws AdminException
	{
		return allowedDepartmentService.getAllowedByLogin(getEmployee().getLogin());
	}

	@Override
	protected void process() throws AdminException, AdminLogicException
	{
		allowedDepartmentService.updateAllowed(addDepartments, deleteDepartments, getEmployee().getLogin());
		updateLogin();
	}

	protected String getConstraintViolationExceptionMessage(String constraintMessage)
	{
		return "��������� � ����� ������� ����������.";
	}
}
