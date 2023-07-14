package com.rssl.phizic.gate.employee;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.AllowedDepartment;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.login.Login;
import com.rssl.phizic.gate.login.LoginBlock;
import com.rssl.phizic.gate.schemes.AccessScheme;

import java.util.List;

/**
 * @author akrenev
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ������������
 */

public interface EmployeeService extends Service
{
	/**
	 * @param filterParameters ��������� ������
	 * @return ������ �����������
	 * @throws GateException
	 */
	public List<Employee> getList(EmployeeListFilterParameters filterParameters) throws GateException, GateLogicException;

	/**
	 * @param filterParameters ��������� ������
	 * @return ������ �����������
	 * @throws GateException
	 */
	public List<ContactCenterEmployee> getListMailManagers(ContactCenterEmployeeFilterParameters filterParameters) throws GateException, GateLogicException;

	/**
	 * @param managerId ������������� ���������
	 * @return ���������� � ������������ ���������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public ManagerInfo getManagerInfo(String managerId) throws GateException, GateLogicException;

	/**
	 * @return ������� ���������
	 * @throws GateException
	 */
	public Employee getCurrent() throws GateException, GateLogicException;

	/**
	 * @param id �������������
	 * @return ��������� � ��������������� id
	 * @throws GateException
	 */
	public Employee getById(Long id) throws GateException, GateLogicException;

	/**
	 * ���������� ������ ����������
	 *
	 * @param employee ������������� ���������
	 * @return ����������� ���������
	 * @throws GateException
	 */
	public Employee save(Employee employee) throws GateException, GateLogicException;

	/**
	 * ���������� ����� ����
	 * @param schemeId ������������� ����� �����
	 * @param employee ������������� ���������
	 * @return ����� �����
	 * @throws GateException
	 */
	public AccessScheme assign(Long schemeId, Employee employee) throws GateException, GateLogicException;

	/**
	 * ���������� ����� ����
	 * @param serviceIds �������
	 * @param category ��������� �������
	 * @param employee ������������� ���������
	 * @return ����� �����
	 * @throws GateException
	 */
	public AccessScheme assign(List<Long> serviceIds, String category, Employee employee) throws GateException, GateLogicException;

	/**
	 * ������� ������ � ����������
	 * @param employee ���������
	 * @throws GateException
	 */
	public void delete(Employee employee) throws GateException, GateLogicException;

	/**
	 * ������������� ����� ����������
	 * @param lockedLogin ����� ������������ ����������
	 * @param block ����������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void lock(Login lockedLogin, LoginBlock block) throws GateException, GateLogicException;

	/**
	 * �������������� ����� ����������
	 * @param login ����� ����������
	 * @return ������ ����������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<LoginBlock> unlock(Login login) throws GateException, GateLogicException;

	/**
	 * �������� ������ ����������
	 * @param login ����� ����������
	 * @return ���������� ������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public Login changePassword(Login login) throws GateException, GateLogicException;

	/**
	 * �������� ������ �������� ����������
	 * @param password - ����� ������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void selfPasswordChange(String password) throws GateException, GateLogicException;

	/**
	 * ����� ��������� �������������
	 * @param employee ��������� ��� �������� ���� �������������
	 * @return ��������� ������������� ��� ����������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public List<AllowedDepartment> getAllowedDepartments(Employee employee) throws GateException, GateLogicException;

	/**
	 * ����� ��������� �������������
	 * @param employee ��������� ��� �������� ���� �������������
	 * @param addAllowedDepartments ��������� ������������� ��� ����������
	 * @param removeAllowedDepartments ��������� ������������� ��� ��������
	 * @return ��������� ������������� ��� ����������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void saveAllowedDepartments(Employee employee, List<AllowedDepartment> addAllowedDepartments, List<AllowedDepartment> removeAllowedDepartments) throws GateException, GateLogicException;
}
