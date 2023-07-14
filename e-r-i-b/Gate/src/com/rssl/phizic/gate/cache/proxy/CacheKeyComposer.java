package com.rssl.phizic.gate.cache.proxy;

import com.rssl.phizic.common.types.transmiters.Pair;

import java.io.Serializable;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Интерфейс для создания классов по преобразованию параметров запросов в ключи кеша и обратно
 */
public interface CacheKeyComposer
{
	/**
	 * Сформировать ключ
	 * @param args аргументы вызова метода
	 * @param params параметры для работа композера
	 * @return ключ
	 */
	Serializable getKey(Object[] args, String params);

	/**
	 * Сформировать ключи для методов возвращающих groupResult и принимающих массивы
	 * @param args аргументы вызова метода
	 * @param params параметры для работа композера
	 * @return список пар - ключ - ключевой объект
	 */
	List<Pair<Serializable, Object>> getKeys(Object[] args, String params);

	/**
	 * Вычесть из параметров групповой операции, те по которым результат уже есть
	 * @param args аргументы вызова метода
	 * @param params параметры для работа композера
	 * @return аргументы для вызова метода, или null если вызов не требуется
	 */
	Object[] substractReadyArgs(Object[] args, String params, List<Object> readyKeys);

	/**
	 * Формирует ключ для очистки кэша.
	 * @param result результат выполнения метода
	 * @param params дополнительные параметры
	 * @return ключ, или null если преобразование не может быть выполнено.
	 */
	Serializable getClearCallbackKey(Object result, Object[] params);

	/**
	 * Поддерживается ли формирование ключа из результата
	 * @return true - поддерживается.
	 */
	boolean isKeyFromResultSupported();
}
