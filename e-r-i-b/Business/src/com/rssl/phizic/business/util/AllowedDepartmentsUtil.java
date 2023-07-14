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
 * ��������� ����� ��� ������ � ���������� ��������������� ����������
 * �������� ������ ��� ��������������������� ��������� ����������
 */
public class AllowedDepartmentsUtil
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final EmployeeService employeeService = new EmployeeService();

	/**
	 * @return ������ ��������� ���������� ���������
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
	 * @return ������ ��, � ������� ��������� ��������� ���������� �������������.
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
	 * @return ������ ������� ��������� ���������� ���������
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
	 * @param departments ������ ������� ���������
	 * @return �������� �� �������� � ����������� ��������
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
	 * �������� �� ����������� �������� ����������
	 * @param department - �����������
	 * @return true - ����������� �������� �������� ����������
	 * @throws BusinessException
	 */
	public static boolean isDepartmentAllowed(Department department) throws BusinessException
	{
		return isDepartmentAllowed(department.getId());
	}

	/**
	 * �������� �� ����������� �������� ����������
	 * @param departmentId - �� �����������
	 * @return true - ����������� �������� �������� ����������
	 * @throws BusinessException
	 */
	public static boolean isDepartmentAllowed(Long departmentId) throws BusinessException
	{
		return isDepartmentsAllowed(Collections.singletonList(departmentId));
	}

	/**
	 * �������� �� ������������ �������� ����������
	 * @param departments - ������ ��������� �������������
	 * @return true - ������������ �������� �������� ����������
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
	 * �������� �� ������������ �������� ����������
	 * @param tb    - ��
	 * @param osb   - ���
	 * @param vsp   - ���
	 * @return true - ������������ �������� �������� ����������
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
	 * �������� �� ������������� � �����
	 * @param code ��� �������������
	 * @return true - ����������� �������� �������� ����������
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
