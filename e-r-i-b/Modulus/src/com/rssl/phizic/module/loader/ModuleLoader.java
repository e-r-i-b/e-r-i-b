package com.rssl.phizic.module.loader;

import com.rssl.phizic.common.types.annotation.ThreadSafe;

/**
 * @author Erkin
 * @ created 07.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Загрузчик модуля
 */
@ThreadSafe
public interface ModuleLoader
{
	/**
	 * Загружает модуль
	 * ВАЖНО! Метод не должен выбрасывать исключений
	 */
	public void start();

	/**
	 * Выгружает модуль
	 * ВАЖНО! Метод не должен выбрасывать исключений
	 */
	public void stop();
}
