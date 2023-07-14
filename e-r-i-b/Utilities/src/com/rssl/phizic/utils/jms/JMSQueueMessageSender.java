package com.rssl.phizic.utils.jms;

import com.rssl.phizic.common.types.annotation.OptionalParameter;
import com.rssl.phizic.utils.StringHelper;

import java.io.Closeable;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Erkin
 * @ created 31.12.2014
 * @ $Author$
 * @ $Revision$
 */
public class JMSQueueMessageSender implements Closeable
{
	private QueueConnection connection;

	private QueueSession session;

	private QueueSender sender;

	private String jmsType;

	private String jmsCorrelationID;

	private String jmsxGroupID;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param queueName - JNDI-имя очереди (never null nor empty)
	 * @param connectionFactoryName - JNDI-имя фабрики соединений (never null nor empty)
	 */
	public JMSQueueMessageSender(String queueName, String connectionFactoryName) throws JMSException, NamingException
	{
		boolean welldone = false;
		InitialContext ctx = null;
		try
		{
			ctx = new InitialContext();

			QueueConnectionFactory connectionFactory = (QueueConnectionFactory) ctx.lookup(connectionFactoryName);
			Queue queue = (Queue) ctx.lookup(queueName);

			connection = connectionFactory.createQueueConnection();
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			sender = session.createSender(queue);

			welldone = true;
		}
		finally
		{
			if (ctx != null)
				try { ctx.close(); } catch (NamingException ignored) {}

			if (!welldone)
				close();
		}
	}

	@OptionalParameter
	public void setJMSType(String jmsType)
	{
		this.jmsType = jmsType;
	}

	@OptionalParameter
	public void setJMSCorrelationID(String jmsCorrelationID)
	{
		this.jmsCorrelationID = jmsCorrelationID;
	}

	/**
	 * @param jmsxGroupID - идентификатор группы сообщений
	 */
	@OptionalParameter
	public void setJMSXGroupID(String jmsxGroupID)
	{
		this.jmsxGroupID = jmsxGroupID;
	}

	/**
	 * Отправить текстовое сообщение
	 * @param messageText - текст сообщения (never null nor empty)
	 */
	public void sendTextMessage(String messageText) throws JMSException
	{
		TextMessage message = session.createTextMessage(messageText);
		setupMessage(message);
		sender.send(message);
	}

	private void setupMessage(Message message) throws JMSException
	{
		if (StringHelper.isNotEmpty(jmsType))
			message.setJMSType(jmsType);

		if (StringHelper.isNotEmpty(jmsCorrelationID))
			message.setJMSCorrelationID(jmsCorrelationID);

		if (StringHelper.isNotEmpty(jmsxGroupID))
		{
			message.setStringProperty("JMSXGroupID", jmsxGroupID);
			message.setBooleanProperty("JMS_IBM_Last_Msg_In_Group", true);
		}
	}

	public void close()
	{
		if (sender != null)
			try { sender.close(); } catch (JMSException ignored) {}
		if (session != null)
			try { session.close(); } catch (JMSException ignored) {}
		if (connection != null)
			try { connection.close(); } catch (JMSException ignored) {}

		sender = null;
		session = null;
		connection = null;
	}
}
