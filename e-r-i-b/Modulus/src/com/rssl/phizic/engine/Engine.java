package com.rssl.phizic.engine;

import com.rssl.phizic.module.loader.LoadOrder;

/**
 * @author Erkin
 * @ created 10.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Механизм (движок)
 */
public interface Engine
{
	/**
	 * @return название механизма
	 */
	String getName();

	/**
	 * @return порядковый номер в очереди загрузки
	 */
	LoadOrder getLoadOrder();

	/**
	 * Запустить механизм
	 */
	void start();

	/**
	 * Остановить механизм
	 */
	void stop();
}
