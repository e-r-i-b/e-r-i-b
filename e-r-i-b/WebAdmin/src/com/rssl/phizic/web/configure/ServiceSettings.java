package com.rssl.phizic.web.configure;

/**
 * @author akrenev
 * @ created 22.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Настройки сервиса
 */

public class ServiceSettings
{
	private final String name;
	private final Mode mode;

	/**
	 * конструктор
	 * @param name имя
	 * @param mode режим
	 */
	public ServiceSettings(String name, Mode mode)
	{
		this.name = name;
		this.mode = mode;
	}

	/**
	 * @return имя сервиса
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return режим сервиса
	 */
	public Mode getMode()
	{
		return mode;
	}

	public static enum Mode
	{
		modeAndTimeout,
		serviceProvider,
		;
	}
}
