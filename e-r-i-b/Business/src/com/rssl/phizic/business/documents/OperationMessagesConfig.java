package com.rssl.phizic.business.documents;

import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Конфиг для сообщений об исполнении операции
 * @author radionenko
 * @ created 09.01.14
 * @ $Author$
 * @ $Revision$
 */

public class OperationMessagesConfig extends Config
{
	public static final String NOT_ACTIVE_PROVIDER_MESSAGE = "not.active.provider.message";
	public static final String CHANGED_PROVIDER_MESSAGE    = "changed.provider.message";
	public static final String TEMPLATE_PLANING_FOR_DEACTIVATE = "com.rssl.iccs.payment.templates.message.planForDeacitivate";

	private static final Map<String, String> messages = new ConcurrentHashMap<String, String>();

	public OperationMessagesConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		for (String key : messages.keySet())
		{
			messages.put(key, getProperty(key));
		}
	}

	/**
	 *
	 * @param messageKey - ключ сообщения
	 * @return сообщение по ключу
	 */
	public String getOperationMessage(final String messageKey)
	{
		if(messageKey == null)
			return null;

		String message = messages.get(messageKey);
		if (StringHelper.isNotEmpty(message))
		{
			return message;
		}
		else
		{
			String property = getProperty(messageKey);
			if (StringHelper.isNotEmpty(property))
			{
				messages.put(messageKey, property);
			}

			return property;
		}
	}
}
