package com.rssl.phizic.dataaccess;

/**
 * @author Evgrafov
 * @ created 27.06.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1545 $
 */

public interface HibernateServiceMBean extends org.hibernate.jmx.HibernateServiceMBean
{
	String getHibernateCfg();
	void setHibernateCfg(String hibernateCfg);

	String getConfigResources();
	void setConfigResources(String configs);

	boolean getStarted();
	void setStarted(boolean value);

	String getDatasourceConfigClass();

	void setDatasourceConfigClass(String datasourceConfigClass);
}
