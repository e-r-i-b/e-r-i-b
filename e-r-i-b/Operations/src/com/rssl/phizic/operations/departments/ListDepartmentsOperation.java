package com.rssl.phizic.operations.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.dictionaries.synchronization.ListDictionaryEntityOperationBase;

import java.util.List;

/**
 * @author egorova
 * @ created 21.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class ListDepartmentsOperation extends ListDictionaryEntityOperationBase implements ListEntitiesOperation
{
	private Department parentDepartment;

	/**
	 * инициализация операции
	 * @param parentId идентификатор родительского подраздления. Null - для получения списка ТБ.
	 * @throws BusinessException
	 */
	public void initialize(Long parentId) throws BusinessException
	{
		if (parentId != null)
		{
			parentDepartment = new DepartmentService().findById(parentId, getInstanceName());
		}
	}
	/**
	 * @return список ТБ, к которым имеет доступ сотрудник
	 * @throws BusinessException
	 */
	public List<Department> getAllowedTBList() throws BusinessException
	{
		return AllowedDepartmentsUtil.getAllowedTerbanks();
	}

	/**
	 * @return родительское подраздление, для которого получается список потомков. Или null, если получается список ТБ.
	 */
	public Department getParentDepartment()
	{
		return parentDepartment;
	}
}
