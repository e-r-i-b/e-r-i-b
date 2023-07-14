package com.rssl.phizic.startup;

/**
 * @author Omeliyanchuk
 * @ created 12.05.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Интерфейс сервиса, который должен быть запущен при старте приложения.
 */
public interface StartupService
{
	/**
	 * метод вызывается для старта сервиса
	 * @throws Exception
	 */
	void start() throws Exception;

	/**
	 * метод вызывается для остановки приложения
	 */
	void stop();

	/**
	 * инициализирован ли сервис в рамках этого приложения.
	 * @return
	 */
	boolean isInitialized();
}
