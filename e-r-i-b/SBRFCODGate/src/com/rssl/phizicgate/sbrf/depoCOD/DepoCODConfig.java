package com.rssl.phizicgate.sbrf.depoCOD;

import com.rssl.phizic.config.*;
import com.rssl.phizic.gate.config.GateConnectionConfig;

/**
 * ������ ��� DepoCOD
 * @author Jatsky
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */

public class DepoCODConfig extends Config
{
	private static final String URL_WS_DEPOCOD_KEY = "depocod.webservice.url";

	public DepoCODConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
	}

	/**
	 * �������� ����� ��� �������
	 * @return ����� ��� �������
	 */
	public String getUrl()
	{
		return getProperty(URL_WS_DEPOCOD_KEY);
	}

	/**
	 * �������� ����� �������� ������ �� ������� ������� � �������������
	 * @return ����� �������� ������ �� ������� ������� � �������������
	 */
	public String getTimeout()
	{
		return getProperty(GateConnectionConfig.CONNECTION_TIMEOUT);
	}
}
