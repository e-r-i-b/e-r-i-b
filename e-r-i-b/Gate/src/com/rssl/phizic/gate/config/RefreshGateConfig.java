package com.rssl.phizic.gate.config;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.cod.CodGateConfig;

/**
 * Обновляемый конфиг гейта.
 * Используется для Софии-ВМС
 * @author bogdanov
 * @ created 27.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class RefreshGateConfig implements WSGateConfig
{
	/**
	 * Код маршрута.
	 */
	private String routeCode;

	/**
	 * @param routeCode код маршрута
	 */
	public RefreshGateConfig(String routeCode)
	{
		this.routeCode = routeCode;
	}

	public String getURL()
	{
		return ConfigFactory.getConfig(CodGateConfig.class).getUrlToRoute(routeCode);
	}

	/**
	 * @return код маршрута.
	 */
	public String getRouteCode()
	{
		return routeCode;
	}

	/**
	 * @param routeCode код маршрута.
	 */
	public void setRouteCode(String routeCode)
	{
		this.routeCode = routeCode;
	}
}
