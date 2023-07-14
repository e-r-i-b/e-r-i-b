package com.rssl.phizgate.common.services;

import com.rssl.phizic.ListenerConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * Обертка стаба обратных сервисов для обновления урл
 * @author gladishev
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class StubUrlBackServiceWrapper<S extends java.rmi.Remote> extends StubTimeoutUrlWrapper<S>
{
	private String serviceName;

	public StubUrlBackServiceWrapper(String serviceName)
	{
		super();
		this.serviceName = "/" + serviceName;
	}

	protected String getServiceName()
	{
		return serviceName;
	}

	protected String getUrl()
	{
		return ConfigFactory.getConfig(ListenerConfig.class).getUrl() + this.serviceName;
	}

	public Integer getTimeout()
	{
		Long timeout = getConfig().getConnectionBackTimeout();
		if (timeout == null)
			return null;

		return timeout.intValue();
	}
}
