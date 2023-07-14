package com.rssl.phizic.operations.employees.authentication;

import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.CSAAdminEmployeeContext;
import com.rssl.phizic.context.CSAAdminEmployeeDataImpl;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizicgate.csaadmin.service.authentication.CSAAdminAuthService;
import com.rssl.phizicgate.csaadmin.service.types.EmployeeContextData;

/**
 * @author mihaylov
 * @ created 24.02.14
 * @ $Author$
 * @ $Revision$
 *
 * Инициализатор контекста ЦСА Админ сотрудника
 */
public class CSAAdminEmployeeContextInitializer implements AthenticationCompleteAction
{
	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		try
		{
			if(!ConfigFactory.getConfig(CSAAdminGateConfig.class).isMultiBlockMode())
				return;
			CSAAdminAuthService csaAdminAuthService = new CSAAdminAuthService();
			EmployeeContextData contextData = csaAdminAuthService.getCurrentEmployeeContextRq();
			boolean allTbAccess = EmployeeContext.getEmployeeDataProvider().getEmployeeData().isAllTbAccess();
			CSAAdminEmployeeDataImpl employeeData = new CSAAdminEmployeeDataImpl(contextData.getLoginId(), allTbAccess);
			CSAAdminEmployeeContext.setData(employeeData);
		}
		catch (GateException e)
		{
			throw new SecurityException(e);
		}
		catch (GateLogicException e)
		{
			throw new SecurityLogicException(e);
		}
	}
}
