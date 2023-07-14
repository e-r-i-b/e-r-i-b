package com.rssl.phizicgate.rsretailV6r4.junit;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.HibernateService;
import com.rssl.phizic.utils.test.DataSourceHelper;
import com.rssl.phizic.utils.test.JUnitConfig;
import com.rssl.phizic.utils.test.JUnitConfigurator;
import com.rssl.phizic.utils.test.JUnitSimpleDatabaseConfig;

import javax.naming.NamingException;

/**
 * @author Omeliyanchuk
 * @ created 20.09.2007
 * @ $Author$
 * @ $Revision$
 */

public class RSRetailV6r4GateInitializer implements JUnitConfigurator
{
	private void initHibernate()
	{
		createDataSource();
		
		HibernateService hibernateService = new HibernateService();

		hibernateService.setJndiName("hibernate/session-factory/RSRetailV6r4");
		hibernateService.setHibernateCfg("rsRetailV6r4.hibernate.cfg.xml");
		hibernateService.setDialect("org.hibernate.dialect.Oracle9Dialect");
		hibernateService.setShowSqlEnabled("true");
		hibernateService.start();
	}

	private void createDataSource()
	{
		JUnitConfig dbConfig = new JUnitConfig();

		JUnitSimpleDatabaseConfig config = new JUnitSimpleDatabaseConfig();
		config.setLogin(dbConfig.getV6r4Login());
		config.setPassword(dbConfig.getV6r4Password());
		config.setDataSourceName( ConfigFactory.getReaderByFileName("PhizGateV6/system.properties").getProperty(JUnitConfig.V6R20_JNDI_NAME) );
		config.setURI(dbConfig.getURI());
		config.setDriver(dbConfig.getDriver());

		
		try
		{
			DataSourceHelper.createJUnitDataSource(config);
		}
		catch (NamingException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public void configure()
	{
		initHibernate();
	}
}
