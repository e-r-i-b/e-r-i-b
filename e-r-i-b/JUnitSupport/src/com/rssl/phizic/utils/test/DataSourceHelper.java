package com.rssl.phizic.utils.test;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Omeliyanchuk
 * @ created 14.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class DataSourceHelper
{
	/**
	 * Создает датасорс по конфигу и биндит в JNDI.
	 * @param config
	 * @throws javax.naming.NamingException
	 */
	public static void createJUnitDataSource(JUnitDatabaseConfig config) throws NamingException
	{
		JUnitDataSource source = new JUnitDataSource();

		source.setDbDriver(config.getDriver());
		source.setDbServer(config.getURI());
		source.setDbLogin(config.getLogin());
		source.setDbPassword(config.getPassword());
		String jndiName = config.getDataSourceName();

		//Set up environment for creating initial context
		Hashtable env = new Hashtable();

		env.put(Context.INITIAL_CONTEXT_FACTORY, JUnitContextFactory.class.getName());
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, JUnitContextFactory.class.getName());

		Context ctx = new InitialContext();

		// Register the data source to JNDI naming service
		ctx.bind(jndiName, source);
	}
}
