package com.rssl.phizic.logging.system;

/**
 * @author Omeliyanchuk
 * @ created 16.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Модули логирования системы
 * при изменении необходимо поменять системный журнал
 */
public enum LogModule
{
	//Система кеширования
	Cache("Cache"),
	//Обработчик расписания
	Scheduler("Scheduler"),
	//Шлюз
	Gate("Gate"),
	//Ядро
	Core("Core"),
	//Веб
	Web("Web");

	private String value;

	LogModule(String value)
	{
		this.value = value;
	}

	public String toValue()
	{
		return value;
	}

	public String toString()
	{
		return value;
	}


	public static LogModule fromValue(String value)
	{
		if (value.equals(Cache.value))
			return Cache;
		if (value.equals(Scheduler.value))
			return Scheduler;
		if (value.equals(Gate.value))
			return Gate;
		if (value.equals(Core.value))
			return Core;
		if (value.equals(Web.value))
			return Web;

		throw new IllegalArgumentException("Неизвестный тип модуля [" + value + "]");
	}
}
