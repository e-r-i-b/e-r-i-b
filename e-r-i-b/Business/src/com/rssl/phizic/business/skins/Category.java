package com.rssl.phizic.business.skins;

/**
 * User: Balovtsev
 * Date: 20.05.2011
 * Time: 13:49:56
 *
 * Категория к которой относится скин
 */
public enum Category
{
	NONE,   // Неиспользуемый (скрытый) скин
	ADMIN,  // АРМ сотрудника
	CLIENT, // Клиентское
	BOTH;   // Оба перечисленных выше

	/**
	 * @return true, если клиентская категория
	 */
	public boolean isClient()
	{
		return this == BOTH || this == CLIENT;
	}

	/**
	 * @return true, если категория сотрудника
	 */
	public boolean isAdmin()
	{
		return this == BOTH || this == ADMIN;
	}
}
