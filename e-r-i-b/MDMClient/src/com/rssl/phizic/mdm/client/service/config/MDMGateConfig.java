package com.rssl.phizic.mdm.client.service.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author akrenev
 * @ created 08.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��� ������� � ���������� ���
 */

public class MDMGateConfig extends Config
{
	private static final String URL_KEY = "com.rssl.phizic.mdmapp.listener.url";
	private static final String TIMEOUT_KEY = "com.rssl.phizic.mdmapp.listener.timeout";

	private String listenerUrl;
	private int listenerTimeout;

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 * @param reader �����.
	 */
	public MDMGateConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return ��� �� ������� ���������� ���������� ���
	 */
	public String getMDMAppUrl()
	{
		return listenerUrl;
	}

	/**
	 * @return ������� �� ���������� � ���
	 */
	public int getMDMAppTimeout()
	{
		return listenerTimeout;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		listenerUrl = getProperty(URL_KEY);
		listenerTimeout = getIntProperty(TIMEOUT_KEY);
	}
}
