package com.rssl.phizic.gate;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * Настройки шлюза
 */
public abstract class GateConfig extends Config
{
	/**
	 * Возвращает класс шлюза одной строкой
	 * @return класс шлюза одной строкой
	 */
    public abstract String getGateClass();

	/**
	 * Создаёт новый экземпляр шлюза
	 * @return новый экземпляр шлюза
	 */
    public abstract Gate buildGate();

	/**
	 * Возвращает время ожидания ответа от шлюза(для основного приложения)
	 * @return время ожидания (в миллисекундах)
	 */
    public abstract int getTimeout();

	protected GateConfig(PropertyReader reader)
	{
		super(reader);
	}
}
