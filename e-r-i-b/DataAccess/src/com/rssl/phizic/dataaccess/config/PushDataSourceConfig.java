package com.rssl.phizic.dataaccess.config;

/**
 * Датасорсконфиг для работы с базой данных для push-уведомлений
 * @author basharin
 * @ created 06.08.13
 * @ $Author$
 * @ $Revision$
 */

public class PushDataSourceConfig  extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY = "com.rssl.iccs.module.push.datasource";
	private static final String HIBERNATE_DIALECT_KEY = "com.rssl.iccs.module.push.dialect";
	private static final String DRIVER_KEY = "com.rssl.iccs.module.push.jdbc.driver";
	private static final String URI_KEY = "com.rssl.iccs.module.push.jdbc.uri";
	private static final String LOGIN_KEY = "com.rssl.iccs.module.push.jdbc.login";
	private static final String PASSWORD_KEY = "com.rssl.iccs.module.push.jdbc.password";

	public PushDataSourceConfig()
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