package com.rssl.phizic.esb.ejb.mock;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.messaging.MessageCoordinator;
import com.rssl.phizic.messaging.MessageProcessor;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.module.loader.ModuleLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author akrenev
 * @ created 27.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Базовый тестовый листенер запросов в шину
 */

public abstract class ESBMockMessageListenerBase implements MessageDrivenBean, MessageListener
{
	private static final Log log = LogFactory.getLog(LogModule.Web.toString());

	private final static Map<String, Module> startedModules = new HashMap<String, Module>();

	protected abstract MessageCoordinator getMessageCoordinator();
	protected abstract ESBMockMessageParser<ESBMessage> getParser();

	@SuppressWarnings({"NoopMethodInAbstractClass"})
	public void setMessageDrivenContext(MessageDrivenContext ctx){}

	public final void onMessage(Message message)
	{
		ApplicationInfo.setCurrentApplication(Application.EjbTest);
		ESBMockMessageParser<ESBMessage> parser = getParser();

		ESBMessage xmlRequest = null;
		try
		{
			xmlRequest = parser.parseMessage(getMessageText(message), message);
			if (xmlRequest == null)
				return;
		}
		catch (Exception e)
		{
			log.error("[ESB-MQ MOCK] Сбой на парсиинге JMS-сообщения - " + e.getMessage(), e);
			return;
		}

		MessageProcessor messageProcessor = getMessageCoordinator().getProcessor(xmlRequest);

		if (messageProcessor == null)
		{
			log.error("[ESB-MQ MOCK] Не нашли процессор для сообщения - " + xmlRequest.getObject().getClass().getSimpleName());
			return;
		}

		Module module = messageProcessor.getModule();
		startModule(module);

		try
		{
			//noinspection unchecked
			messageProcessor.processMessage(xmlRequest);
		}
		catch (Throwable e)
		{
			log.error("[ESB-MQ MOCK] Сбой на обработке JMS-сообщения - " + e.getMessage(), e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	private com.rssl.phizic.common.types.TextMessage getMessageText(Message message)
	{
		if (message instanceof TextMessage)
		{
			TextMessage textMessage = (TextMessage) message;

			try
			{
				return new com.rssl.phizic.common.types.TextMessage(textMessage.getText());
			}
			catch (JMSException e)
			{
				throw new RuntimeException(e);
			}
		}

		throw new IllegalArgumentException("Неожиданный тип JMS-сообщения " + message.getClass());
	}

	public final void ejbRemove()
	{
		Iterator<String> iterator = startedModules.keySet().iterator();

		while (iterator.hasNext())
		{
			Module module = startedModules.get(iterator.next());
			ModuleLoader moduleLoader = module.getModuleLoader();
			if (moduleLoader != null)
				moduleLoader.stop();
			iterator.remove();
		}
	}

	private void startModule(Module module)
	{
		if (!startedModules.containsKey(module.getName()))
		{
			ModuleLoader moduleLoader = module.getModuleLoader();
			moduleLoader.start();
			startedModules.put(module.getName(), module);
		}
	}
}
