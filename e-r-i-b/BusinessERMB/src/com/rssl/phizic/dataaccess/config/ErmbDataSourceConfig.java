package com.rssl.phizic.dataaccess.config;

import com.rssl.phizic.utils.test.JUnitDatabaseConfig;

/**
 * @author Erkin
 * @ created 09.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class ErmbDataSourceConfig extends DataSourceConfigBase implements JUnitDatabaseConfig
{
	private static final String DATA_SOURCE_KEY = "com.rssl.iccs.ermb.sms.datasource";

	private static final String HIBERNATE_DIALECT_KEY = "com.rssl.iccs.ermb.sms.dialect";

	private final static String DRIVER_KEY   = "com.rssl.iccs.ermb.sms.jdbc.driver";

	private final static String URI_KEY      = "com.rssl.iccs.ermb.sms.jdbc.uri";

	private final static String LOGIN_KEY    = "com.rssl.iccs.ermb.sms.jdbc.login";

	private final static String PASSWORD_KEY = "com.rssl.iccs.ermb.sms.jdbc.password";

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 */
	public ErmbDataSourceConfig()
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
