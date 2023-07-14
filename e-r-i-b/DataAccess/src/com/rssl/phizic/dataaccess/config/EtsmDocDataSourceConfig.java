package com.rssl.phizic.dataaccess.config;

/**
 * Датасорсконфиг для подключения к БД документов ETSM
 * @author Moshenko
 * @ created 22.06.15
 * @ $Author$
 * @ $Revision$
 */
public class EtsmDocDataSourceConfig extends DataSourceConfigBase
{

	private static final String DATA_SOURCE_KEY = "com.rssl.iccs.documents.etsm.datasource";
	private static final String HIBERNATE_DIALECT_KEY = "com.rssl.iccs.documents.etsm.dialect";
	private static final String DRIVER_KEY = "com.rssl.iccs.documents.etsm.jdbc.driver";
	private static final String URI_KEY = "com.rssl.iccs.documents.etsm.jdbc.uri";
	private static final String LOGIN_KEY = "com.rssl.iccs.documents.etsm.jdbc.login";
	private static final String PASSWORD_KEY = "com.rssl.iccs.documents.etsm.jdbc.password";

	public EtsmDocDataSourceConfig()
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
