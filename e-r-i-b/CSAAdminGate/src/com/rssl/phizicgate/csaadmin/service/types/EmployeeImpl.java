package com.rssl.phizicgate.csaadmin.service.types;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.officies.BackRefOfficeGateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.employee.Employee;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.login.Login;
import com.rssl.phizic.gate.schemes.AccessScheme;
import com.rssl.phizicgate.csaadmin.service.generated.DepartmentType;
import com.rssl.phizicgate.csaadmin.service.generated.EmployeeType;

/**
 * @author akrenev
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 */

public class EmployeeImpl implements Employee
{
	private EmployeeType employee;
	private LoginImpl login;
	private AccessScheme scheme;
	private Office department;

	/**
	 * конструктор
	 * @param employee исходный сотрудник
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public EmployeeImpl(EmployeeType employee) throws GateException, GateLogicException
	{
		this.employee = employee;
		login = new LoginImpl(employee.getLogin());
		if (employee.getLogin().getAccessScheme() != null)
			scheme = new AccessSchemeImpl(employee.getLogin().getAccessScheme());
		DepartmentType employeeDepartment = employee.getDepartment();
		ExtendedCodeGateImpl code = new ExtendedCodeGateImpl(employeeDepartment.getTb(), employeeDepartment.getOsb(), employeeDepartment.getVsp());
		department = GateSingleton.getFactory().service(BackRefOfficeGateService.class).getOfficeByCode(code);
	}

	public Long getId()
	{
		return employee.getExternalId();
	}

	public Long getExternalId()
	{
		return employee.getExternalId();
	}

	public void setExternalId(Long externalId)
	{
		employee.setExternalId(externalId);
	}

	public Login getLogin()
	{
		return login;
	}

	public AccessScheme getScheme()
	{
		return scheme;
	}

	public void setScheme(AccessScheme scheme)
	{
		this.scheme =  scheme;
	}

	public void setDepartment(Office department)
	{
		this.department = department;
	}

	public Office getDepartment()
	{
		return department;
	}

	public String getSurName()
	{
		return employee.getSurname();
	}

	public void setSurName(String surname)
	{
		employee.setSurname(surname);
	}

	public String getFirstName()
	{
		return employee.getFirstName();
	}

	public void setFirstName(String firstName)
	{
		employee.setFirstName(firstName);
	}

	public String getPatrName()
	{
		return employee.getPatronymic();
	}

	public void setPatrName(String patrName)
	{
		employee.setPatronymic(patrName);
	}

	public String getInfo()
	{
		return employee.getInfo();
	}

	public void setInfo(String info)
	{
		employee.setInfo(info);
	}

	public String getEmail()
	{
		return employee.getEmail();
	}

	public void setEmail(String email)
	{
		employee.setEmail(email);
	}

	public String getMobilePhone()
	{
		return employee.getMobilePhone();
	}

	public void setMobilePhone(String mobilePhone)
	{
		employee.setMobilePhone(mobilePhone);
	}

	public boolean isCAAdmin()
	{
		return employee.isCaAdmin();
	}

	public void setCAAdmin(boolean caAdmin)
	{
		employee.setCaAdmin(caAdmin);
	}

	public boolean isVSPEmployee()
	{
		return employee.isVspEmployee();
	}

	public void setVSPEmployee(boolean vspEmployee)
	{
		employee.setVspEmployee(vspEmployee);
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
		return employee.getManagerChannel();
	}

	public void setManagerChannel(String managerChannel)
	{
		employee.setManagerChannel(managerChannel);
	}

	public String getSUDIRLogin()
	{
		return employee.getSudirLogin();
	}

	public void setSUDIRLogin(String sudirLogin)
	{
		employee.setSudirLogin(sudirLogin);
	}
}
