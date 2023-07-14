package com.rssl.phizic.utils.test;

/**
 * @author Omeliyanchuk
 * @ created 21.07.2008
 * @ $Author$
 * @ $Revision$
 */

public interface JUnitDatabaseConfig
{
	/**
	 * Полное имя класса драйвера
	 * @return
	 */
    String getDriver();

	/**
	 * Полная строка подключения 
	 * @return
	 */
    String getURI();

	/**
	 * Имя пользователя
	 * @return
	 */
    String getLogin();

	/**
	 * Пароль
	 * @return
	 */
    String getPassword();

	/**
	 * Имя датасорса
	 * @return
	 */
	String getDataSourceName();
}
