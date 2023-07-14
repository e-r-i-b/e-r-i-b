package com.rssl.phizic.utils.test;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.naming.NamingException;
/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 02.09.2005
 * Time: 15:22:41
 * */
public abstract class RSSLTestCaseBase extends TestCase
{
	protected static Log log = LogFactory.getLog("TEST");

	private static boolean initialized = false;
	private static boolean gateInitialized = false;


	public RSSLTestCaseBase(String string)
	{
		super(string);
	}

	public RSSLTestCaseBase()
	{
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		initialize();
	}

	public void runBare() throws Throwable
	{
		String key = getClass().getName() + "#" + getName();
		System.out.println("SETUP " + key);
		try
		{
			super.runBare();
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
			throw e;
		}
		finally
		{
			System.out.println("TEARDOWN " + key);
		}
	}

	private void initialize()
	{
		if (initialized)
			return;

		initializeEnvironment();

		initialized = true;
	}

	/**
	 * Инициализация переданными данными
	 * @param config
	 */
	public static void initializeEnvironment(JUnitDatabaseConfig config)
	{
		initializeDataSource(config);
		initConfigs();
	}

	/**
	 * инициализация предустановленными
	 */
	public static void initializeEnvironment()
	{
		initializeDataSource();
		initConfigs();
	}

	private static void initConfigs()
	{
		JUnitConfigurator configurator = null;
		try
		{
			configurator = (JUnitConfigurator) Class.forName("com.rssl.phizic.business.JUnitConfiguratorImpl").newInstance();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if (configurator != null)
			configurator.configure();
		else
			throw new NullPointerException("JUnitConfigurator cannot be null.");
	}


	public static void initializeDataSource(JUnitDatabaseConfig config)
	{
		log.info("Initializing test datasources from config");
			try
			{
				JNDIUnitTestHelper.init(config);
			}
			catch (NamingException ne)
			{
				ne.printStackTrace();
				fail("NamingException thrown on Init : " + ne.getMessage());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				fail("Exception thrown on Init : " + e.getMessage());
			}
	}

	/** Инициализация DataSource */
	public static void initializeDataSource()
	{
		log.info("Initializing test datasources");
		if (JNDIUnitTestHelper.notInitialized())
		{
			try
			{
				JNDIUnitTestHelper.init();
			}
			catch (NamingException ne)
			{
				ne.printStackTrace();
				fail("NamingException thrown on Init : " + ne.getMessage());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				fail("Exception thrown on Init : " + e.getMessage());
			}
		}
	}

	/** Инициализировать шлюз к RS-Retaill */
	public void initializeRsV51Gate()
	{
//		if (gateInitialized)
//			return;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		JUnitConfigurator rsV51GateInitializer = null;
		try
		{
			rsV51GateInitializer = (JUnitConfigurator) classLoader.loadClass("com.rssl.phizicgate.junit.RsV51GateInitializer").newInstance();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		rsV51GateInitializer.configure();

		gateInitialized = true;
	}

	/** Инициализировать шлюз к RS-Bank */
	public void initializeRSBankV50Gate()
	{
//		if (gateInitialized)
//			return;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		JUnitConfigurator bankV50GateInitializer = null;
		try
		{
			bankV50GateInitializer = (JUnitConfigurator) classLoader.loadClass("com.rssl.phizicgate.rsbankV50.junit.RSBankGateInitializer").newInstance();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		bankV50GateInitializer.configure();

		gateInitialized = true;
	}

	public void initializeRSGate()
	{
        initializeRSBankV50Gate();
		initializeRsV51Gate();
	}
}
