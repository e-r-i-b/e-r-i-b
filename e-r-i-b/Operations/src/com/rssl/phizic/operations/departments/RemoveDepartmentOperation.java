package com.rssl.phizic.operations.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 18.08.2006
 * @ $Author: krenev $
 * @ $Revision: 57583 $
 */

public class RemoveDepartmentOperation extends RemoveDictionaryEntityOperationBase implements RemoveEntityOperation
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private Department department;
	private List<String> errors;

	public void initialize(Long departmentId) throws BusinessException, BusinessLogicException
	{
		Department temp = departmentService.findById(departmentId,getInstanceName());

		if(temp == null)
			throw new BusinessLogicException("ѕодразделение с id " + departmentId + " не найдено");

		department = temp;
		errors = new ArrayList<String>();		
	}

	public Department getEntity()
	{
		return department;
	}

	public void doRemove() throws BusinessException, BusinessLogicException
	{
		removeDepartmentWithChildren(department);
	}

	private void removeDepartmentWithChildren(Department parent) throws BusinessException
	{
		for (Department child : getChildren(parent))
		{
			removeDepartmentWithChildren(child);
		}
		try
		{
			removeDepartment(parent);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void removeDepartment(final Department department) throws BusinessException
	{
		try
		{
			departmentService.remove(department, getInstanceName());
		}
		catch(BusinessLogicException e)
		{
			log.error("Ќе удалось удалить подразделение "+ department.getFullName(), e);
			addError("Ќевозможно удалить подразделение "+ department.getFullName() +".   нему прив€заны сотрудники и/или клиенты");
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return список ошибок, возникших при удалении подразделени€.
	 */
	public List<String> getErrors()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return errors;
	}

	/**
	 * @param str - описание ошибки 
	 */
	public void addError(String str)
	{
		errors.add(str);
	}

	/**
	 * получение списка непосредственно подчиненных подразделений
	 * @param parent подразделение, дл€ которого получаем подчиненные
	 * @return список непосредственно подчиненных подразделений, или пустой список при отсутствии оных
	 * @throws BusinessException
	 */
	private List<Department> getChildren(final Department parent) throws BusinessException
	{
		if (parent == null)
		{
			throw new IllegalArgumentException("ѕодразделение не может быть null");
		}
		if (DepartmentService.isTB(parent))
		{
			return departmentService.getOSBs(parent.getRegion(), getInstanceName());
		}

		if (DepartmentService.isOSB(parent))
		{
			return departmentService.getVSPs(parent.getRegion(), parent.getOSB(), getInstanceName());
		}
		return Collections.EMPTY_LIST;
	}
}