package com.rssl.phizgate.common.messaging.retail.jni;

import com.rssl.phizgate.common.messaging.retail.jni.jndi.RetailJNIHelper;
import com.rssl.phizgate.common.messaging.retail.jni.pool.RetailJniFactory;
import com.rssl.phizgate.common.messaging.retail.jni.pool.RetailJniPool;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.startup.StartupService;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.management.MBeanException;
import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author Omeliyanchuk
 * @ created 25.11.2009
 * @ $Author$
 * @ $Revision$
 */

public class RetailJNIStartServiceImpl implements RetailJNIStartServiceImplMBean, MBeanRegistration, StartupService
{
	private static final Log log = LogFactory.getLog(Constants.LOG_MODULE_GATE.toValue());
	//настройки ретейл jni
	private Boolean isDebug;
	private Boolean isRemote;
	//настройки пула
	private String maxActive = null;
	private String maxIdle = null;
	private String maxTotal = null;
	private String maxWait = null;
    private String minEvictableIdleTimeMillis = null;
    private String minPoolSize = null;
	private String timeBetweenEvictionRunsMillis = null;

	//Нужно для запуска бина, должно быть последним
	private Boolean started;

	public RetailJNIStartServiceImpl() throws MBeanException
	{
		RetailJNIStartServiceImpl.log.info("RetailJNIService instance created");
	}

	public void start() throws GateException
	{
		RetailJNIStartServiceImpl.log.debug("RetailJNIService starting");
		loadSettings( ConfigFactory.getConfig(GateConfig.class) );

		if(isRemote == null)
			throw new GateException("Для RetailJNIService не установлен обязательный параметр isRemote");

		RetailJniFactory factory = new RetailJniFactory();

		factory.init(isRemote, isDebug);

		RetailJniPool poolFactory = new RetailJniPool(factory);
		if(!StringHelper.isEmpty(maxActive))
			poolFactory.setMaxActive(Integer.valueOf(maxActive));
		if(!StringHelper.isEmpty(maxIdle))
			poolFactory.setMaxIdle(Integer.valueOf(maxIdle));
		if(!StringHelper.isEmpty(maxTotal))
			poolFactory.setMaxTotal(Integer.valueOf(maxTotal));
		if(!StringHelper.isEmpty(maxWait))
			poolFactory.setMaxWait(Integer.valueOf(maxWait));
		if(!StringHelper.isEmpty(minEvictableIdleTimeMillis))
			poolFactory.setMinEvictableIdleTimeMillis(Integer.valueOf(minEvictableIdleTimeMillis));
		if(!StringHelper.isEmpty(minPoolSize))
			poolFactory.setMinIdle(Integer.valueOf(minPoolSize));
		if(!StringHelper.isEmpty(timeBetweenEvictionRunsMillis))
			poolFactory.setTimeBetweenEvictionRunsMillis(Integer.valueOf(timeBetweenEvictionRunsMillis));

		RetailJNIHelper.putPoolFactory(poolFactory);

		RetailJNIStartServiceImpl.log.debug("RetailJNIService started");
	}

