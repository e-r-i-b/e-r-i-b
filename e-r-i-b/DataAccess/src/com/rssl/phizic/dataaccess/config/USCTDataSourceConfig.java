package com.rssl.phizic.dataaccess.config;

/**
 * @author khudyakov
 * @ created 06.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class USCTDataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY                     = "com.rssl.iccs.usct.datasource";
	private static final String HIBERNATE_DIALECT_KEY               = "com.rssl.iccs.usct.dialect";
	private static final String DRIVER_KEY                          = "com.rssl.iccs.usct.jdbc.driver";
	private static final String URI_KEY                             = "com.rssl.iccs.usct.jdbc.uri";
	private static final String LOGIN_KEY                           = "com.rssl.iccs.usct.jdbc.login";
	private static final String PASSWORD_KEY                        = "com.rssl.iccs.usct.jdbc.password";


	public USCTDataSourceConfig()
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
