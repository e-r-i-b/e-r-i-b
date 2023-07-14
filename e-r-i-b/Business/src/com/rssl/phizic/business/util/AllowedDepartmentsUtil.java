package com.rssl.phizic.business.util;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.MultiNodeEmployeeData;
import com.rssl.phizic.gate.dictionaries.officies.Code;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author mihaylov
 * @ created 09.01.14
 * @ $Author$
 * @ $Revision$
 * ”тилитный класс дл€ работы с доступными подразделени€ми сотрудника
 * –аботает только при проинициализированном контексте сотрудника
 */
public class AllowedDepartmentsUtil
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final EmployeeService employeeService = new EmployeeService();

	/**
	 * @return список доступных сотруднику тербанков
	 * @throws BusinessException
	 */
	public static List<Department> getAllowedTerbanks() throws BusinessException
	{
		MultiNodeEmployeeData employeeData = MultiBlockModeDictionaryHelper.getEmployeeData();
		if(employeeData.isAllTbAccess())
			return departmentService.getTerbanks(MultiBlockModeDictionaryHelper.getDBInstanceName());
		else
			return departmentService.getTerbanks(employeeData.getLoginId(), MultiBlockModeDictionaryHelper.getDBInstanceName());
	}

	/**
	 * @return —писок “Ѕ, к которым относ€тс€ доступные сотруднику подразделени€.
	 * @throws BusinessException
	 */
	public static List<Department> getTerbanksByAllowedDepartments() throws BusinessException
	{
		MultiNodeEmployeeData employeeData = MultiBlockModeDictionaryHelper.getEmployeeData();
		if(employeeData.isAllTbAccess())
			return departmentService.getTerbanks(MultiBlockModeDictionaryHelper.getDBInstanceName());
		else
			return departmentService.getTerbanksByAllowedDepartments(employeeData.getLoginId(), MultiBlockModeDictionaryHelper.getDBInstanceName());
	}

	/**
	 * @return список номеров доступных сотруднику тербанков
	 * @throws BusinessException
	 */
	public static List<String> getAllowedTerbanksNumbers() throws BusinessException
	{
		MultiNodeEmployeeData employeeData = MultiBlockModeDictionaryHelper.getEmployeeData();
		if(employeeData.isAllTbAccess())
			return departmentService.getTerbanksNumbers(MultiBlockModeDictionaryHelper.getDBInstanceName());
		else
			return departmentService.getAllowedTerbanksNumbers(employeeData.getLoginId(),MultiBlockModeDictionaryHelper.getDBInstanceName());
	}

	/**
	 * @param departments список номеров тербанков
	 * @return доступны ли тербанки с переданными номерами
	 * @throws BusinessException
	 */
	public static boolean isAllowedTerbanksNumbers(Collection<String> departments) throws BusinessException
	{
		MultiNodeEmployeeData employeeData = MultiBlockModeDictionaryHelper.getEmployeeData();
		if(employeeData.isAllTbAccess())
			return true;
		else
			return employeeService.isTBsAllowed(employeeData.getLoginId(), departments,MultiBlockModeDictionaryHelper.getDBInstanceName());
	}

	/**
	 * ƒоступен ли департамент текущему сотруднику
	 * @param department - департамент
	 * @return true - департамент доступен текущему сотруднику
	 * @throws BusinessException
	 */
	public static boolean isDepartmentAllowed(Department department) throws BusinessException
	{
		return isDepartmentAllowed(department.getId());
	}

	/**
	 * ƒоступен ли департамент текущему сотруднику
	 * @param departmentId - ид департамент
	 * @return true - департамент доступен текущему сотруднику
	 * @throws BusinessException
	 */
	public static boolean isDepartmentAllowed(Long departmentId) throws BusinessException
	{
		return isDepartmentsAllowed(Collections.singletonList(departmentId));
	}

	/**
	 * ƒоступны ли департаменты текущему сотруднику
	 * @param departments - список доступных департаментов
	 * @return true - департаменты доступны текущему сотруднику
	 * @throws BusinessException
	 */
	public static boolean isDepartmentsAllowed(List<Long> departments) throws BusinessException
	{
		MultiNodeEmployeeData employeeData = MultiBlockModeDictionaryHelper.getEmployeeData();
		if(employeeData.isAllTbAccess())
			return true;
		return employeeService.isDepartmentsAllowed(employeeData.getLoginId(), departments, MultiBlockModeDictionaryHelper.getDBInstanceName());
	}

	/**
	 * ƒоступны ли департаменты текущему сотруднику
	 * @param tb    - “Ѕ
	 * @param osb   - ќ—Ѕ
	 * @param vsp   - ¬—ѕ
	 * @return true - департаменты доступны текущему сотруднику
	 * @throws BusinessException
	 */
	public static boolean isDepartmentsAllowedByCode(String tb, String osb, String vsp) throws BusinessException
	{
		MultiNodeEmployeeData employeeData = MultiBlockModeDictionaryHelper.getEmployeeData();
		if(employeeData.isAllTbAccess())
			return true;
		return employeeService.isDepartmentsAllowedByCode(employeeData.getLoginId(), tb, osb, vsp,MultiBlockModeDictionaryHelper.getDBInstanceName());
	}

	/**
	 * ƒоступно ли подразделение в блоке
	 * @param code код подразделени€
	 * @return true - департамент доступен текущему сотруднику
	 * @throws BusinessException
	 */
	public static boolean isDepartmentAllowedInNode(Code code) throws BusinessException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		if(employeeData.isAllTbAccess())
			return true;

		SBRFOfficeCodeAdapter sbrfOfficeCodeAdapter = new SBRFOfficeCodeAdapter(code);
		String tb = sbrfOfficeCodeAdapter.getRegion();
		String osb = sbrfOfficeCodeAdapter.getBranch();
		String vsp = sbrfOfficeCodeAdapter.getOffice();
		return employeeService.isDepartmentsAllowedByCode(employeeData.getLoginId(), tb, osb, vsp);
	}
}
