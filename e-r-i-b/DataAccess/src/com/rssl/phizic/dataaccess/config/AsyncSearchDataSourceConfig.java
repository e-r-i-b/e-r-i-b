package com.rssl.phizic.dataaccess.config;

/**
 * @ author: Gololobov
 * @ created: 26.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsyncSearchDataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY = "com.rssl.iccs.asyncsearch.datasource";
	private static final String HIBERNATE_DIALECT_KEY = "com.rssl.iccs.asyncsearch.dialect";
	private static final String PASSWORD_KEY = "com.rssl.iccs.asyncsearch.jdbc.password";
	private static final String LOGIN_KEY = "com.rssl.iccs.asyncsearch.jdbc.login";
	private static final String URI_KEY = "com.rssl.iccs.asyncsearch.jdbc.uri";
	private static final String DRIVER_KEY = "com.rssl.iccs.asyncsearch.jdbc.driver";

	public AsyncSearchDataSourceConfig()
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
