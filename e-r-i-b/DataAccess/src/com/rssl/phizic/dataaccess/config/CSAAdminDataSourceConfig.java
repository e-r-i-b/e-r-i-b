package com.rssl.phizic.dataaccess.config;

/**
 * @author akrenev
 * @ created 23.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Конфиг датасорса CSAAdmin
 */

public class CSAAdminDataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY         = "com.rssl.iccs.module.csaadmin.datasource";
	private static final String HIBERNATE_DIALECT_KEY   = "com.rssl.iccs.module.csaadmin.dialect";
	private static final String DRIVER_KEY              = "com.rssl.iccs.module.csaadmin.jdbc.driver";
	private static final String URI_KEY                 = "com.rssl.iccs.module.csaadmin.jdbc.uri";
	private static final String LOGIN_KEY               = "com.rssl.iccs.module.csaadmin.jdbc.login";
	private static final String PASSWORD_KEY            = "com.rssl.iccs.module.csaadmin.jdbc.password";

	public CSAAdminDataSourceConfig()
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