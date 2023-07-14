package com.rssl.phizicgate.junit;

import com.rssl.phizic.dataaccess.HibernateService;
import com.rssl.phizic.utils.test.JUnitConfigurator;

/**
 * @author Evgrafov
 * @ created 03.07.2006
 * @ $Author: Roshka $
 * @ $Revision: 2885 $
 */

public class RsV51GateInitializer implements JUnitConfigurator
{
	private void initHibernate()
	{
		HibernateService hibernateService = new HibernateService();

		hibernateService.setJndiName("hibernate/session-factory/RSRetailV51");
		hibernateService.setHibernateCfg("rsV51.hibernate.cfg.xml");
		hibernateService.setDialect("org.hibernate.dialect.SQLServerDialect");

		hibernateService.start();
	}

	public void configure()
	{
		initHibernate();
	}
}