package com.rssl.phizic.web.employees;

import com.rssl.phizic.operations.employees.AllowedDepartmentWrapper;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author akrenev
 * @ created 02.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ����� �������������� ��������� ������������� ����������
 */

public class EditAllowedDepartmentsForm extends ActionFormBase
{
	private Long employeeId;
	private boolean adminHasAllTBAccess;
	private List<AllowedDepartmentWrapper> allowedDepartments;
	private String[] addedDepartment;
	private String[] removedDepartment;

	/**
	 * @return �������������� �������������� ����������
	 */
	public Long getEmployeeId()
	{
		return employeeId;
	}

	/**
	 * ������ �������������� �������������� ����������
	 * @param employeeId �������������� �������������� ����������
	 */
	public void setEmployeeId(Long employeeId)
	{
		this.employeeId = employeeId;
	}

	/**
	 * @return ����� �� ������������� ��������� ������ �� ���� ��������������
	 */
	public boolean isAdminHasAllTBAccess()
	{
		return adminHasAllTBAccess;
	}

	/**
	 * ������ ������� ������� �� ���� �������������
	 * @param adminHasAllTBAccess ����� �� ������������� ��������� ������ �� ���� ��������������
	 */
	public void setAdminHasAllTBAccess(boolean adminHasAllTBAccess)
	{
		this.adminHasAllTBAccess = adminHasAllTBAccess;
	}

	/**
	 * @return ������ ��������� �������������
	 */
	public List<AllowedDepartmentWrapper> getAllowedDepartments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return allowedDepartments;
	}

	/**
	 * ������ ������ ��������� �������������
	 * @param allowedDepartments ������ ��������� �������������
	 */
	public void setAllowedDepartments(List<AllowedDepartmentWrapper> allowedDepartments)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.allowedDepartments = allowedDepartments;
	}

	/**
	 * @return ����������� �������������
	 */
	public String[] getAddedDepartment()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return addedDepartment;
	}

	/**
	 * ������ ����������� �������������
	 * @param addedDepartment ����������� �������������
	 */
	public void setAddedDepartment(String[] addedDepartment)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.addedDepartment = addedDepartment;
	}

	/**
	 * @return ��������� �������������
	 */
	public String[] getRemovedDepartment()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return removedDepartment;
	}

	/**
	 * ������ ��������� �������������
	 * @param removedDepartment ��������� �������������
	 */
	public void setRemovedDepartment(String[] removedDepartment)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.removedDepartment = removedDepartment;
	}
}
