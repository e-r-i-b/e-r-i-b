package com.rssl.phizic.armour;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.startup.StartupService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.management.MBeanException;
import javax.management.modelmbean.RequiredModelMBean;

/**
 * @author Krenev
 * @ created 05.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class ArmourProviderService extends RequiredModelMBean implements StartupService
{
	private static final Log log = LogFactory.getLog(Constants.LOG_MODULE_CORE.toValue());

	/**
	 * default ctor
	 * @throws javax.management.MBeanException
	 */
	public ArmourProviderService() throws MBeanException
	{
		log.info("ArmourProviderService instance created");
	}

	public boolean isInitialized()
	{
		return ArmourProviderHelper.isFactoryExists();
	}

	protected ArmourProviderFactory createFactory()
	{
		return new ArmourProviderFactory();
	}

	/**
	 * start
	 */
	public void start() throws ArmourProviderServiceException
	{
		log.info("ArmourProviderService starting");

		try{
			ArmourProviderFactory factory = createFactory();
			ArmourProviderHelper helper = new ArmourProviderHelper();
			helper.putFactory(factory);
		}
		catch (Exception e)
		{
			log.error("exception during starting ", e);
			throw new ArmourProviderServiceException("Exception during starting: ", e);
		}
		log.info("ArmourProviderService started");
	}

	/**
	 * stop
	 */
	public void stop()
	{
		log.info("ArmourProviderService stopping");
		try
		{
			ArmourProviderFactory factory = new ArmourProviderHelper().lookupFactory();
			if( factory != null )
				factory.release();
		}
		catch (Exception e)
		{
			log.error("Exception while stopping ArmourProviderService. ", e);
		}
		log.info("ArmourProviderService stopped");
	}

	public void postRegister(Boolean registrationDone)
	{
		super.postRegister(registrationDone);
		try
		{
			start();
		}
		catch (ArmourProviderServiceException e)
		{
			log.error( "message: " + e.getMessage() + " " + e.getStackTrace(), e);
		}
	}

	public void preDeregister() throws Exception
	{
		super.preDeregister();
		stop();
	}
}
