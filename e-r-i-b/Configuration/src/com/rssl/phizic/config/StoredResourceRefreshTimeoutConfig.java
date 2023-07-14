package com.rssl.phizic.config;

import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

/**
 *
 * ����� �������� ��������� ���������, ������������ ������� ������ ����� ������������ �� ���� ��� ����������
 * ��������. � �������� ��������� ��������� ��. ��������� ��������� ���������� �� ��������� �������� � ���������
 * 'resources.timeout'.
 *
 * User: Balovtsev
 * Date: 24.10.2012
 * Time: 12:38:18
 */
public class StoredResourceRefreshTimeoutConfig extends Config implements StoredResourceRefreshTimeoutConfigMBean
{
	private Properties timeouts;

	public StoredResourceRefreshTimeoutConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		timeouts = new Properties();
		for (Map.Entry prop : getAllProperties().entrySet())
		{
			if (((String)prop.getKey()).endsWith("time2refresh.key"))
				timeouts.put(prop.getKey(), prop.getValue());
		}
	}

	/**
	 *
	 * ���������, �������� �� ��� ������������ ������
	 *
	 * @param key  ���� ������������ �������
	 * @param time ����� ����� ������ ��� �������� ��������� ���
	 *
	 * @return true ������������� ��������, false ���
	 */
	public boolean isExpired(ResourceTypeKey key, Calendar time)
	{
		if (time == null)
		{
			return true;
		}
		        
		return Calendar.getInstance().getTimeInMillis() - time.getTimeInMillis() > getValue(key);
	}

	/**
	 * �������� ��������� ���������
	 *
	 * @return Properties
	 */
	public Properties getTimeouts()
	{
		return timeouts;
	}

	protected long getValue(ResourceTypeKey key)
	{
		return 1000 * 60 * Long.valueOf(getProperty(key.toValue()));
	}
}
