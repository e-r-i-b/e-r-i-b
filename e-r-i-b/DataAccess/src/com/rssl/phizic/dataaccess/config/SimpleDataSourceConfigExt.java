package com.rssl.phizic.dataaccess.config;

/**
 * @author Omeliyanchuk
 * @ created 07.07.2008
 * @ $Author$
 * @ $Revision$
 */

public class SimpleDataSourceConfigExt extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY = "com.rssl.iccs.shadow.datasource";
	private static final String HIBERNATE_DIALECT_KEY = "com.rssl.iccs.shadow.dialect";

	public SimpleDataSourceConfigExt()
	{
		super(DATA_SOURCE_KEY, HIBERNATE_DIALECT_KEY);
	}
}
