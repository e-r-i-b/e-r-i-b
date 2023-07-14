package com.rssl.phizic.messaging;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * Конфиг с настройками для транспортного сервиса
 * @author Rtischeva
 * @ created 04.08.14
 * @ $Author$
 * @ $Revision$
 */
public class MessagingConfig extends Config
{
	private Long numberOfIMSICheckTries = null; //Настройка "Количество попыток получения результатов проверки IMSI"
	private Long imsiCheckDelayTime = null; //Настройка "Задержка между попытками получения результатов проверки IMSI"
	private boolean ermbSmsRequestIdSettingEnabled; //Флажок "Включена ли передача идентификатора смс-запроса в заголовке JMS сообщения"
	private boolean ermbTransportUse; //Настройка "Отправлять все сообщения в MSS"

	public MessagingConfig(PropertyReader reader)
	{
		super(reader);
	}

	public Long getNumberOfIMSICheckTries()
	{
		return numberOfIMSICheckTries;
	}

	public Long getImsiCheckDelayTime() throws ConfigurationException
	{
		return imsiCheckDelayTime;
	}

	public boolean isErmbSmsRequestIdSettingEnabled()
	{
		return ermbSmsRequestIdSettingEnabled;
	}

	public boolean isErmbTransportUse()
	{
		return ermbTransportUse;
	}

	public void doRefresh() throws ConfigurationException
	{
		numberOfIMSICheckTries = getLongProperty("ermb.transport.tries");
		imsiCheckDelayTime = getLongProperty("ermb.transport.delay.time");
		ermbSmsRequestIdSettingEnabled = getBoolProperty("ermb.smsRequestId.setting.enabled");
		ermbTransportUse = getBoolProperty("ermb.transport.use");
	}
}
