package com.rssl.phizic.business.persons.forms;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * Проверяет соответствие тербанка сотрудника
 *
 * @author khudyakov
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ClientDepartmentValidator extends MultiFieldsValidatorBase
{
	private static final String REGION_CODE_FIELD = "region";

	private static final DepartmentService departmentService = new DepartmentService();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		try
		{
			Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();

			// если сотруднику разрешен доступ ко всем ТБ
			if (EmployeeContext.getEmployeeDataProvider().getEmployeeData().isAllTbAccess())
				return true;

			String region = StringHelper.getEmptyIfNull(retrieveFieldValue(REGION_CODE_FIELD, values));
			//ТБ введенный сотрудником
			String enteredRegionMainTB = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(region);
			Department department = departmentService.getDepartmentTBByTBNumber(enteredRegionMainTB);
			if (department == null)
				return false;

			// если сотруднику разрешен поиск по ТБ
			if (PermissionUtil.impliesServiceRigid("SeachClientsByTB"))
			{
				return departmentService.isTBAllowed(department, employee.getLogin().getId());
			}

			return departmentService.isDepartmentAllowed(department, employee.getLogin().getId());
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
