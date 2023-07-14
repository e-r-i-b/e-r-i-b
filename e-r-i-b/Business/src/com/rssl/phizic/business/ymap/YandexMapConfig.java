package com.rssl.phizic.business.ymap;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Gulov
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 */
public class YandexMapConfig extends Config
{
	private static final String COMPONENT_URL  = "com.rssl.iccs.yandex.map.component.url";
	private static final String SERVICE_URL = "com.rssl.iccs.yandex.map.service.url";
	private static final String SERVICE_TIMEOUT = "com.rssl.iccs.yandex.map.service.timeout";

	private String componentUrl;
	private String serviceUrl;
	private String serviceTimeout;

	public YandexMapConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		componentUrl = getProperty(COMPONENT_URL);
		serviceUrl = getProperty(SERVICE_URL);
		serviceTimeout = getProperty(SERVICE_TIMEOUT);
	}

	public String getComponentUrl()
	{
		return componentUrl;
	}

	public String getServiceUrl()
	{
		return serviceUrl;
	}

	public String getServiceTimeout()
	{
		return serviceTimeout;
	}
}
