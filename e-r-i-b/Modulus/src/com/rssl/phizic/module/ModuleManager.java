package com.rssl.phizic.module;

import com.rssl.phizic.common.types.annotation.ThreadSafe;

/**
 * @author Erkin
 * @ created 07.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Менеджер модулей
 * Знает, где и как держать модули
 */
@ThreadSafe
public interface ModuleManager
{
	/**
	 * Возвращает/создаёт модуль по его классу
	 * @param moduleClass - класс модуля
	 * @return модуль
	 */
	<T extends Module> T getModule(Class<T> moduleClass);
}
