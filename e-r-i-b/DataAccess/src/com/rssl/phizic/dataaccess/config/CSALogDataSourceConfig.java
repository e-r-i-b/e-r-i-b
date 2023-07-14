package com.rssl.phizic.dataaccess.config;

/**
 * @author vagin
 * @ created 16.10.2012
 * @ $Author$
 * @ $Revision$
 * Конфиг датосорца для БД логов ЦСА
 */
public class CSALogDataSourceConfig extends DataSourceConfigBase
{
	private static final String CSA_DATA_SOURCE_KEY = "com.rssl.iccs.log.csa.datasource";
	private static final String CSA_HIBERNATE_DIALECT_KEY = "com.rssl.iccs.log.csa.dialect";

	public CSALogDataSourceConfig()
	{
		super(CSA_DATA_SOURCE_KEY, CSA_HIBERNATE_DIALECT_KEY);
	}
}
