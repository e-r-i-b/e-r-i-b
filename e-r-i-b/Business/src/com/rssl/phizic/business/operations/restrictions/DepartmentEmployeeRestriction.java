package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.auth.modes.AccessType;

/**
 * Возможность работы с несколькими подразделениями для сотрудника
 * администратору имеем право привязать, обычному сотруднику нет
 * @author egorova
 * @ created 27.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentEmployeeRestriction implements EmployeeRestriction
{
	public boolean accept(Employee employee) throws BusinessException
	{
		SchemeOwnService shemeService = new SchemeOwnService();
		AccessScheme accessScheme = shemeService.findScheme(employee.getLogin(), AccessType.employee);
		return accessScheme.getCategory().equals(AccessCategory.CATEGORY_ADMIN);
	}
}
