package com.rssl.phizic.config.system;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.Properties;

/**
 * @author bogdanov
 * @ created 22.01.14
 * @ $Author$
 * @ $Revision$
 */

public class SystemServiceConfig extends Config
{
	private static final String SERVICE_PREFIX = "services.payment.";
	private Properties paymentServices;

	public SystemServiceConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		paymentServices = getProperties(SERVICE_PREFIX);
	}

	public Properties getPaymentServices()
	{
		return paymentServices;
	}
}
