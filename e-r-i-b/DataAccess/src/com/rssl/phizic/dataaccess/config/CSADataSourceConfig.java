package com.rssl.phizic.dataaccess.config;

/**
 * @author vagin
 * @ created 24.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class CSADataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY = "com.rssl.iccs.module.csa.datasource";
	private static final String HIBERNATE_DIALECT_KEY = "com.rssl.iccs.module.csa.dialect";
	private static final String DRIVER_KEY = "com.rssl.iccs.module.csa.jdbc.driver";
	private static final String URI_KEY = "com.rssl.iccs.module.csa.jdbc.uri";
	private static final String LOGIN_KEY = "com.rssl.iccs.module.csa.jdbc.login";
	private static final String PASSWORD_KEY = "com.rssl.iccs.module.csa.jdbc.password";

	public CSADataSourceConfig()
	{
		super(DATA_SOURCE_KEY, HIBERNATE_DIALECT_KEY);
	}

	public String getDriver()
	{
		return getProperty(DRIVER_KEY);
	}

	public String getURI()
	{
		return getProperty(URI_KEY);
	}

	public String getLogin()
	{
		return getProperty(LOGIN_KEY);
	}

	public String getPassword()
	{
		return getProperty(PASSWORD_KEY);
	}
}
