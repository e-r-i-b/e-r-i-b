package com.rssl.phizic.utils.test;

import com.rssl.phizic.config.ConfigurationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public final class JNDIUnitTestHelper
{
    private static boolean initialized;
    private static Set<String> jndiName = new HashSet<String>();

	private static final Log log = LogFactory.getLog(JNDIUnitTestHelper.class);

    private JNDIUnitTestHelper()
    {
    }

	public static void init() throws NamingException, IOException
	{
		JUnitConfig config = new JUnitConfig();
		init(config);
	}
    /**
     * Intializes the pool and sets it in the InitialContext
     */
    @SuppressWarnings({"JNDIResourceOpenedButNotSafelyClosed"})
    public static void init(JUnitDatabaseConfig config) throws NamingException, IOException
    {
		log.info(">>>>>>>> active configuration: ["+
				ConfigurationContext.getIntstance().getActiveConfiguration() +"] <<<<<<<<<");

	    //Если конфиг не для основной БД - всегда создавать датасорс на основную БД иначе падаем при старте хибернета
	    if (!(config instanceof JUnitConfig))
	        createJUnitDataSource(new JUnitConfig());
	    createJUnitDataSource(config);
	    createJUnitDataSource(new JUnitLogDatabaseConfig());

	    // start hibernate
	    HibernateConfigurator configurator = new HibernateConfigurator();
	    configurator.csaConfigure(config.getDataSourceName());
	    configurator.configure();

	    initialized = true;

	    log.info("JNDIUnitTestHelper initialized");
    }

	/**
	 * Создает датасорс по конфигу и биндит в JNDI.
	 * @param config
	 * @throws NamingException
	 */
	public static void createJUnitDataSource(JUnitDatabaseConfig config) throws NamingException
	{
		DataSourceHelper.createJUnitDataSource(config);

		jndiName.add(config.getDataSourceName());
	}

	/**
     * determines if the pool was successfully initialized or not.
     *
     * @return true if the pool was not successfully initialized.
     */
    public static boolean notInitialized()
    {
        return !initialized;
    }

    /**
     * shutdowns down the pool and ends the Thread that DbConnectionBroker starts.
     *
     * @throws NamingException
     */
    @SuppressWarnings({"JNDIResourceOpenedButNotSafelyClosed"})
    public static void shutdown()
            throws NamingException
    {

        InitialContext ctx = new InitialContext();
	    for (String name : jndiName)
	    {
		    ctx.unbind(name);
	    }
        initialized = false;
    }
}
