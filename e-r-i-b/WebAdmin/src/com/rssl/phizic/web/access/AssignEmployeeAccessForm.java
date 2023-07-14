package com.rssl.phizic.web.access;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.employee.Employee;
import com.rssl.phizic.gate.login.Login;
import com.rssl.phizic.business.services.groups.ServicesGroupIterator;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: komarov $
 * @ $Revision: 79196 $
 */
@SuppressWarnings({"JavaDoc"})
public class AssignEmployeeAccessForm extends ActionFormBase
{
    private Long                employeeId;
    private Employee            employee;
	private AssignAccessSubForm employeeAccess = new AssignAccessSubForm();
	private ServicesGroupIterator groups;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public Long getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId)  throws BusinessException
    {
        this.employeeId = employeeId;
    }

	public Login getLogin() throws BusinessException
	{
		return getEmployee().getLogin();
	}

	public AssignAccessSubForm getEmployeeAccess()
	{
		return employeeAccess;
	}

	/**
	 * @return группы сервисов
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public ServicesGroupIterator getGroups()
	{
		groups.refresh();
		return groups;
	}

	/**
	 * задать группы сервисов
	 * @param groups группы
	 */
	public void setGroups(ServicesGroupIterator groups)
	{
		this.groups = groups;
	}

	/**
	 * @return назанченные сервисы
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public Long[] getSelectedServices ()
	{
		return getEmployeeAccess().getSelectedServices();
	}

	/**
	 * задать назначенные сервисы
	 * @param selectedServices сервисы
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public void setSelectedServices ( Long[] selectedServices )
	{
		getEmployeeAccess().setSelectedServices(selectedServices);
	}
}
