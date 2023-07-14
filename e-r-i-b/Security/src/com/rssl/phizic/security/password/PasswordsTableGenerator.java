package com.rssl.phizic.security.password;

import com.rssl.phizic.auth.Login;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 12.09.2005
 * Time: 14:29:30
 * Description: Интерфейс для создания таблицы паролей
 */
public interface PasswordsTableGenerator
{

	/**
	 * Метод создает таблицу паролей для переданного логина
	 * @param login - Логин
	 * @return массив Паролей
	 * @throws Exception
	 */
	public Password[] generatePasswords(Login login) throws Exception;
}
