package com.rssl.phizic.esb.ejb.mock;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizic.utils.jms.MessageCreator;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;

import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author akrenev
 * @ created 27.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * заглушечный провайдер запросов в шину
 */

public class MockJMSTransportProvider
{
	private static final Map<ESBSegment, MockJMSTransportProvider> INSTANCES = new HashMap<ESBSegment, MockJMSTransportProvider>();
	private static final JmsService jmsService = new JmsService();

	private final QueueConnectionFactory factory;
	private final Queue outAsyncQueue;
	private final Queue outSyncQueue;

	private MockJMSTransportProvider(ESBSegment segment)
	{
		InitialContext ctx = null;
		try
		{
			ctx           = new InitialContext();
			factory       = (QueueConnectionFactory) ctx.lookup(segment.getQueueFactoryName());
			outAsyncQueue = (Queue) ctx.lookup(segment.getOfflineOutQueueName());
			outSyncQueue  = StringHelper.isEmpty(segment.getOnlineOutQueueName())? null: (Queue) ctx.lookup(segment.getOnlineOutQueueName());
		}
		catch (NamingException e)
		{
			throw new InternalErrorException("[ESB] Сбой при инициализации JMSTransportProvider", e);
		}
		finally
		{
			if (ctx != null)
				try { ctx.close(); } catch (NamingException ignored) {}
		}
	}

	/**
	 * @param segment сегмент шины
	 * @return инстанс
	 */
	public static MockJMSTransportProvider getInstance(ESBSegment segment)
	{
		MockJMSTransportProvider instance = INSTANCES.get(segment);
		if(instance != null)
			return instance;

		synchronized (INSTANCES)
		{
			instance = INSTANCES.get(segment);
			if (instance == null)
			{
				instance = new MockJMSTransportProvider(segment);
				INSTANCES.put(segment, instance);
			}
		}

		return instance;
	}

	/**
	 * поместить сообщение в очередь
	 * @param xml сообщение
	 * @param messageCreator конструктор jms сообщений
	 * @param correlationId идентификатор jms сообщений
	 * @return сообщение
	 * @throws javax.jms.JMSException
	 */
	public Message sendToAsync(String xml, MessageCreator messageCreator, String correlationId) throws JMSException
	{
		return jmsService.sendToQueue(xml, outAsyncQueue, factory, messageCreator, correlationId);
	}
	/**
	 * поместить сообщение в очередь
	 * @param xml сообщение
	 * @param messageCreator конструктор jms сообщений
	 * @param correlationId идентификатор jms сообщений
	 * @return сообщение
	 * @throws javax.jms.JMSException
	 */
	public Message sendToSync(String xml, MessageCreator messageCreator, String correlationId) throws JMSException
	{
		return jmsService.sendToQueue(xml, outSyncQueue, factory, messageCreator, correlationId);
	}
}
