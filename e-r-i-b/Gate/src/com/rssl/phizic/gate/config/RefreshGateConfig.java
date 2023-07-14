package com.rssl.phizic.gate.config;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.cod.CodGateConfig;

/**
 * ����������� ������ �����.
 * ������������ ��� �����-���
 * @author bogdanov
 * @ created 27.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class RefreshGateConfig implements WSGateConfig
{
	/**
	 * ��� ��������.
	 */
	private String routeCode;

	/**
	 * @param routeCode ��� ��������
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
	 * @return ��� ��������.
	 */
	public String getRouteCode()
	{
		return routeCode;
	}

	/**
	 * @param routeCode ��� ��������.
	 */
	public void setRouteCode(String routeCode)
	{
		this.routeCode = routeCode;
	}
}
