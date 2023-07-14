package com.rssl.phizic.dataaccess.config;

/**
 * @author niculichev
 * @ created 28.01.14
 * @ $Author$
 * @ $Revision$
 */
public class LimitsAppDataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY = "com.rssl.iccs.documents.limits.datasource";
	private static final String HIBERNATE_DIALECT_KEY = "com.rssl.iccs.documents.limits.dialect";

	public LimitsAppDataSourceConfig()
	{
		super(DATA_SOURCE_KEY, HIBERNATE_DIALECT_KEY);
	}
}
