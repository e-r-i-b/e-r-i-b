package com.rssl.phizic.dataaccess.config;

/**
 * Датасорсконфиг для доступа к БД оффлайн документов
 * @author gladishev
 * @ created 08.10.13
 * @ $Author$
 * @ $Revision$
 */
public class OfflineDocDataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY = "com.rssl.iccs.documents.offline.datasource";
	private static final String HIBERNATE_DIALECT_KEY = "com.rssl.iccs.documents.offline.dialect";
	private static final String DRIVER_KEY = "com.rssl.iccs.documents.offline.jdbc.driver";
	private static final String URI_KEY = "com.rssl.iccs.documents.offline.jdbc.uri";
	private static final String LOGIN_KEY = "com.rssl.iccs.documents.offline.jdbc.login";
	private static final String PASSWORD_KEY = "com.rssl.iccs.documents.offline.jdbc.password";

	public OfflineDocDataSourceConfig()
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
