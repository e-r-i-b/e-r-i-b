package com.rssl.phizic.authgate;

import java.util.HashMap;
import java.util.Map;

/**
 * Контейнер параметров авторизации.
 * Заполняется в случае аутентификации во внешней системе из которой приходит пользователь с набором параметров
 *
 * @author Gainanov
 * @ created 21.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class AuthParamsContainer
{
	// основные параметры
	private Map<String, String> parameters;

	public AuthParamsContainer()
	{
		parameters = new HashMap<String, String>();
	}

	/**
	 * добавить  параметр
	 * @param name имя
	 * @param value значение
	 */
	public void addParameter(String name, String value)
	{
		parameters.put(name, value);
	}

	/**
	 * получить  параметр
	 * @param name имя
	 * @return значение
	 */
	public String getParameter(String name)
	{
		return parameters.get(name);
	}

	public void clear()
	{
		parameters.clear();
	}
}
