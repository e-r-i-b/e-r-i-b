package com.rssl.phizicgate.csaadmin.service.types;

import com.rssl.phizic.gate.dictionaries.officies.AllowedDepartment;
import com.rssl.phizic.gate.employee.Employee;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.login.Login;
import com.rssl.phizicgate.csaadmin.service.generated.DepartmentType;
import com.rssl.phizicgate.csaadmin.service.generated.GetEmployeeSynchronizationDataResultType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 12.12.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ ����������� ��� �������������
 */

public class EmployeeSynchronizationData
{
	private Employee employee;
	private Login login;
	private List<AllowedDepartment> allowedDepartments;

	private EmployeeSynchronizationData(){}

	/**
	 * @return ���������� � ����������
	 */
	public Employee getEmployee()
	{
		return employee;
	}

	/**
	 * @return ����� ����������
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * @return ��������� �������������
	 */
	public List<AllowedDepartment> getAllowedDepartments()
	{
		return Collections.unmodifiableList(allowedDepartments);
	}

	/**
	 * �������������� ������
	 * @param synchronizationData ������ �� ��� �����
	 * @return ������ ��� �������������
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public static EmployeeSynchronizationData fromGate(GetEmployeeSynchronizationDataResultType synchronizationData) throws GateLogicException, GateException
	{
		EmployeeSynchronizationData data = new EmployeeSynchronizationData();
		if (synchronizationData.getEmployee() != null)
			data.employee = new EmployeeImpl(synchronizationData.getEmployee());
		data.login = new LoginImpl(synchronizationData.getLogin());
		data.allowedDepartments = new ArrayList<AllowedDepartment>();
		for (DepartmentType department : synchronizationData.getAllowedDepartments())
			data.allowedDepartments.add(new AllowedDepartment(department.getTb(), department.getOsb(), department.getVsp()));
		return data;
	}
}

