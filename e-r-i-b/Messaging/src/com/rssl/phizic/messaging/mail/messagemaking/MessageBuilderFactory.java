package com.rssl.phizic.messaging.mail.messagemaking;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateExceptionHandler;

import java.lang.reflect.Constructor;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.Session;

/**
 * @author Evgrafov
 * @ created 05.07.2006
 * @ $Author: rtishcheva $
 * @ $Revision: 64708 $
 */

public class MessageBuilderFactory
{
	private static final Object MAIL_SESSION_LOCKER = new Object();
	private static volatile Session mailSession = null;

	private MessagemakingConfig messagemakingConfig;

	/**
	 * @return создает новую фабрику
	 */
	public static MessageBuilderFactory newInstance()
	{
		return new MessageBuilderFactory();
	}

	private Configuration configuration;

	/**
	 * ctor
	 */
	public MessageBuilderFactory()
	{
		messagemakingConfig = ConfigFactory.getConfig(MessagemakingConfig.class);
	}

	/**
	 * @return сессия для email.
	 */
	public static Session getMailSession()
	{
		Session localMailSession = mailSession;
		if (localMailSession == null)
		{
			synchronized (MAIL_SESSION_LOCKER)
			{
				if (mailSession == null)
				{
					PropertyReader config = ConfigFactory.getReaderByFileName("messaging.properties");
					mailSession = Session.getInstance(config.getAllProperties());
				}
				localMailSession = mailSession;
			}
		}

		return localMailSession;
	}

	private MessageBuilder createMessageBuilder(String channel, String templateName)
	{
		String addressBuilderClassName = messagemakingConfig.getAddressBuilderClassName(channel);
		String messageBuilderClassName = messagemakingConfig.getMessageBuilderClassName(channel);

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		AddressBuilder ab;
		try
		{
			Class<?> abClazz = classLoader.loadClass(addressBuilderClassName);
			ab = (AddressBuilder) abClazz.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException("Ошибка при создании AddressBuilder для channel:" + channel, e);
		}

		MessageBuilder mb;
		try
		{
			Class<?> mbClazz = classLoader.loadClass(messageBuilderClassName);
			Constructor<?> ctor = mbClazz.getConstructor(Configuration.class,String.class, Session.class, AddressBuilder.class);
			mb = (MessageBuilder) ctor.newInstance(configuration, templateName, getMailSession(), ab);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Ошибка при создании MessageBuilder для channel:" + channel, e);
		}

		return mb;
	}

	/**
	 * Создать MessageBuilder для channel и templateName
	 * @param channel канал передачи
	 * @param templateName имя шаблона
	 * @return MessageBuilder
	 */
	public MessageBuilder newMessageBuilder(String channel, String templateName) throws MessagingException
	{
		configuration = new Configuration();

		// - Set an error handler that prints errors so they are readable with a HTML browser.
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		// - Use beans wrapper (recommmended for most applications)
		configuration.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
		// - Set the default charset of the template files
		configuration.setDefaultEncoding("windows-1251");
		// - Set the charset of the output. This is actually just a hint, that
		//   templates may require for URL encoding and for generating META element
		//   that uses http-equiv="Content-type".
		configuration.setOutputEncoding("windows-1251");
		// - Set the default locale
		configuration.setLocale(new Locale("ru", "RU"));

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		String resourceLoaderClassName = messagemakingConfig.getResourceLoaderClassName(channel);
		TemplateLoader tl;
		try
		{
			Class<?> tlClazz = classLoader.loadClass(resourceLoaderClassName);
			tl = (TemplateLoader) tlClazz.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException("Ошибка при создании TemplateLoader для channel:" + channel, e);
		}

		configuration.setTemplateLoader(tl);

		return createMessageBuilder(channel, templateName);
	}
}