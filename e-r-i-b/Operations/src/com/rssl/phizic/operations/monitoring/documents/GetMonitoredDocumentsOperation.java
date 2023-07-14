package com.rssl.phizic.operations.monitoring.documents;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author mihaylov
 * @ created 18.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class GetMonitoredDocumentsOperation extends OperationBase implements ListEntitiesOperation
{
	private EmployeeService employeeService = new EmployeeService();
	private DepartmentService departmentService = new DepartmentService();
	private Long TB;

	public void initialize(Long departmentId) throws BusinessException, BusinessLogicException
	{
		if(!AllowedDepartmentsUtil.isDepartmentAllowed(departmentId))
			throw new BusinessLogicException("У вас нет прав для просмотра отчета по заданному департаменту");
		Department department = departmentService.findById(departmentId);
		String tbCode = department.getCode().getFields().get("region");
		TB = Long.valueOf(tbCode);
	}

	public Long getTB()
	{
		return TB;
	}

}
