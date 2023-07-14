package com.rssl.phizic.config;

import com.rssl.phizic.utils.PropertyHelper;

import java.util.Properties;

/**
 * !!! ������������� ��� ������ �� ������������ � ���� !!!
 * @author Roshka
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class ConfigurationContext
{
	private static final String ACTIVE_CONFIGURATION = "current.config.name";
	private static final String CONFIGS_PROPERTIES   = "configs.properties";

	private static ConfigurationContext intstance = new ConfigurationContext();

	private String activeConfiguration;

	private ConfigurationContext()
	{
		final Properties properties = PropertyHelper.readProperties(CONFIGS_PROPERTIES);
		this.activeConfiguration = (String) properties.get(ACTIVE_CONFIGURATION);
	}

	/**
	 * ���������
	 * @return
	 */
	public static ConfigurationContext getIntstance()
	{
		return intstance;
	}

	/**
	 * �������� ������������ ����������.
	 * @return  �������� ������������
	 */
	public String getActiveConfiguration()
	{
		return activeConfiguration;
	}
}