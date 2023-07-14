package com.rssl.phizic.logging.jms;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.utils.naming.NamingHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;

import java.io.Serializable;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author krenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class JMSQueueSender
{
	private Queue queue;
	private QueueConnectionFactory connectionFactory;
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * Конструкуктор "отсыльщика в очередь"
	 * @param factoryName jndi имя фабрики соединения очереди
	 * @param queueName  jndi имя очереди
	 */
	public JMSQueueSender(String factoryName, String queueName)
	{
		try
		{
			InitialContext context = NamingHelper.getInitialContext();
			connectionFactory = (QueueConnectionFactory) context.lookup(factoryName);
			queue = (Queue) context.lookup(queueName);
		}
		catch (NamingException e)
		{
			throw new ConfigurationException("Ошибка конфигурирования jms", e);
		}
	}

	/**
	 * Послать ObjectMessage
	 * @param obj объект токотоый следует поместить в очередь
	 * @throws JMSException
	 */
	public void send(Serializable obj) throws JMSException
	{
		MessageProducer sender = null;
		Session session = null;
		QueueConnection connection = null;
		try
		{
			connection = connectionFactory.createQueueConnection();
			connection.start();
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			sender = session.createProducer(queue);
			ObjectMessage msg = session.createObjectMessage();
			msg.setObject(obj);
			sender.send(msg);
		}
		finally
		{
			try
			{
				if (sender != null)
					sender.close();
			}
			catch (JMSException e)
			{
				log.error("Не удалось закрыть sender ", e);
			}
			try
			{
				if (session != null)
					session.close();
			}
			catch (JMSException e)
			{
				log.error("Не удалось закрыть сессию ", e);
			}
			try
			{
				if (connection != null)
					connection.close();
			}
			catch (JMSException e)
			{
				log.error("Не удалось закрыть соедиение ", e);
			}
		}
	}
}
