package com.rssl.phizic.operations.csaadmin.auth;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.csaadmin.EmployeeImportService;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizicgate.csaadmin.service.authentication.CSAAdminAuthService;
import com.rssl.phizicgate.csaadmin.service.types.AuthenticationParameters;
import org.apache.commons.collections.MapUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция поиска/синхронизации сотрудника для авторизации по токену из ЦСА Админ
 */
public class PostCSAAdminAuthOperation
{
	private static final SecurityService securityService = new SecurityService();
	private static final EmployeeImportService employeeImportService = new EmployeeImportService();

	private CSAAdminAuthService getAuthService()
	{
		return new CSAAdminAuthService();
	}

	/**
	 * Заполнить контекст аутентификации
	 * @param token - токен
	 * @param parameters - дополнительные параметры аутентификации
	 * @return контекст аутентификации
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public AuthenticationContext fillAuthenticationContext(String token, Map<String, String> parameters) throws BusinessException, BusinessLogicException
	{
		try
		{
			AuthenticationParameters authenticationParameters = getAuthService().getAuthenticationParameters(token);
			AuthenticationContext context = AuthenticationContext.getContext();
			context.setCSA_SID(authenticationParameters.getSessionId());
			context.setStartJobPagePath(authenticationParameters.getAction());
			CommonLogin login = findLogin(authenticationParameters);
			context.setLogin(login);
			Map<String, String> authenticationJobPageParameters = new HashMap<String, String>();
			authenticationJobPageParameters.putAll(parameters);
			if(MapUtils.isNotEmpty(authenticationParameters.getParameters()))
			{
				authenticationJobPageParameters.putAll(authenticationParameters.getParameters());
			}
			context.setAuthenticationParameters(authenticationJobPageParameters);
			return context;
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

	/**
	 * Поиск логина сотрудника
	 * Если логин сотрудника будет не найден, то создаем нового сотрудника в блоке на основе данных из ЦСА Админ
	 * Если логин найден, то проверяем изменились ли данные в ЦСА с момента последнего входа в блок. Если данные изменились, то обновляем сотрудника.
	 * @param authenticationParameters - данные аутентификации
	 * @return логин сотрудника
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private CommonLogin findLogin(AuthenticationParameters authenticationParameters) throws BusinessException, BusinessLogicException
	{
		try
		{
			String csaUserId = authenticationParameters.getLoginId();
			BankLogin login = securityService.getActiveBankLoginByCsaUserId(csaUserId);
			if(login == null)
				return employeeImportService.importEmployee(csaUserId).getLogin();

			Calendar lastEmployeeUpdateDate = authenticationParameters.getLastEmployeeUpdateDate();
			if(login.getLastSynchronizationDate().compareTo(lastEmployeeUpdateDate) < 0)
				employeeImportService.updateEmployee(login);

			return login;
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}
}
