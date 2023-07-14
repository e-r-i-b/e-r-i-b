package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: chegodaev $
 * @ $Revision: 65430 $
 */

public class CategoryAssignAccessHelper extends CategoryDependedSchemeOperationHelper implements AssignAccessHelper
{
	private String                   scope;

	private List<SharedAccessScheme> schemes;
	private AuthenticationConfig     config;

	/**
	 * @param scope  см. SecurityService.SCOPE_*
	 * @param category см. AccessCategory.CATEGORY_*
	 */
	public CategoryAssignAccessHelper(String scope, String category, boolean isCaAdmin)
	{
		super(category, isCaAdmin);
		this.scope = scope;
	}

	/**
	 * получить все схемы прав текущей категории
	 * @return
	 * @throws BusinessException
	 */
	public List<SharedAccessScheme> getSchemes() throws BusinessException
	{
		List<SharedAccessScheme> filteredResult = new ArrayList<SharedAccessScheme>();
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		if(schemes == null)
		{
			schemes = getAllSchemes();
		}

		boolean CAAdmin = employee.isCAAdmin();

		for (SharedAccessScheme scheme : schemes)
		{
			if (CAAdmin || !scheme.isCAAdminScheme())
				filteredResult.add(scheme);
		}

		return filteredResult;
	}

	private List getAllSchemes() throws BusinessException
	{
		try
		{
			return GateSingleton.getFactory().service(com.rssl.phizic.gate.schemes.AccessSchemeService.class).getList(new String[]{getCategory()});
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	public AuthenticationConfig getAuthenticationConfig()
	{
		if(config == null)
		{
			config = ConfigFactory.getConfig(AuthenticationConfig.class, scope.equals(SecurityService.SCOPE_CLIENT) ? Application.PhizIC : Application.PhizIA);
		}
		return config;
	}

	/**
	 * @return хелпер для клиентов
	 */
	public static final AssignAccessHelper createClient()
	{
		return new CategoryAssignAccessHelper(SecurityService.SCOPE_CLIENT, AccessCategory.CATEGORY_CLIENT, false);
	}

	/**
	 * @return хелпер для сотрудников
	 */
	public static final AssignAccessHelper createEmployee()
	{
		boolean isCaAdmin = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isCAAdmin();
		return new CategoryAssignAccessHelper(SecurityService.SCOPE_EMPLOYEE, AccessCategory.CATEGORY_EMPLOYEE, isCaAdmin);
	}

	/**
	 * @return хелпер для администраторов.
	 */
	public static final AssignAccessHelper createAdmin()
	{
		boolean isCaAdmin = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isCAAdmin();
		return new CategoryAssignAccessHelper(SecurityService.SCOPE_EMPLOYEE, AccessCategory.CATEGORY_ADMIN, isCaAdmin);
	}
}
