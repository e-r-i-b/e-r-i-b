package com.rssl.phizicgate.ips;

import com.rssl.phizic.utils.test.JUnitDatabaseConfig;
import com.rssl.phizic.utils.test.JUnitConfig;
import com.rssl.phizic.utils.PropertyHelper;

import java.util.Properties;

/**
 * @author Erkin
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */
class IPSJUnitDatabaseConfig implements JUnitDatabaseConfig
{
	private final Properties configReader;

	IPSJUnitDatabaseConfig()
	{
		configReader = PropertyHelper.readProperties("ips.properties");
	}

	public String getDriver()
	{
		return configReader.getProperty(JUnitConfig.DRIVER_KEY);
	}

	public String getURI()
	{
		return configReader.getProperty(JUnitConfig.URI_KEY);
	}

	public String getLogin()
	{
		return configReader.getProperty(JUnitConfig.LOGIN_KEY);
	}

	public String getPassword()
	{
		return configReader.getProperty(JUnitConfig.PASSWORD_KEY);
	}

	public String getDataSourceName()
	{
		return configReader.getProperty("com.rssl.iccs.datasource");
	}
}
