package com.rssl.phizic.business.employees;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author akrenev
 * @ created 10.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Обертка для редактирования сотрудника
 */

@SuppressWarnings("UnusedDeclaration")//поля используется для сборки объекта гибернетом
public class EmployeeWrapper implements com.rssl.phizic.gate.employee.Employee
{
	private Long employeeId;
	private Long loginId;
	private Employee employee;
	private LoginWrapper login;
	@SuppressWarnings("JpaAttributeTypeInspection")
	private com.rssl.phizic.gate.schemes.AccessScheme scheme;
	private Department department;
	private Department newDepartment;
	private String managerChannel;

	/**
	 * получить новый инстанс обертки
	 * @param vspEmployee сотрудник ВСП
	 * @return новый инстанс обертки
	 */
	public static EmployeeWrapper getNewInstance(boolean vspEmployee)
	{
		EmployeeWrapper wrapper = new EmployeeWrapper();
		wrapper.employee = new EmployeeImpl();
		wrapper.login = new LoginWrapper(null);
		wrapper.employee.setVSPEmployee(vspEmployee);
		return wrapper;
	}

	/**
	 * создать новый инстанс обертки
	 * @param employee сотрудник
	 * @return новый инстанс обертки
	 */
	public static EmployeeWrapper getNewInstance(Employee employee)
	{
		EmployeeWrapper wrapper = new EmployeeWrapper();
		wrapper.employee = employee;
		wrapper.login = new LoginWrapper(employee.getLogin());
		return wrapper;
	}

	public Long getId()
	{
		return employee.getId();
	}

	public Long getExternalId()
	{
		return employee.getExternalId();
	}

	public void setExternalId(Long externalId)
	{
		employee.setExternalId(externalId);
	}

	public void setSurName(String surName)
	{
		employee.setSurName(surName);
	}

	public String getSurName()
	{
		return employee.getSurName();
	}

	public void setFirstName(String firstName)
	{
		employee.setFirstName(firstName);
	}

	public String getFirstName()
	{
		return employee.getFirstName();
	}

	public void setPatrName(String patrName)
	{
		employee.setPatrName(patrName);
	}

	public String getPatrName()
	{
		return employee.getPatrName();
	}

	public void setInfo(String info)
	{
		employee.setInfo(info);
	}

	public String getInfo()
	{
		return employee.getInfo();
	}

	public void setEmail(String email)
	{
		employee.setEmail(email);
	}

	public String getEmail()
	{
		return employee.getEmail();
	}

	public void setMobilePhone(String mobilePhone)
	{
		employee.setMobilePhone(mobilePhone);
	}

	public String getMobilePhone()
	{
		return employee.getMobilePhone();
	}

	public String getManagerId()
	{
		return employee.getManagerId();
	}

	public void setManagerId(String managerId)
	{
		employee.setManagerId(managerId);
	}

	public String getManagerPhone()
	{
		return employee.getManagerPhone();
	}

	public void setManagerPhone(String managerPhone)
	{
		employee.setManagerPhone(managerPhone);
	}

	public String getManagerEMail()
	{
		return employee.getManagerEMail();
	}

	public void setManagerEMail(String managerEMail)
	{
		employee.setManagerEMail(managerEMail);
	}

	public String getManagerLeadEMail()
	{
		return employee.getManagerLeadEMail();
	}

	public void setManagerLeadEMail(String managerLeadEMail)
	{
		employee.setManagerLeadEMail(managerLeadEMail);
	}

	public String getManagerChannel()
	{
		return managerChannel;
	}

	public void setManagerChannel(String managerChannel)
	{
		this.managerChannel = managerChannel;
	}

	public String getSUDIRLogin()
	{
		return employee.getSUDIRLogin();
	}

	public void setSUDIRLogin(String sudirLogin)
	{
		employee.setSUDIRLogin(sudirLogin);
	}

	public void setCAAdmin(boolean caAdmin)
	{
		employee.setCAAdmin(caAdmin);
	}

	public void setVSPEmployee(boolean vspEmployee)
	{
		employee.setVSPEmployee(vspEmployee);
	}

	public boolean isCAAdmin()
	{
		return employee.isCAAdmin();
	}

	public boolean isVSPEmployee()
	{
		return employee.isVSPEmployee();
	}

	public LoginWrapper getLogin()
	{
		if (login == null)
			login = new LoginWrapper(employee.getLogin());
		return login;
	}

	public com.rssl.phizic.gate.schemes.AccessScheme getScheme()
	{
		return scheme;
	}

	public void setScheme(com.rssl.phizic.gate.schemes.AccessScheme scheme)
	{
		this.scheme = scheme;
	}

	public void setDepartment(Office office)
	{
		newDepartment = (Department) office;
		employee.setDepartmentId(newDepartment.getId());
	}

	public Department getDepartment()
	{
		if (newDepartment == null)
			return department;
		return newDepartment;
	}

	Department getOldDepartment()
	{
		return department;
	}

	Employee getEmployee()
	{
		return employee;
	}
}