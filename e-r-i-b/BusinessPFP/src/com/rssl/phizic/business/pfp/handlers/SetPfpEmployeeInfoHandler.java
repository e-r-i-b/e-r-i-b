package com.rssl.phizic.business.pfp.handlers;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeDataProvider;

/**
 * @author mihaylov
 * @ created 27.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Устанавливает данные сотрудника проводившего ПФП
 */
public class SetPfpEmployeeInfoHandler extends PersonalFinanceProfileHandlerBase
{
	private static final DepartmentService departmentService = new DepartmentService();

	public void process(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent)
	{
		EmployeeDataProvider employeeDataProvider = EmployeeContext.getEmployeeDataProvider();
		if (employeeDataProvider == null || employeeDataProvider.getEmployeeData() == null)
			return;

		Employee employee = employeeDataProvider.getEmployeeData().getEmployee();
		profile.setEmployeeFIO(employee.getFullName());
		profile.setManagerId(employee.getManagerId());
		profile.setChannelId(employee.getChannelId());

		try
		{
			Department department = departmentService.findById(employee.getDepartmentId());
			SBRFOfficeCodeAdapter officeCodeAdapter = new SBRFOfficeCodeAdapter(department.getCode());
			profile.setManagerOSB(officeCodeAdapter.getBranch());
			profile.setManagerVSP(officeCodeAdapter.getOffice());
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения офиса сотрудника.", e);
		}
	}
}
