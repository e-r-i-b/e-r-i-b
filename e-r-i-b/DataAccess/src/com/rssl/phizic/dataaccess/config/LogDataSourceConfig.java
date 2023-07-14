package com.rssl.phizic.dataaccess.config;

/**
 * @author krenev
 * @ created 14.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class LogDataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY = "com.rssl.iccs.log.datasource";
	private static final String HIBERNATE_DIALECT_KEY = "com.rssl.iccs.log.dialect";

	public LogDataSourceConfig()
	{
		super(DATA_SOURCE_KEY, HIBERNATE_DIALECT_KEY);
	}
}
