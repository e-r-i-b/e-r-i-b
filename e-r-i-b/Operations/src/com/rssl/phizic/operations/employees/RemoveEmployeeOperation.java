package com.rssl.phizic.operations.employees;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.EmployeeWrapper;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.employee.Employee;
import com.rssl.phizic.gate.employee.EmployeeService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author Evgrafov
 * @ created 03.05.2006
 * @ $Author: krenev_a $
 * @ $Revision: 55395 $
 */
public class RemoveEmployeeOperation extends OperationBase implements RemoveEntityOperation
{
	private Employee employee;

	/**
	 * @param employeeId ID сотрудника
	 */
	public void initialize(Long employeeId) throws BusinessException
	{
		try
		{
			employee = GateSingleton.getFactory().service(EmployeeService.class).getById(employeeId);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		if(employee == null)
			throw new BusinessException("Сотрудник с ID:" + employeeId + " не найден");
	}

	/**
	 * для логгирования
	 * @return сотрудник
	 */
	public Employee getEntity()
	{
		return employee;
	}

	/**
	 * Удалить сотрудника
	 */
	public void remove() throws BusinessException, BusinessLogicException
	{
		try
		{
			GateSingleton.getFactory().service(EmployeeService.class).delete(employee);
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

	protected com.rssl.phizic.business.employees.Employee getOwner()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
	}

	protected EmployeeWrapper getInitiator()
	{
		return EmployeeWrapper.getNewInstance(getOwner());
	}
}