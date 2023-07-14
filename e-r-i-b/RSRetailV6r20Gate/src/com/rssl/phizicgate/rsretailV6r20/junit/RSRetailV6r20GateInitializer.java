package com.rssl.phizicgate.rsretailV6r20.junit;

import com.rssl.phizic.dataaccess.HibernateService;
import com.rssl.phizic.utils.test.JUnitConfigurator;

/**
 * @author Omeliyanchuk
 * @ created 20.09.2007
 * @ $Author$
 * @ $Revision$
 */

public class RSRetailV6r20GateInitializer implements JUnitConfigurator
{
	private void initHibernate()
	{
		HibernateService hibernateService = new HibernateService();

		hibernateService.setJndiName("hibernate/session-factory/RSRetailV6r20");
		hibernateService.setHibernateCfg("rsRetailV6r20.hibernate.cfg.xml");
		hibernateService.setDialect("org.hibernate.dialect.Oracle9Dialect");
		hibernateService.setShowSqlEnabled("true");
		hibernateService.start();
	}

	public void configure()
	{
		initHibernate();
	}
}
