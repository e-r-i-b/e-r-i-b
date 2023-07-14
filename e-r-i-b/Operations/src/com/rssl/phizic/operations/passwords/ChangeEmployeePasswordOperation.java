package com.rssl.phizic.operations.passwords;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.employee.EmployeeService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.OperationBase;

/**
 * Операция изменения пароля ползователя
 * @author Kidyaev
 * @ created 23.12.2005
 * @ $Author: mihaylov $
 * @ $Revision: 55985 $
 */
public class ChangeEmployeePasswordOperation extends OperationBase
{

	/**
	 * Изменить пароль текущему сотруднику
	 * @param password - новый пароль
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void changePassword(String password) throws BusinessException, BusinessLogicException
	{
		try
		{
			EmployeeService employeeService = GateSingleton.getFactory().service(com.rssl.phizic.gate.employee.EmployeeService.class);
			employeeService.selfPasswordChange(password);
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
}
