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
 * Форма редактирования доступных подразделений сотрудника
 */

public class EditAllowedDepartmentsForm extends ActionFormBase
{
	private Long employeeId;
	private boolean adminHasAllTBAccess;
	private List<AllowedDepartmentWrapper> allowedDepartments;
	private String[] addedDepartment;
	private String[] removedDepartment;

	/**
	 * @return иденитификатор редактируемого сортудника
	 */
	public Long getEmployeeId()
	{
		return employeeId;
	}

	/**
	 * задать иденитификатор редактируемого сортудника
	 * @param employeeId иденитификатор редактируемого сортудника
	 */
	public void setEmployeeId(Long employeeId)
	{
		this.employeeId = employeeId;
	}

	/**
	 * @return имеет ли редактирующий сотрудник доступ ко всем подразделениям
	 */
	public boolean isAdminHasAllTBAccess()
	{
		return adminHasAllTBAccess;
	}

	/**
	 * задать признак доступа ко всем подразделения
	 * @param adminHasAllTBAccess имеет ли редактирующий сотрудник доступ ко всем подразделениям
	 */
	public void setAdminHasAllTBAccess(boolean adminHasAllTBAccess)
	{
		this.adminHasAllTBAccess = adminHasAllTBAccess;
	}

	/**
	 * @return список доступных подразделений
	 */
	public List<AllowedDepartmentWrapper> getAllowedDepartments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return allowedDepartments;
	}

	/**
	 * задать список доступных подразделений
	 * @param allowedDepartments список доступных подразделений
	 */
	public void setAllowedDepartments(List<AllowedDepartmentWrapper> allowedDepartments)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.allowedDepartments = allowedDepartments;
	}

	/**
	 * @return добавленные подразделения
	 */
	public String[] getAddedDepartment()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return addedDepartment;
	}

	/**
	 * задать добавленные подразделения
	 * @param addedDepartment добавленные подразделения
	 */
	public void setAddedDepartment(String[] addedDepartment)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.addedDepartment = addedDepartment;
	}

	/**
	 * @return удаленные подразделения
	 */
	public String[] getRemovedDepartment()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return removedDepartment;
	}

	/**
	 * задать удаленные подразделения
	 * @param removedDepartment удаленные подразделения
	 */
	public void setRemovedDepartment(String[] removedDepartment)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.removedDepartment = removedDepartment;
	}
}
