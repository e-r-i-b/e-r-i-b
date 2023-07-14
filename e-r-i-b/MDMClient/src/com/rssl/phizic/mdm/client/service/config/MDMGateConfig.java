package com.rssl.phizic.mdm.client.service.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author akrenev
 * @ created 08.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Параметры для доступа к приложению МДМ
 */

public class MDMGateConfig extends Config
{
	private static final String URL_KEY = "com.rssl.phizic.mdmapp.listener.url";
	private static final String TIMEOUT_KEY = "com.rssl.phizic.mdmapp.listener.timeout";

	private String listenerUrl;
	private int listenerTimeout;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 * @param reader ридер.
	 */
	public MDMGateConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return урл на котором развернуты вебсервисы МДМ
	 */
	public String getMDMAppUrl()
	{
		return listenerUrl;
	}

	/**
	 * @return таймаут на соединение к МДМ
	 */
	public int getMDMAppTimeout()
	{
		return listenerTimeout;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		listenerUrl = getProperty(URL_KEY);
		listenerTimeout = getIntProperty(TIMEOUT_KEY);
	}
}
