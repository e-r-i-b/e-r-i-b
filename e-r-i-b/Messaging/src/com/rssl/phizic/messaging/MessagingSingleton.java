package com.rssl.phizic.messaging;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.messaging.ermb.jms.JmsTransportService;
import com.rssl.phizic.messaging.mail.SmsTransportService;
import com.rssl.phizic.messaging.mail.messagemaking.MessageVariablesProvider;
import com.rssl.phizic.messaging.mail.mobilebank.MobileBankSmsTransportService;
import com.rssl.phizic.messaging.push.PushTransportService;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 08.05.2010
 * @ $Author$
 * @ $Revision$
 */
public final class MessagingSingleton
{
	private PropertyReader reader = null;
	private MessageVariablesProvider messageVariablesProvider = null;
	private PushTransportService pushTransportService = null;
	private MessagingService messagingService = null;

	private static final MessagingSingleton instance = new MessagingSingleton();

	private MessagingSingleton()
	{

		reader = ConfigFactory.getReaderByFileName("messaging.properties");
		messageVariablesProvider = loadInstance(MessageVariablesProvider.class);
		messagingService = loadInstance(MessagingService.class);
		pushTransportService = loadInstance(PushTransportService.class);
	}

	public static MessagingSingleton getInstance()
	{
		return instance;
	}

	/**
	 * Инстанс сервиса отправки сообщений
	 * @param ermbConnectedPerson - подключен ли клиент к ЕРМБ
	 * @param additionalCheck - сообщение с проверкой IMSI или нет
	 * @return
	 */
	public SmsTransportService getSmsTransportService(boolean ermbConnectedPerson, boolean additionalCheck)
	{
		if (ermbConnectedPerson)
			return new JmsTransportService();

		if (additionalCheck)
			return new MobileBankSmsTransportService();

		if (ConfigFactory.getConfig(MessagingConfig.class).isErmbTransportUse())
			return new JmsTransportService();

		return new MobileBankSmsTransportService();
	}

	/**
	 * @return инстанс сервиса для МБК
	 * @param additionalCheck - сообщение с проверкой IMSI или нет
	 * @return
	 */
	public static SmsTransportService getMbkSmsTransportService (boolean additionalCheck)
	{
		if (!additionalCheck && ConfigFactory.getConfig(MessagingConfig.class).isErmbTransportUse())
			return new JmsTransportService();

		return new MobileBankSmsTransportService();
	}

	/**
	 * @return инстанс сервиса для ЕРМБ
	 */
	public SmsTransportService getErmbSmsTransportService ()
	{
		return new JmsTransportService();
	}

	/**
	 * @return поставщик переменных для шаблона сообщения
	 */
	public MessageVariablesProvider getMessageVariablesProvider()
	{
		return messageVariablesProvider;
	}

	/**
	 * @return фабрика посыльных
	 */
	public MessagingService getMessagingService()
	{
		return messagingService;
	}

	public PushTransportService getPushTransportService()
	{
		return pushTransportService;
	}

	private <T> T loadInstance(Class<T> serviceInterfaceClass)
	{
		String serviceClassKey = serviceInterfaceClass.getName();
		String serviceClassName = reader.getProperty(serviceClassKey);
		if (StringHelper.isEmpty(serviceClassName))
			throw new ConfigurationException("Ошибка при загрузке " + serviceClassKey + ": " +
					"не указан класс-имплементатор");

		try
		{
			try
			{
				@SuppressWarnings({"unchecked"})
				Class<? extends T> serviceImplClass =
						(Class<? extends T>) Class.forName(serviceClassName);
				return serviceImplClass.newInstance();
			}
			catch (ClassNotFoundException ex) {
				@SuppressWarnings({"unchecked"})
				Class<? extends T> serviceImplClass =
						(Class<? extends T>) Class.forName(serviceClassName, true, Thread.currentThread().getContextClassLoader());
				return serviceImplClass.newInstance();
			}
		}
		catch (ClassNotFoundException ex)
		{
			throw new ConfigurationException("Ошибка при загрузке " + serviceClassKey + " ", ex);
		}
		catch (IllegalAccessException ex)
		{
			throw new ConfigurationException("Ошибка при загрузке " + serviceClassKey + " ", ex);
		}
		catch (InstantiationException ex)
		{
			throw new ConfigurationException("Ошибка при загрузке " + serviceClassKey + " ", ex);
		}
	}
}
