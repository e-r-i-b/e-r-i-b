package com.rssl.phizic.dataaccess.config;

/**
 * @author Gulov
 * @ created 16.12.13
 * @ $Author$
 * @ $Revision$
 */
public class MigrationDataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY = "com.rssl.iccs.migration.datasource";
	private static final String HIBERNATE_DIALECT_KEY = "com.rssl.iccs.migration.dialect";

	public MigrationDataSourceConfig()
	{
		super(DATA_SOURCE_KEY, HIBERNATE_DIALECT_KEY);
	}
}
