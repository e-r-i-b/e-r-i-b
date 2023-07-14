package com.rssl.phizicgate.way4u;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author krenev
 * @ created 08.10.2013
 * @ $Author$
 * @ $Revision$
 * Конфиг шлюза way4u
 */

public class Config extends com.rssl.phizic.config.Config
{
	private static final String RESPONCE_TIMEOUT_PROPERTY_NAME = "com.rssl.phizicgate.way4u.responce-timeout";
	private Integer responseTimeout;

	public Config(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		responseTimeout = getIntProperty(RESPONCE_TIMEOUT_PROPERTY_NAME);
	}

	/**
	 * @return Таймаут ожидания ответа в миллисекундах. 0 - без таймаута.
	 */
	public long getResponseTimeout()
	{
		return responseTimeout;
	}
}
