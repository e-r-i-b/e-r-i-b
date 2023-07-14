package com.rssl.phizic.gate.impl;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author bogdanov
 * @ created 23.01.14
 * @ $Author$
 * @ $Revision$
 */

public class DataSourceConfig extends Config
{
	private String csaDataSource;
	private String dataSourceName;

	public DataSourceConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		csaDataSource = getProperty("com.rssl.iccs.module.csa.datasource");
		dataSourceName = getProperty("datasource.name");
	}

	public String getCsaDataSource()
	{
		return csaDataSource;
	}

	public String getDataSourceName()
	{
		return dataSourceName;
	}
}
