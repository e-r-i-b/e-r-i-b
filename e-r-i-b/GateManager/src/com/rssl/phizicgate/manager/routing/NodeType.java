package com.rssl.phizicgate.manager.routing;

import com.rssl.phizic.logging.messaging.System;
/**
 * ���� ������
 * @author basharin
 * @ created 19.12.2012
 * @ $Author$
 * @ $Revision$
 */

public enum NodeType
{
	COD("���", System.cod),

	IQWAVE("IQWave", System.iqwave),

	RETAIL_V6("Retail V6", System.retail),

	GOROD("����� �����", System.gorod),

	CPFL("����", System.cpfl),

	ENISEY("������", System.enisey),

	SOFIA_BILLING("�����-�������", System.sofia),

	SOFIA("�����-���", System.cod),
		
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

