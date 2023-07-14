package ru.softlab.phizicgate.rsloansV64.junit;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.HibernateService;
import com.rssl.phizic.utils.test.JUnitConfigurator;
import oracle.jdbc.pool.OracleDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * @author Omeliyanchuk
 * @ created 07.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class RSLoans64GateInitializer implements JUnitConfigurator
{
	private void initLoansConnection()
	{
		RSLoans64Config config = ConfigFactory.getConfig(RSLoans64Config.class);

		try
		{
			OracleDataSource source = new OracleDataSource();
			source.setPassword( config.getPassword() );
			source.setUser( config.getLogin() );
			source.setURL("jdbc:oracle:thin:@" + config.getServerName() + ":" + config.getServerPort() + ":" + config.getDb());

			String jndiName = config.getDatasorceName();

			Context ctx = new InitialContext();

			// Register the data source to JNDI naming service
			ctx.bind(jndiName, source);

		}
		catch(Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public void configure()
	{
		initLoansConnection();
		initHibernate();
	}

	private void initHibernate()
	{
		HibernateService hibernateService = new HibernateService();
		
		hibernateService.setJndiName("hibernate/session-factory/RSLoansV64");
		hibernateService.setHibernateCfg("rsLoansV64.hibernate.cfg.xml");
		hibernateService.start();
	}
}