package com.rssl.phizic.authgate.authorization;

import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.authgate.AuthGateException;
import com.rssl.phizic.authgate.AuthGateLogicException;
import com.rssl.phizic.authgate.AuthConfig;

/**
 * @author Gainanov
 * @ created 21.07.2009
 * @ $Author$
 * @ $Revision$
 */
public interface AuthGateService
{
	/**
	 * проверить результат аутентификации по переданному идентификатору
	 * @param params параметры для проверки
	 * @return параметры результата аутентификации (собственно сам результат удачно или нет и дополнительные параметры such as ФИО и т.п.)
	 */
	public AuthParamsContainer checkSession(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException;

	/**
	 * подготовка дополнительной аутентификации
	 * @param params парметры для подготовки (идентификатор основной сессии + доп. параметры)
	 * @return параметры подготовки (токен для передачи на страницу доп.аутентификации...)
	 */
	public AuthParamsContainer prepareAuthentication(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException;

	/**
	 *  проверка результатов доп. аутентификации
	 * @param params параметры для проверки (токен переданный со страницы аутентификации...)
	 * @return параметры результатов проверки (сам результат, тип аутентификации и т.п.)
	 */
	public AuthParamsContainer checkAuthentication(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException;

	/**
	 * подготовка для передачи контекста сессии в стороннюю систему
	 * @param params параметры для передачи (идентификатор сессии и т.п.)
	 * @return токен для переноса контекста сессии
	 */
	public AuthParamsContainer moveSession(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException;

	/**
	 * закрытие сессии внешней системы авторизации (ЦСА)
	 * @param params параметры (идентификатор сессии)
	 * @return результат выполнения запроса.
	 */
	public AuthParamsContainer closeSession(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException;

	/**
	 * Открытие сессии в СБОЛ ЦА
	 * @param userId идентфикатор пользователя(iPas)
	 * @param password пароль
	 * @return результат выполнения запроса(статус и токен).
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public AuthParamsContainer prepareSession(String userId, String password) throws AuthGateException, AuthGateLogicException;

	/**
	 * @return конфиг цса
	 */
	public AuthConfig getConfig();
}
