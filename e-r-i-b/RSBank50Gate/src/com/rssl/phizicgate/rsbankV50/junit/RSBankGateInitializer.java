package com.rssl.phizicgate.rsbankV50.junit;

import com.rssl.phizic.utils.test.JUnitConfigurator;
import com.rssl.phizic.dataaccess.HibernateService;

/**
 * @author Roshka
 * @ created 01.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class RSBankGateInitializer implements JUnitConfigurator
{
	private void initHibernate()
	{
		HibernateService hibernateService = new HibernateService();

		hibernateService.setJndiName("hibernate/session-factory/RSBankV50");
		hibernateService.setHibernateCfg("rsbankV50.hibernate.cfg.xml");
		hibernateService.setDialect("org.hibernate.dialect.SQLServerDialect");

		hibernateService.start();
	}

	public void configure()
	{
		initHibernate();
	}
}