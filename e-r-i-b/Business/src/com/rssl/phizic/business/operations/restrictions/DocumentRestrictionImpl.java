package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;

/**
 * Рестрикшн для проверки доступности редактирования документа сотруднику
 * @author niculichev
 * @ created 20.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class DocumentRestrictionImpl implements DocumentRestriction
{
	private static final DepartmentService departmentService = new DepartmentService();

	public boolean accept(BusinessDocument document) throws BusinessException
	{
		Department department = departmentService.findById(((Department) document.getDepartment()).getId());
		return AllowedDepartmentsUtil.isDepartmentAllowedInNode(department.getCode());
	}
}
