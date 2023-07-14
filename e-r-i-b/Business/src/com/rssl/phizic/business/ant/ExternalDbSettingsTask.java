package com.rssl.phizic.business.ant;

/**
 * @author Omeliyanchuk
 * @ created 22.07.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Если таску необходимо задать параметры подключения к БД из скрипта
 */
public interface ExternalDbSettingsTask
{
	/**
	 * Инициализировать параметрами из скрипта
	 * @return
	 */
	String getInitByParams();

	void setInitByParams(String initByParams);

	/**
	 * Логин для подсоединения к БД
	 * @return
	 */
	String getLogin();

	void setLogin(String login);

	/**
	 * Пароль для подсоединение к бд
	 * @return
	 */
	String getPassword();

	void setPassword(String password);

}
