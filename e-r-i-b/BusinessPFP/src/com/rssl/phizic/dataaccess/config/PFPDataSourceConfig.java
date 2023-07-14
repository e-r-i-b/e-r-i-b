package com.rssl.phizic.dataaccess.config;

/**
 * @author Erkin
 * @ created 18.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class PFPDataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY         = "com.rssl.iccs.module.pfp.datasource";
	private static final String HIBERNATE_DIALECT_KEY   = "com.rssl.iccs.module.pfp.dialect";

	/**
	 * ctor
	 */
	public PFPDataSourceConfig()
	{
		super(DATA_SOURCE_KEY, HIBERNATE_DIALECT_KEY);
	}
}
