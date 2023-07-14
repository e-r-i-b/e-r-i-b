package com.rssl.phizic.common.types.security;

/**
 * @author osminin
 * @ created 09.05.2013
 * @ $Author$
 * @ $Revision$
 *
 * уровень безопасности
 */
public enum SecurityType
{
	HIGHT("ѕрисвоен высокий уровень безопасности."),                                // ¬ысокий уровень
	MIDDLE("ѕрисвоен средний уровень безопасности."),                               // —редний уровень
	LOW("ѕрисвоен низкий уровень безопасности: клиент доверенный.");                // Ќизкий уровень

	private String description;

	SecurityType(String description)
	{
		this.description = description;
	}

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return description;
	}
}
