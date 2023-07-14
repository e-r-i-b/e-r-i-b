package com.rssl.phizic.operations.scheme;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.access.AssignAccessHelper;
import com.rssl.phizic.operations.access.CategoryAssignAccessHelper;
import com.rssl.phizic.operations.access.CategoryDependedSchemeOperationHelper;
import com.rssl.phizic.security.config.SecurityConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 29.05.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public class AccessHelper
{
	private static final EmployeeService employeeService = new EmployeeService();

	private static final Map<String, List<String>> categoriesByScope = initCategoriesByScope();
	private static final Map<String, String> scopeByCategory = initScopeByCategory();

	private static Map<String, String> initScopeByCategory()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(AccessCategory.CATEGORY_ADMIN, SecurityService.SCOPE_EMPLOYEE);
		map.put(AccessCategory.CATEGORY_EMPLOYEE, SecurityService.SCOPE_EMPLOYEE);
		map.put(AccessCategory.CATEGORY_CLIENT, SecurityService.SCOPE_CLIENT);
		return map;
	}

	private static Map<String, List<String>> initCategoriesByScope()
	{
		Map<String, List<String>> result = new HashMap<String, List<String>>();

		List<String> client = new ArrayList<String>();
		client.add(AccessCategory.CATEGORY_CLIENT);

		List<String> admin = new ArrayList<String>();
		admin.add(AccessCategory.CATEGORY_ADMIN);
		admin.add(AccessCategory.CATEGORY_EMPLOYEE);

		result.put(SecurityService.SCOPE_CLIENT, client);
		result.put(SecurityService.SCOPE_EMPLOYEE, admin);
		return result;
	}

	public static List<String> getScopeCategories(String scope)
	{
		return categoriesByScope.get(scope);
	}

	public static List<SchemeOperationHelper> createSchemeHelpers(String scope, boolean isCaAdmin) throws BusinessException
	{
		List<SchemeOperationHelper> helpers = new ArrayList<SchemeOperationHelper>();
		List<String> choices = categoriesByScope.get(scope);

		for (String category : choices)
		{
			CategoryDependedSchemeOperationHelper helper = new CategoryDependedSchemeOperationHelper(category, isCaAdmin);
			if (!helper.getServices().isEmpty())
				helpers.add(helper);
		}
		return helpers;
	}

	public static List<AssignAccessHelper> createAssignAccessHelpers(String scope, boolean isCaAdmin) throws BusinessException
	{
		List<AssignAccessHelper> helpers = new ArrayList<AssignAccessHelper>();
		List<String> choices = categoriesByScope.get(scope);

		for (String category : choices)
		{
			CategoryAssignAccessHelper helper = new CategoryAssignAccessHelper(scope, category, isCaAdmin);

			if (!helper.getServices().isEmpty())
				helpers.add(helper);
		}

		return helpers;
	}

	public static String getScopeByCategory(String category)
	{
		return scopeByCategory.get(category);
	}

	 /**
	 * Проверка на превышение лимита подключений сотрудников со схемой прав "Администратор"
	 * @param employee - сотрудник, которому меняют права 
	 * @return  true - лимит не превышен, false - превышен
	 * @throws BusinessException
	 */
	public static Boolean isExcessAdminsLimit(Employee employee) throws BusinessException
	{
		if (employee == null)
			throw new BusinessException("Не удалось идентифицировать сотрудника");

		return isExcessAdminsDepartments(employee.getDepartmentId());
	}

	/**
	 * Проверка на превышение лимита подключений сотрудников со схемой прав "Администратор" к выбранному департамену
	 * @param departmentId
	 * @return
	 * @throws BusinessException
	 */
	public static boolean isExcessAdminsDepartments(Long departmentId) throws BusinessException
	{
		if (departmentId == null)
			throw new BusinessException("Не удалось идентифицировать подразделение");

		int numberDepartmentAdmins = employeeService.getNumberAdmins(departmentId);
		return numberDepartmentAdmins + 1 <= getDepartmentAdminsLimit();
	}
	/**
	 * Получает заданный лимит на администраторов
	 * @return  int - максимально-возможное количество администраторов в системе
	 */
	private static int getDepartmentAdminsLimit()
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		return securityConfig.getDepartmentAdminsLimit();
	}

	/**
	 * Проверяет, является ли сотрудник админом
	 * @param employeeLogin логин, для которого надо изменяются права
	 * @return true - админ, false - не админ
	 * @throws BusinessException
	 */
	public static boolean isAdmin(CommonLogin employeeLogin) throws BusinessException
	{
		SchemeOwnService shemeService = new SchemeOwnService();
		AccessScheme accessScheme = shemeService.findScheme(employeeLogin, AccessType.employee);
		return (accessScheme != null  && accessScheme.getCategory().equals(AccessCategory.CATEGORY_ADMIN));
	}

	/**
	 * Проверяет, является ли сотрудник админом
	 * @param id идентификатор сотрудника
	 * @return true - админ, false - не админ
	 * @throws BusinessException
	 */
	public static boolean isAdmin(Long id) throws BusinessException
	{
		com.rssl.phizic.gate.employee.Employee employee = null;
		try
		{
			com.rssl.phizic.gate.employee.EmployeeService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.employee.EmployeeService.class);
			employee = service.getById(id);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
		return employee != null && employee.getScheme() != null && AccessCategory.CATEGORY_ADMIN.equals(employee.getScheme().getCategory());
	}

	/**
	 * Проверяет, является ли текущий пользователь администратором ЦА
	 * @return boolean true-является админом ЦА
	 */
	public static boolean isCAAdmin()
	{
		 EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
	     if (employeeData != null)
			return employeeData.getEmployee().isCAAdmin();
		else
		    return false;
	}
}