package com.rssl.phizic.dataaccess.config;

/**
 * @author Mescheryakova
 * @ created 29.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class ReportDataSourceConfig extends DataSourceConfigBase
{
	private static final String DATA_SOURCE_KEY = "com.rssl.iccs.report.datasource";
	private static final String HIBERNATE_DIALECT_KEY = "com.rssl.iccs.report.dialect";

	public ReportDataSourceConfig()
	{
		super(DATA_SOURCE_KEY, HIBERNATE_DIALECT_KEY);
	}
}
