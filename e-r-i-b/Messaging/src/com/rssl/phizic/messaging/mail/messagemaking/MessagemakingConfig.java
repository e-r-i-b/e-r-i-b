package com.rssl.phizic.messaging.mail.messagemaking;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 16.03.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public abstract class MessagemakingConfig extends Config
{
	protected MessagemakingConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * ѕолучить класс имплементирующий MessageBuilder дл€ заданного канала передачи
	 * @param channel канал передачи
	 * @return им€ класса
	 */
	public abstract String getMessageBuilderClassName(String channel);

	/**
	 * ѕолучить класс имплементирующий AddressBuilder дл€ заданного канала передачи
	 * @param channel канал передачи
	 * @return им€ класса
	 */
	public abstract String getAddressBuilderClassName(String channel);

	/**
	 * ѕолучить класс имплементирующий TemplateLoader дл€ заданного канала передачи
	 * @param channel канал передачи
	 * @return им€ класса
	 */
	public abstract String getResourceLoaderClassName(String channel);

	/**
	 * @return —писок всех каналов передачи
	 */
	public abstract List<String> getAllChannels();
}
