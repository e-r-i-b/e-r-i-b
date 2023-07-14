package com.rssl.phizic.auth;

import com.rssl.phizic.security.SecurityDbException;

/**
 * User: Evgrafov
 * Date: 26.08.2005
 * Time: 14:56:41
 *
 * Обеспечивает потребности в информации, необходмой для проведения аутентификации
 */
public interface LoginInfoProvider
{
	/**
	 * нужно ли сменить пароль пользователю при следующем входе в систему
	 * @param login логин
	 * @return true == надо
	 */
	public boolean needChangePassword (CommonLogin login) throws SecurityDbException;
}
