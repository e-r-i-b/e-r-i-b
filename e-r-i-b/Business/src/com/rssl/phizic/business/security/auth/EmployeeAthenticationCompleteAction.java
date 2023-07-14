package com.rssl.phizic.business.security.auth;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeDataImpl;

/**
 * @author Evgrafov
 * @ created 18.12.2006
 * @ $Author: omeliyanchuk $
 * @ $Revision: 20696 $
 */

/**
 * ¬ –≈¿À»«¿÷»ﬂ’ Õ≈ ƒŒÀ∆ÕŒ ¡€“‹ —Œ—“ŒﬂÕ»ﬂ!!!!!!
 */
public class EmployeeAthenticationCompleteAction implements AthenticationCompleteAction
{
	private static final EmployeeService employeeService = new EmployeeService();

	public void execute(AuthenticationContext context) throws SecurityException
	{
		try
		{
			Employee employee = employeeService.findByLogin((BankLogin) context.getLogin());
			EmployeeContext.getEmployeeDataProvider().setEmployeeData(new EmployeeDataImpl(employee));
		}
		catch (BusinessException e)
		{
			throw new SecurityException(e);
		}
	}
}