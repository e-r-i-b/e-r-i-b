package com.rssl.phizic.dataaccess.config;

/**
 * @author khudyakov
 * @ created 04.06.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringBackGateDataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY                     = "com.rssl.iccs.fraud-monitoring-gate.datasource";
	private static final String HIBERNATE_DIALECT_KEY               = "com.rssl.iccs.fraud-monitoring-gate.dialect";
	private static final String DRIVER_KEY                          = "com.rssl.iccs.fraud-monitoring-gate.jdbc.driver";
	private static final String URI_KEY                             = "com.rssl.iccs.fraud-monitoring-gate.jdbc.uri";
	private static final String LOGIN_KEY                           = "com.rssl.iccs.fraud-monitoring-gate.jdbc.login";
	private static final String PASSWORD_KEY                        = "com.rssl.iccs.fraud-monitoring-gate.jdbc.password";


	public FraudMonitoringBackGateDataSourceConfig()
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
