package com.rssl.phizic.web.util;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.EmployeeDataProvider;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.scheme.AccessHelper;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.StringHelper;

/**
 * Created by IntelliJ IDEA.
 * User: Zhuravleva
 * Date: 22.04.2009
 */
public class EmployeeInfoUtil
{

		private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

		/**
		 * Получение информации по текущему пользователю
		 * @return данные пользователя
		 */
		public static Employee getEmployeeInfo()
		{
			try
		    {
			    EmployeeDataProvider dataProvider = EmployeeContext.getEmployeeDataProvider();
			    if (dataProvider == null)
		            return null;
				EmployeeData data = dataProvider.getEmployeeData();
				if (data == null)
					return null;
				return data.getEmployee();
		    }
			catch (Exception e)
	        {
	            log.error("Ошибка получения данных сотрудника", e);
		        return null;
	        }

		}

	/**
	 * @return подразделение сотрудника
	 */
	public static Department getCurrentDepartment()
	{
		try
		{
			DepartmentService departmentService = new DepartmentService();
			return departmentService.findById(getEmployeeInfo().getDepartmentId());
		}
		catch (Exception e)
	    {
	        log.error("Ошибка получения данных департамента сотрудника", e);
		    return null;
	    }
	}

	/**
	 * Получение категории схемы прав сотрудника
	 * @param employeeId - id сотрудника
	 * @return категория схемы прав
	 */
	public static String getAccessCategory(Long employeeId)
	{
		try
		{
			EmployeeService employeeService = new EmployeeService();
			Employee employee = employeeService.findById(employeeId);
			if (employee == null)
			{
				return null;
			}
			SchemeOwnService shemeService = new SchemeOwnService();
			AccessScheme accessScheme = shemeService.findScheme(employee.getLogin(), AccessType.employee);
			return (accessScheme==null)?"":accessScheme.getCategory();
		}
		catch (Exception e)
		{
			log.error("Ошибка определения категории схемы прав сотрудника", e);
			return "";
		}
	}

	/**
	 * @return true -- CSAAdmin работает в многоблочной структуре
	 */
	public static boolean isMultiBlockMode()
	{
		return ConfigFactory.getConfig(CSAAdminGateConfig.class).isMultiBlockMode();
	}

	/**
	 * @return true -- письма работают в многоблочной структуре
	 */
	public static boolean isMailMultiBlockMode()
	{
		return ConfigFactory.getConfig(CSAAdminGateConfig.class).isMailMultiBlockMode();
	}

	/**
	 * @return нужно ли обновлять информацию о менеджере
	 */
	public static boolean needUpdateManagerInfo()
	{
		try
		{
			Employee employee = getEmployeeInfo();
			if (AccessHelper.isAdmin(employee.getLogin()))
				return false;

			if (!PermissionUtil.impliesService("SelfPersonalManagerInformationManagement"))
				return false;

			return  StringHelper.isEmpty(employee.getManagerId())       || employee.getChannelId() == null                  ||
					StringHelper.isEmpty(employee.getManagerPhone())    || StringHelper.isEmpty(employee.getManagerEMail()) ||
					StringHelper.isEmpty(employee.getManagerLeadEMail());
		}
		catch (BusinessException e)
		{
			log.error("Ошибка определения необходимости заполнения персональных данных.", e);
			return false;
		}
	}
}
