package com.rssl.phizicgate.manager.services.objects;

/**
 * Свойство: возвращать маршрутизирующую информацию.
 *
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */

public interface RouteInfoReturner
{
	/**
	 * @return маршрутизирующаю информацию.
	 */
	public String getRouteInfo();

	/**
	 * @return идентифиактор объекта.
	 */
	public String getId();
}
