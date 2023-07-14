package com.rssl.phizic.logging.ermb;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * Конфиг для настроек логирования превышении лимита запросов в ЕРМБ/МБК  по номеру телефона
 * @author lukina
 * @ created 07.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class RequestCardByPhoneLogConfig  extends Config
{
	public static final String LOGGING_ON_KEY = "com.rssl.phizic.logging.ermb.RequestCardByPhoneLog.on";

	private boolean loggingOn;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	public RequestCardByPhoneLogConfig(PropertyReader reader)
	{
		super(reader);
	}


	/**
	 * @return true - логирование включено
	 */
	public boolean isLoggingOn()
	{
		return loggingOn;
	}

	protected void doRefresh() throws ConfigurationException
	{
		loggingOn = getBoolProperty(LOGGING_ON_KEY);
	}
}
