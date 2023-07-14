package com.rssl.phizic.operations.employees;

import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.employees.EmployeeWrapper;
import com.rssl.phizic.business.schemes.AccessSchemesConfig;
import com.rssl.phizic.business.schemes.DbAccessSchemesConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.gate.employee.Employee;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.csaadmin.auth.SynchronizeEmployeeUtil;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */

public class EditEmployeeOperation extends OperationBase implements EditEntityOperation
{
	private static final EmployeeService employeeService = new EmployeeService();
	private static final SecurityService securityService = new SecurityService();
	private static final DepartmentService departmentService = new DepartmentService();

	private Employee employee;

	private void initialize(Employee employee)
	{
		this.employee = employee;
	}

	protected void initializeCurrentEmployee() throws BusinessException, BusinessLogicException
	{
		CSAAdminGateConfig config = ConfigFactory.getConfig(CSAAdminGateConfig.class);
		if(config.isMultiBlockMode())
		{
			try
		    {
			    initialize(getService().getCurrent());
		    }
		    catch (GateLogicException e)
		    {
			    throw new BusinessLogicException(e);
		    }
		    catch (GateException e)
		    {
			    throw new BusinessException(e);
		    }
		}
		else
		{
			initialize(EmployeeWrapper.getNewInstance(getOwner()));
		}
	}

	/**
	 * Инициализировать операцию новым сотрудником
	 */
    public void initialize() throws BusinessException, BusinessLogicException
    {
	    EmployeeWrapper newEmployee = EmployeeWrapper.getNewInstance(isVSPEmployee());
	    AccessSchemesConfig schemesConfig = ConfigFactory.getConfig(DbAccessSchemesConfig.class);
	    newEmployee.setScheme(schemesConfig.getDefaultAccessScheme(AccessType.employee));
	    initialize(newEmployee);
    }
	/**
	 * Инициализировать операцию существующим сотрудником
	 * @param employeeId - id сотрудника
	 * @throws BusinessException
	 * @throws BusinessLogicException сотрудник не найден
	 */
    public void initialize(Long employeeId) throws BusinessException, BusinessLogicException
    {
	    try
	    {
		    initialize(getService().getById(employeeId));

		    if(employee == null)
		        throw new BusinessLogicException("Сотрудник с id=" + employeeId + " не найден");
	    }
	    catch (GateLogicException e)
        {
            throw new BusinessLogicException(e);
        }
	    catch (GateException e)
	    {
		    throw new BusinessException(e);
	    }
    }

	public Employee getEntity()
	{
		return employee;
	}

	/**
	 * задать новое подразделение
	 * @param tb номер тб
	 * @param osb номер osb или null
	 * @param vsp номер vsp или null
	 */
	public void setDepartment(String tb, String osb, String vsp) throws BusinessException
	{
		employee.setDepartment(departmentService.getDepartment(tb, osb, vsp, MultiBlockModeDictionaryHelper.getDBInstanceName()));
	}

	/**
	 * Создать нового сотрудника (с генерацией пароля)
	 * @throws BusinessException
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		try
		{
			employee = getService().save(employee);
			if (!employee.getLogin().getUserId().equals(getOwner().getLogin().getCsaUserId()))
				return;

			SynchronizeEmployeeUtil.update(getOwner(), employee);
			employeeService.update(getOwner());

			securityService.updateLastSynchronizationDate(getOwner().getLogin(), employee.getLogin().getLastSynchronizationDate());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	protected com.rssl.phizic.gate.employee.EmployeeService getService()
	{
		return GateSingleton.getFactory().service(com.rssl.phizic.gate.employee.EmployeeService.class);
	}

	protected com.rssl.phizic.business.employees.Employee getOwner()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
	}

	/**
	 * @return разрешено ли редактирование признака "администратора ЦА"
	 */
	public boolean isAllowEditCA()
	{
		return getOwner().isCAAdmin();
	}

	/**
	 * @return является ли текущий сотрудник Сотрудником ВСП.
	 */
	public boolean isVSPEmployee()
	{
		return getOwner().isVSPEmployee();
	}
}
