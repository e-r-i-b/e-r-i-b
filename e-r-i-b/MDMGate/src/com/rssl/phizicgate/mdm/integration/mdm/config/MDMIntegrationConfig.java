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
 * ������ ���������� � ���
 */

public class MDMIntegrationConfig extends Config
{
	private static final String TIMEOUT_KEY = "com.rssl.es.integration.mdm.timeout";

	private int mdmTimeout;

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 * @param reader �����.
	 */
	public MDMIntegrationConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return ������� �� ���������� � ���
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
