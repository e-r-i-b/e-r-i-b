package com.rssl.phizic.gate;

import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Collection;

/**
 * Фабрика, создающая гейт
 */
public interface GateFactory
{
	/**
	 * Конфигурация фабрики служб
	 */
	void initialize() throws GateException;

	/**
	 * Возвращает гейтовый сервис по классу сервиса
	 * @param serviceInterface Интерфейс службы
	 * @return служба
	 */
	<S extends Service> S service(Class<S> serviceInterface);

	/**
	 * @param configInterface интерфес конфига подключения к шлюзу
	 * @return
	 */
	<C extends com.rssl.phizic.gate.config.GateConfig> C config(Class<C> configInterface);

	/**
	 * Возвращает гейтовые сервисы
	 * @return сервисы
	 */
	Collection<? extends Service> services();
}
