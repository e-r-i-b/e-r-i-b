package com.rssl.phizic.utils.test;

import com.rssl.phizic.dataaccess.config.LogDataSourceConfig;

/**
 * @author krenev
 * @ created 16.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class JUnitLogDatabaseConfig extends LogDataSourceConfig implements JUnitDatabaseConfig
{
	public final static String DRIVER_KEY   = "com.rssl.iccs.log.jdbc.driver";
	public final static String URI_KEY      = "com.rssl.iccs.log.jdbc.uri";
	public final static String LOGIN_KEY    = "com.rssl.iccs.log.jdbc.login";
	public final static String PASSWORD_KEY = "com.rssl.iccs.log.jdbc.password";

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
