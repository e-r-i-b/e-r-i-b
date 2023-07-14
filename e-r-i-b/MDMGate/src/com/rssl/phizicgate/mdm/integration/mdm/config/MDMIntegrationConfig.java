package com.rssl.phizicgate.mdm.integration.mdm.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author akrenev
 * @ created 15.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Конфиг интеграции с мдм
 */

public class MDMIntegrationConfig extends Config
{
	private static final String TIMEOUT_KEY = "com.rssl.es.integration.mdm.timeout";

	private int mdmTimeout;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 * @param reader ридер.
	 */
	public MDMIntegrationConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return таймаут на соединение к МДМ
	 */
	public int getMDMTimeout()
	{
		return mdmTimeout;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		mdmTimeout = getIntProperty(TIMEOUT_KEY);
	}
}
