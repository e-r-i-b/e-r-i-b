package com.rssl.phizic.gate.monitoring.fraud;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * Конфиг приложения FraudMonitoringBackGateApp
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ConfigImpl extends Config
{
	private static final String NEED_MESSAGE_LOGGING_ATTRIBUTE                  = "com.rssl.iccs.need.message.logging";

	private boolean needMessageLogging;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	public ConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		needMessageLogging = getBoolProperty(NEED_MESSAGE_LOGGING_ATTRIBUTE);
	}

	/**
	 * Показывает необходимость логировать обмен сообщениями с ВС ФМ
	 * @return true - да
	 */
	public boolean isNeedMessageLogging()
	{
		return needMessageLogging;
	}
}
