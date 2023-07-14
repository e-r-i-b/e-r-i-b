package com.rssl.phizgate.common.messaging.retail;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Omeliyanchuk
 * @ created 25.11.2009
 * @ $Author$
 * @ $Revision$
 */

public class RetailMessagingConfigImpl extends RetailMessagingConfig
{
	String configFilePath;
	String systemName;
	String responceHandlerClass;

	public RetailMessagingConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		configFilePath = getProperty(RetailMessagingConfig.CONFIG_FILE_PATH);
		systemName = getProperty(RetailMessagingConfig.SYSTEM_NAME);
		responceHandlerClass = getProperty(RetailMessagingConfig.RESPONCE_HANDLER);
	}

	public String getConfigFilePath()
	{
		return configFilePath;
	}

	public String getSystemName()
	{
		return systemName;
	}

	public String getResponceHandlerClass()
	{
		return responceHandlerClass;
	}
}

