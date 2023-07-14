package com.rssl.phizic.dataaccess.config;

/**
 * @author khudyakov
 * @ created 03.07.14
 * @ $Author$
 * @ $Revision$
 */
public class USCTLogDataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY                     = "com.rssl.iccs.usct.log.datasource";
	private static final String HIBERNATE_DIALECT_KEY               = "com.rssl.iccs.usct.log.dialect";
	private static final String DRIVER_KEY                          = "com.rssl.iccs.usct.log.jdbc.driver";
	private static final String URI_KEY                             = "com.rssl.iccs.usct.log.jdbc.uri";
	private static final String LOGIN_KEY                           = "com.rssl.iccs.usct.log.jdbc.login";
	private static final String PASSWORD_KEY                        = "com.rssl.iccs.usct.log.jdbc.password";


	public USCTLogDataSourceConfig()
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