	private void loadSettings(GateConfig reader)
	{
//		String value = reader.getProperty("com.rssl.phizic.gate.rs-retail-v64.remote.host");
//		if (!StringHelper.isEmpty( value ))
//		{
//			host = value;
//		}
//
//		value = reader.getProperty("com.rssl.phizic.gate.rs-retail-v64.remote.port");
//		if (!StringHelper.isEmpty(value))
//		{
//			port = value;
//		}
//
//		value = reader.getProperty("com.rssl.phizic.gate.rs-retail-v64.version");
//		if (!StringHelper.isEmpty(value))
//		{
//			version = value;
//		}

//		value = reader.getProperty("com.rssl.phizic.gate.rs-retail-v64.location");
//		if (!StringHelper.isEmpty(value))
//		{
//			location = value;
//		}

		String value = reader.getProperty("com.rssl.phizic.gate.rs-retail-v64.debug");
		if (!StringHelper.isEmpty(value))
		{
			isDebug = Boolean.valueOf(value);
		}

		value = reader.getProperty("com.rssl.phizic.gate.rs-retail-v64.remote.mode.on");
		if (!StringHelper.isEmpty(value))
		{
			isRemote = Boolean.valueOf(value);
		}

		value = reader.getProperty("com.rssl.phizic.gate.rs-retail-v64.maxActive");
		if (!StringHelper.isEmpty(value))
		{
			maxActive = value;
		}

		value = reader.getProperty("com.rssl.phizic.gate.rs-retail-v64.maxIdle");
		if (!StringHelper.isEmpty(value))
		{
			maxIdle = value;
		}

		value = reader.getProperty("com.rssl.phizic.gate.rs-retail-v64.maxTotal");
		if (!StringHelper.isEmpty(value))
		{
			maxTotal = value;
		}

		value = reader.getProperty("com.rssl.phizic.gate.rs-retail-v64.maxWait");
		if (!StringHelper.isEmpty(value))
		{
			maxWait = value;
		}

		value = reader.getProperty("com.rssl.phizic.gate.rs-retail-v64.minPoolSize");
		if (!StringHelper.isEmpty(value))
		{
			minPoolSize = value;
		}

		value = reader.getProperty("com.rssl.phizic.gate.rs-retail-v64.minEvictableIdleTimeMillis");
		if (!StringHelper.isEmpty(value))
		{
			minEvictableIdleTimeMillis = value;
		}

		value = reader.getProperty("com.rssl.phizic.gate.rs-retail-v64.timeBetweenEvictionRunsMillis");
		if (!StringHelper.isEmpty(value))
		{
			timeBetweenEvictionRunsMillis = value;
		}
	}

	/**
	 * stop
	 */
	public void stop()
	{
		RetailJNIStartServiceImpl.log.info("Stopping RetailJNIService service");
		try
		{
			RetailJniPool factory = new RetailJNIHelper().lookupPoolFactory();
			if( factory != null )
				factory.close();
		}
		catch (Exception e)
		{
			RetailJNIStartServiceImpl.log.error("Exception while stopping RetailJNIService. ", e);
		}
		RetailJNIStartServiceImpl.log.info("RetailJNIService service stoped");
	}

	public void postRegister(Boolean registrationDone)
	{
		//тут инициализировать нельзя, т.к. установка аттрибутов будет позже.
	}

	public boolean isInitialized()
	{
		return RetailJNIHelper.checkIfExist();
	}


	//метода mbean
	public void preDeregister() throws Exception
	{
		//super.preDeregister();
		stop();
	}

	public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception
	{
		return name;
	}

	public void postDeregister()
	{

	}

	public void setMaxActive(String maxActive)
	{
		this.maxActive = maxActive;
	}

	public void setMaxIdle(String maxIdle)
	{
		this.maxIdle = maxIdle;
	}

	public void setMaxTotal(String maxTotal)
	{
		this.maxTotal = maxTotal;
	}

	public void setMaxWait(String maxWait)
	{
		this.maxWait = maxWait;
	}

	public void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis)
	{
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public void setMinPoolSize(String minPoolSize)
	{
		this.minPoolSize = minPoolSize;
	}

	public void setTimeBetweenEvictionRunsMillis(String timeBetweenEvictionRunsMillis)
	{
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public String getMaxActive()
	{
		return maxActive;
	}

	public String getMaxIdle()
	{
		return maxIdle;
	}

	public String getMaxTotal()
	{
		return maxTotal;
	}

	public String getMaxWait()
	{
		return maxWait;
	}

	public String getMinEvictableIdleTimeMillis()
	{
		return minEvictableIdleTimeMillis;
	}

	public String getMinPoolSize()
	{
		return minPoolSize;
	}

	public String getTimeBetweenEvictionRunsMillis()
	{
		return timeBetweenEvictionRunsMillis;
	}

	public boolean getDebug()
	{
		return isDebug;
	}

	public void setDebug(boolean debug)
	{
		isDebug = debug;
	}

	public boolean getRemote()
	{
		return isRemote;
	}

	public void setRemote(boolean remote)
	{
		isRemote = remote;
	}

	public Boolean getStarted()
	{
		return started;
	}

	/**
	 * Свойство для запуска, используется из-за особенностей контейнера. 
	 * @param started
	 */
	public void setStarted(Boolean started)
	{
		try
		{
			if(started)
				start();
			else
				stop();
		}
		catch (GateException e)
		{
			log.error( "message: " + e.getMessage(), e);
			e.printStackTrace();
		}
	}
}
