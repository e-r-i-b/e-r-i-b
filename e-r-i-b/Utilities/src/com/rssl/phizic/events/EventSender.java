package com.rssl.phizic.events;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * @author Krenev
 * @ created 01.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class EventSender
{
	private static final String TOPIC_NAME = "jms/PhizEventTopic";
	private static final String CONNECTION_FACTORY_NAME = "jms/PhizConnectionFactory";
	private Topic topic;
	private TopicConnectionFactory connectionFactory;
	private static EventSender instance = null;
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private EventSender() throws NamingException
	{
//      дебажный вывод на консоль работы с oracle advanced queqes
//		AQjmsOracleDebug.setLogStream(System.out);
//		AQjmsOracleDebug.setTraceLevel(1);
//		AQjmsOracleDebug.setDebug(false);

		Context context = new InitialContext();
		try
		{
			connectionFactory = (TopicConnectionFactory) context.lookup(CONNECTION_FACTORY_NAME);
			topic = (Topic) context.lookup(TOPIC_NAME);
		}
		finally
		{
			context.close();
		}
	}

	public static EventSender getInstance() throws NamingException
	{
		if (instance == null)
		{
			instance = new EventSender();
		}
		return instance;
	}

	public void sendEvent(Event event) throws Exception
	{
		TopicConnection connection = null;
		TopicSession session = null;
		TopicPublisher publisher = null;
		try
		{
			connection = connectionFactory.createTopicConnection();
			connection.start();
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			publisher = session.createPublisher(topic);
			Message msg = session.createObjectMessage(event);
			msg.setJMSType(event.getClass().getName());
			publisher.publish(msg);
		}
		finally
		{
			try
			{
				if (publisher != null)
					publisher.close();
			}
			catch (JMSException e)
			{
				log.error("Ќе удалось закрыть publisher ", e);
			}
			try
			{
				if (session != null)
					session.close();
			}
			catch (JMSException e)
			{
				log.error("Ќе удалось закрыть сессию ", e);
			}
			try
			{
				if (connection != null)
					connection.close();
			}
			catch (JMSException e)
			{
				log.error("Ќе удалось закрыть соединение ", e);
			}
		}
	}
}
