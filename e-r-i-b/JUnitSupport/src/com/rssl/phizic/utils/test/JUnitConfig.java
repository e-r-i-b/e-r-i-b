package com.rssl.phizic.utils.test;

import com.rssl.phizic.dataaccess.config.SimpleDataSourceConfig;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 19.09.2005 Time: 13:43:50 */
public class JUnitConfig extends SimpleDataSourceConfig implements JUnitDatabaseConfig
{
    public final static String DRIVER_KEY   = "com.rssl.iccs.jdbc.driver";
    public final static String URI_KEY      = "com.rssl.iccs.jdbc.uri";
    public final static String LOGIN_KEY    = "com.rssl.iccs.jdbc.login";
    public final static String PASSWORD_KEY = "com.rssl.iccs.jdbc.password";
	
	public final static String SHADOW_JNDI_NAME = "com.rssl.iccs.shadow.datasource";
	public final static String SHADOW_DIALECT = "com.rssl.iccs.shadow.dialect";
	public final static String SHADOW_DRIVER = "com.rssl.iccs.shadow.jdbc.driver";
	public final static String SHADOW_URI = "com.rssl.iccs.shadow.jdbc.uri";
	public final static String SHADOW_LOGIN = "com.rssl.iccs.shadow.jdbc.login";
	public final static String SHADOW_PASSWORD = "com.rssl.iccs.shadow.jdbc.password";

	public final static String V6R4_JNDI_NAME = "com.rssl.iccs.v6r4.datasource";
	public final static String V6R4_DIALECT = "com.rssl.iccs.v6r4.dialect";
	public final static String V6R4_DRIVER = "com.rssl.iccs.v6r4.jdbc.driver";
	public final static String V6R4_URI = "com.rssl.iccs.v6r4.jdbc.uri";
	public final static String V6R4_LOGIN = "com.rssl.iccs.v6r4.jdbc.login";
	public final static String V6R4_PASSWORD = "com.rssl.iccs.v6r4.jdbc.password";

	public final static String V6R20_JNDI_NAME = "com.rssl.iccs.v6r20.datasource";
	public final static String V6R20_DIALECT = "com.rssl.iccs.v6r20.dialect";
	public final static String V6R20_DRIVER = "com.rssl.iccs.v6r20.jdbc.driver";
	public final static String V6R20_URI = "com.rssl.iccs.v6r20.jdbc.uri";
	public final static String V6R20_LOGIN = "com.rssl.iccs.v6r20.jdbc.login";
	public final static String V6R20_PASSWORD = "com.rssl.iccs.v6r20.jdbc.password";

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

	public String getShadowJNDI()
	{
		return getProperty(SHADOW_JNDI_NAME);
	}

	public String getShadowDialect()
	{
		return getProperty(SHADOW_DIALECT);
	}

	public String getShadowDriver()
	{
		return getProperty(SHADOW_DRIVER);
	}

	public String getShadowURI()
	{
		return getProperty(SHADOW_URI);
	}

	public String getShadowLogin()
	{
		return getProperty(SHADOW_LOGIN);
	}

	public String getShadowPassword()
	{
		return getProperty(SHADOW_PASSWORD);
	}

	public String getV6r4JNDI()
	{
		return getProperty(V6R4_JNDI_NAME);
	}

	public String getV6r4Dialect()
	{
		return getProperty(V6R4_DIALECT);
	}

	public String getV6r4Driver()
	{
		return getProperty(V6R4_DRIVER);
	}

	public String getV6r4URI()
	{
		return getProperty(V6R4_URI);
	}

	public String getV6r4Login()
	{
		return getProperty(V6R4_LOGIN);
	}

	public String getV6r4Password()
	{
		return getProperty(V6R4_PASSWORD);
	}

	public String getV6r20JNDI()
	{
		return getProperty(V6R20_JNDI_NAME);
	}

	public String getV6r20Dialect()
	{
		return getProperty(V6R20_DIALECT);
	}

	public String getV6r20Driver()
	{
		return getProperty(V6R20_DRIVER);
	}

	public String getV6r20URI()
	{
		return getProperty(V6R20_URI);
	}

	public String getV6r20Login()
	{
		return getProperty(V6R20_LOGIN);
	}

	public String getV6r20Password()
	{
		return getProperty(V6R20_PASSWORD);
	}
}
