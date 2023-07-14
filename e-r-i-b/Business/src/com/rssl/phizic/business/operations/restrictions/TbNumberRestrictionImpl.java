package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.security.config.SecurityConfig;
import org.apache.commons.lang.StringUtils;

/**
 * @author komarov
 * @ created 05.03.2014
 * @ $Author$
 * @ $Revision$
 */
public class TbNumberRestrictionImpl implements TbNumberRestriction
{
	public boolean accept(String tb) throws BusinessException
	{
		if (StringUtils.isEmpty(tb))
			return isSuperEmployee();

		return AllowedDepartmentsUtil.isDepartmentsAllowedByCode(tb, null, null);
	}

	private boolean isSuperEmployee()
	{
		SecurityConfig securityConfig= ConfigFactory.getConfig(SecurityConfig.class);
		String superUserName = securityConfig.getDefaultAdminName();
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();        return superUserName != null && superUserName.equals(employee.getLogin().getUserId());
	}
}
