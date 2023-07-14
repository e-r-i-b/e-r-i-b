package com.rssl.phizic.csaadmin.business.login;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Типы блокировок
 */

public enum BlockType
{
	/**
	 * заблокировано системно
	 */
	system("Доступ в систему запрещен"),
	/**
	 * заблокировано сотрудником из АРМ Сотрудника
	 */
	employee("Доступ в систему запрещен администратором"),
	/**
	 * заблокировано из-за большого количества неправильного ввода пароля
	 */
	wrongLogons("Доступ в систему запрещен"),
	/**
	 * заблокировано из-за длительной неактивности
	 */
	longInactivity("Доступ в систему запрещен");

	private String prefix;

	BlockType(String prefix)
	{
		this.prefix = prefix;
	}

	public String getPrefix()
	{
		return prefix;
	}
}
