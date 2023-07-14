package com.rssl.phizic.gate;

/**
 * @author hudyakov
 * @ created 09.12.2009
 * @ $Author$
 * @ $Revision$
 */

@Deprecated
public interface Routable
{
	/**
	 * Сохранить в объекте информацию о маршрутизации
	 * @param info
	 */
	void storeRouteInfo(String info);

	/**
	 * Получить из объекта информацию о маршрутизации
	 * @return info
	 */
	String restoreRouteInfo();

	/**
	 * Убрать из объекта информацию для маршрутизации,
	 * восстановить оригинальное состояние объекта.
	 * @return информация о маршрутизации
	 */
	 String removeRouteInfo();
}
