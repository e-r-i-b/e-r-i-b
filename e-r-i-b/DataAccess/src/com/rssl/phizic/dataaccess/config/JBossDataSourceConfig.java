package com.rssl.phizic.dataaccess.config;

/**
 * @author Evgrafov
 * @ created 11.07.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1633 $
 */

public class JBossDataSourceConfig extends SimpleDataSourceConfig
{
	public String getDataSourceName()
	{
		return "java:" + super.getDataSourceName();
	}
}