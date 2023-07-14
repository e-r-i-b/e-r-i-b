package com.rssl.phizic.dataaccess.config;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 16.09.2005
 * Time: 13:16:34
 */
public class SimpleDataSourceConfig extends DataSourceConfigBase
{
	//*********************************************************************//
	//***************************  CLASS MEMBERS  *************************//
	//*********************************************************************//

	private static final String DATA_SOURCE_KEY = "com.rssl.iccs.datasource";
	private static final String HIBERNATE_DIALECT_KEY = "com.rssl.iccs.dialect";

	public SimpleDataSourceConfig()
	{
		super(DATA_SOURCE_KEY, HIBERNATE_DIALECT_KEY);
	}
}
