package com.rssl.phizic.business.ant;

import com.rssl.phizic.dataaccess.config.CSADataSourceConfig;
import com.rssl.phizic.utils.test.JUnitDatabaseConfig;

/**
 *
 * Класс используется для получения параметров соединения со схемой ЦСА
 *
 * @author  Balovtsev
 * @version 04.04.13 15:08
 */
class CSAJUnitDatabaseConfig implements JUnitDatabaseConfig
{
	private final CSADataSourceConfig csaDataSourceConfig;

	CSAJUnitDatabaseConfig(CSADataSourceConfig csaDataSourceConfig)
	{
		this.csaDataSourceConfig = csaDataSourceConfig;
	}

	public String getDriver()
	{
		return csaDataSourceConfig.getDriver();
	}

	public String getURI()
	{
		return csaDataSourceConfig.getURI();
	}

	public String getLogin()
	{
		return csaDataSourceConfig.getLogin();
	}

	public String getPassword()
	{
		return csaDataSourceConfig.getPassword();
	}

	public String getDataSourceName()
	{
		return csaDataSourceConfig.getDataSourceName();
	}
}
