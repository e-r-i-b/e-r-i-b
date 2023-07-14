package com.rssl.phizic.dataaccess.config;

/**
 * @author Moshenko
 * @ created 03.07.14
 * @ $Author$
 * @ $Revision$
 */
public class ProxyMbkDataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY = "com.rssl.iccs.proxy.mbk.datasource";
	private static final String HIBERNATE_DIALECT_KEY = "com.rssl.iccs.proxy.mbk.dialect";

	public ProxyMbkDataSourceConfig()
	{
		super(DATA_SOURCE_KEY, HIBERNATE_DIALECT_KEY);
	}
}
