package com.rssl.phizicgate.manager.routing;

import com.rssl.phizic.logging.messaging.System;
/**
 * типы шлюзов
 * @author basharin
 * @ created 19.12.2012
 * @ $Author$
 * @ $Revision$
 */

public enum NodeType
{
	COD("ЦОД", System.cod),

	IQWAVE("IQWave", System.iqwave),

	RETAIL_V6("Retail V6", System.retail),

	GOROD("ИСППН Город", System.gorod),

	CPFL("ЦПФЛ", System.cpfl),

	ENISEY("Енисей", System.enisey),

	SOFIA_BILLING("София-биллинг", System.sofia),

	SOFIA("София-ВМС", System.cod),
		
	XBANK("XBank", System.xbank);

	private String description;
	private System system;

	NodeType(String description, System system)
	{
		this.description = description;
		this.system = system;
	}

	public String getDescription()
	{
		return description;
	}

	public System getSystem()
	{
		return system;
	}
}

