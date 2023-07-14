package com.rssl.phizic.config.debug;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 *
 * ������ ��� ���������� �������� ���������/���������� �����������. ������ ��� �������.
 * �� ����������� ����������� �� ������� �����������.
 *
 * @ author: Gololobov
 * @ created: 08.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class DebugRefreshConfig extends Config
{
	//����������� ��������� ������ �����(true - ��������/false - ���������)
	private static final String RATE_LOGING_ENABLED = "com.rssl.iccs.rate.loging.enabled";

	private boolean needRateLoging; //������� ������������� ����������� ��������� ������ �����

	public DebugRefreshConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * ���������� ������
	 * @throws ConfigurationException
	 */
	protected void doRefresh() throws ConfigurationException
	{
		needRateLoging = getBoolProperty(RATE_LOGING_ENABLED);
	}

	/**
	 * @return - ����� �� ���������� ��������� ������ �����
	 */
	public boolean isNeedRateLoging()
	{
		return needRateLoging;
	}
}
