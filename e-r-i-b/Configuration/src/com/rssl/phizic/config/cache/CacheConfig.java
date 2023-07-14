package com.rssl.phizic.config.cache;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author osminin
 * @ created 13.08.15
 * @ $Author$
 * @ $Revision$
 *
 * Конфиг для работы с кэшем
 */
public class CacheConfig extends Config
{
	private static final String WAIT_MESSAGE_PROPERTY = "com.rssl.iccs.cache.wait.message";

	private String waitMessage;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 * @param reader ридер.
	 */
	public CacheConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		waitMessage = getProperty(WAIT_MESSAGE_PROPERTY);
	}

	/**
	 * Получить сообщение, которое необходимо отобразить клиенту, если вызван кэшируемый метод,
	 * но предыдущий вызов данного метода другим потоком еще не завершился и кэш еще не построен
	 *
	 * @return Сообщение, отображаемое клиенту
	 */
	public String getWaitMessage()
	{
		return waitMessage;
	}
}
