package com.rssl.phizic.dataaccess.config;

/**
 * @author khudyakov
 * @ created 22.07.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringBackGateLogDataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY                     = "com.rssl.iccs.fraud-monitoring-gate.log.datasource";
	private static final String HIBERNATE_DIALECT_KEY               = "com.rssl.iccs.fraud-monitoring-gate.log.dialect";
	private static final String DRIVER_KEY                          = "com.rssl.iccs.fraud-monitoring-gate.log.jdbc.driver";
	private static final String URI_KEY                             = "com.rssl.iccs.fraud-monitoring-gate.log.jdbc.uri";
	private static final String LOGIN_KEY                           = "com.rssl.iccs.fraud-monitoring-gate.log.jdbc.login";
	private static final String PASSWORD_KEY                        = "com.rssl.iccs.fraud-monitoring-gate.log.jdbc.password";


	public FraudMonitoringBackGateLogDataSourceConfig()
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
