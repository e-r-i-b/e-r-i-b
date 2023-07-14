package com.rssl.phizic.dataaccess.config;

import com.rssl.phizic.utils.PropertyHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

/**
 * @author krenev
 * @ created 14.03.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class DataSourceConfigBase implements DataSourceConfig
{
	private static final Log log = LogFactory.getLog(DataSourceConfigBase.class);
	private final String configSuffix;
	private final Properties configReader;
	private final String dataSourceKey;
	private final String dialect;

	public DataSourceConfigBase(String dataSourceKey, String dialect)
	{
		this.dataSourceKey = dataSourceKey;
		this.dialect = dialect;
		configReader = PropertyHelper.readProperties("system.properties");
		String temp = System.getProperty(dataSourceKey);
		configSuffix = (temp == null) || (temp.length() == 0) ? "" : "." + temp;
		log.info(dataSourceKey + ":  " + configSuffix);
	}

	public String getDataSourceName()
	{
		return getProperty(dataSourceKey);
	}

	public String getHibernateDialect()
	{
		return getProperty(dialect);
	}

	protected String getProperty(String key)
	{
		return configReader.getProperty(key + configSuffix);
	}
}
