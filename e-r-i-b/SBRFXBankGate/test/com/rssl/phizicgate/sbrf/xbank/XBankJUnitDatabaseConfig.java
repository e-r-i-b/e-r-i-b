package com.rssl.phizicgate.sbrf.xbank;

import com.rssl.phizic.utils.PropertyHelper;
import com.rssl.phizic.utils.test.JUnitDatabaseConfig;
import com.rssl.phizic.utils.test.JUnitConfig;

import java.util.Properties;

/**
 * @author Gololobov
 * @ created 16.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class XBankJUnitDatabaseConfig implements JUnitDatabaseConfig
{
	private final Properties configReader;

	XBankJUnitDatabaseConfig()
	{
		configReader = PropertyHelper.readProperties("xBank.properties");;
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
