package com.rssl.phizic.operations.csaadmin;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CSAAdminLoginGenerator;
import com.rssl.phizic.auth.DublicateUserIdException;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.EmployeeImpl;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.business.schemes.AccessSchemeService;
import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.officies.AllowedDepartment;
import com.rssl.phizic.gate.employee.Employee;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.login.Login;
import com.rssl.phizic.operations.csaadmin.auth.SynchronizeEmployeeUtil;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizicgate.csaadmin.service.authentication.CSAAdminAuthService;
import com.rssl.phizicgate.csaadmin.service.types.EmployeeSynchronizationData;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 30.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Сервис синхронизации данных сотрудника
 */

public final class EmployeeImportService
{
	private static final SecurityService securityService = new SecurityService();
	private static final com.rssl.phizic.business.employees.EmployeeService businessEmployeeService = new com.rssl.phizic.business.employees.EmployeeService();
	private static final AccessSchemeService accessSchemeService = new AccessSchemeService();
	private static final SchemeOwnService schemeOwnService = new SchemeOwnService();


	/**
	 * Создаем сотрудника на основе данных из ЦСА Админ
	 * @param externalId внешний идентификатор клиента
	 * @return созданный сотрудник
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public com.rssl.phizic.business.employees.Employee importEmployee(final String externalId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<com.rssl.phizic.business.employees.Employee>()
			{
				public com.rssl.phizic.business.employees.Employee run(Session session) throws Exception
				{
					EmployeeSynchronizationData employeeSynchronizationData = getEmployeeSynchronizationData(externalId);
					Employee gateEmployee = employeeSynchronizationData.getEmployee();

					businessEmployeeService.clearManagerInfo(gateEmployee.getManagerId());
					Login login = employeeSynchronizationData.getLogin();
					BankLogin bankLogin = createBusinessLogin(login, login.getLastSynchronizationDate());

					com.rssl.phizic.business.employees.Employee businessEmployee = createFromGateEmployee(gateEmployee);
					businessEmployee.setLogin(bankLogin);
					businessEmployeeService.add(businessEmployee);

					SharedAccessScheme businessAccessScheme = getBusinessAccessScheme(gateEmployee.getScheme());
					schemeOwnService.setScheme(bankLogin, AccessType.employee,businessAccessScheme);

					List<AllowedDepartment> allowedDepartments = employeeSynchronizationData.getAllowedDepartments();
					businessEmployeeService.updateAllowedDepartmentByGate(businessEmployee,allowedDepartments);
					return businessEmployee;
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Обновляем сотрудника данными из ЦСА Админ
	 * @param login - логин сотрудника в блоке
	 * @throws BusinessException
	 */
	public void updateEmployee(final BankLogin login) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					EmployeeSynchronizationData employeeSynchronizationData = getEmployeeSynchronizationData(login.getCsaUserId());

					Login gateLogin = employeeSynchronizationData.getLogin();
					securityService.setPasswordHash(login, gateLogin.getPassword(),false);

					SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
					if (!gateLogin.getUserId().equals(securityConfig.getDefaultAdminName()))
					{
						Employee gateEmployee = employeeSynchronizationData.getEmployee();
						businessEmployeeService.clearManagerInfo(gateEmployee.getManagerId());
						com.rssl.phizic.business.employees.Employee businessEmployee = businessEmployeeService.findByLogin(login);
						SynchronizeEmployeeUtil.update(businessEmployee, gateEmployee);
						businessEmployeeService.update(businessEmployee);

						SharedAccessScheme businessAccessScheme = getBusinessAccessScheme(gateEmployee.getScheme());
						schemeOwnService.setScheme(login, AccessType.employee,businessAccessScheme);

						List<AllowedDepartment> allowedDepartments = employeeSynchronizationData.getAllowedDepartments();
						businessEmployeeService.updateAllowedDepartmentByGate(businessEmployee,allowedDepartments);
					}

					securityService.updateLastSynchronizationDate(login, gateLogin.getLastSynchronizationDate());
					return null;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException("Не удалось обновить сотрудника данными из ЦСА Админ",e);
		}
	}


	private CSAAdminAuthService getAuthService()
	{
		return new CSAAdminAuthService();
	}

	private EmployeeSynchronizationData getEmployeeSynchronizationData(String externalId) throws GateException, GateLogicException
	{
		return getAuthService().getEmployeeSynchronizationParameters(externalId);
	}

	/**
	 * Генерация логина в блоке на основе логина из ЦСА Админ
	 * @param gateLogin - логин ЦСА Админ
	 * @param lastSynchronizationDate - дата последней синхронизации в ЦСА Админ
	 * @return логин сотрудника в блоке
	 * @throws BusinessException
	 */
	private BankLogin createBusinessLogin(com.rssl.phizic.gate.login.Login gateLogin, Calendar lastSynchronizationDate) throws BusinessException
	{
		//noinspection SpellCheckingInspection
		try
		{
			CSAAdminLoginGenerator loginGenerator = new CSAAdminLoginGenerator(gateLogin.getUserId(), gateLogin.getUserId(), lastSynchronizationDate);
			return loginGenerator.generate();
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException("Не удалось сгенерировать логин сотрудника",e);
		}
		catch (DublicateUserIdException e)
		{
			throw new BusinessException("Не удалось сгенерировать логин сотрудника, так как такой логин с userId "+ gateLogin.getUserId() + " уже есть в блоке", e);
		}
	}

	/**
	 * Создание бизнесового сотрудника на основе гейтовых данных
	 * @param gateEmployee - гейтовое представление сотрудника
	 * @return бизнесовый сотруник
	 */
	private com.rssl.phizic.business.employees.Employee createFromGateEmployee(Employee gateEmployee)
	{
		com.rssl.phizic.business.employees.Employee businessEmployee = new EmployeeImpl();
		SynchronizeEmployeeUtil.update(businessEmployee, gateEmployee);
		return businessEmployee;
	}

	/**
	 * Получение схемы прав сотрудника на основе схемы из ЦСА Админ.
	 * @param gateScheme - схема прав из ЦСА Админ
	 * @return бизнесовая схема прав
	 * @throws BusinessException
	 */
	private SharedAccessScheme getBusinessAccessScheme(com.rssl.phizic.gate.schemes.AccessScheme gateScheme) throws BusinessException
	{
		SharedAccessScheme businessScheme = accessSchemeService.findByExternalId(gateScheme.getId());
		if(businessScheme == null)
		{
			businessScheme = new SharedAccessScheme();
			businessScheme.setCategory(gateScheme.getCategory());
			businessScheme.setName(gateScheme.getName());
			businessScheme.setExternalId(gateScheme.getExternalId());
			businessScheme.setCAAdminScheme(gateScheme.isCAAdminScheme());
			businessScheme.setVSPEmployeeScheme(gateScheme.isVSPEmployeeScheme());
			accessSchemeService.save(businessScheme);
		}
		return businessScheme;
	}
}
