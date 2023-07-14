package com.rssl.phizic.business.mbuesi;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * Настройки для сообщений UESI
 * @author Pankin
 * @ created 30.01.15
 * @ $Author$
 * @ $Revision$
 */
public class UESIMessagesConfig extends Config
{
	private static final String MESSAGES_STORAGE_TIME_KEY = "uesi.messages.storage.time";
	private int storageTime;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	public UESIMessagesConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		storageTime = getIntProperty(MESSAGES_STORAGE_TIME_KEY);
	}

	/**
	 * @return максимальное время в днях, в течение которого обрабатываются сообщения
	 */
	public int getStorageTime()
	{
		return storageTime;
	}
}
