package com.rssl.phizic.utils.jms;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.io.Serializable;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Rtischeva
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class JmsService
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	/**
	 * ���������� ��������� ��������� � �������
	 * @param messageText - ���������
	 * @param queueName  - �������� �������
	 * @param connectionFactoryName - �������� ������� ����������
	 * @param jmsType
	 * @param jmsCorrelationID
	 * @return ��������� ���������
	 * @throws JMSException
	 */
	public TextMessage sendMessageToQueue(String messageText, String queueName, String connectionFactoryName, String jmsType, String jmsCorrelationID) throws JMSException
	{
		return sendToQueue(messageText, queueName, connectionFactoryName, TextMessageCreator.getInstance(), jmsType, jmsCorrelationID);
	}

	/**
	 * ���������� ������ ���� � �������
	 * @param messageBytes
	 * @param queueName
	 * @param connectionFactoryName
	 * @param jmsType
	 * @param jmsCorrelationID
	 * @return
	 * @throws JMSException
	 */
	public BytesMessage sendBytesToQueue(byte[] messageBytes, String queueName, String connectionFactoryName, String jmsType, String jmsCorrelationID) throws JMSException
	{
		return sendToQueue(messageBytes, queueName, connectionFactoryName, BytesMessageCreator.getInstance(), jmsType, jmsCorrelationID);
	}

	/**
	 * ���������� ��������� ��������� c ���������������� ���������� � �������
	 * @param messageText - ���������
	 * @param queueName  - �������� �������
	 * @param connectionFactoryName - �������� ������� ����������
	 * @param jmsCorrelationID - correlationId
	 * @param usersHeaders - ���������������� ���������
	 * @return ��������� ���������
	 * @throws JMSException
	 */
	public TextMessage sendMessageWithUserHeadersToQueue(String messageText, String queueName, String connectionFactoryName, String jmsCorrelationID, Pair<String,String>...usersHeaders) throws JMSException
	{
		return sendToQueue(messageText, queueName, connectionFactoryName, TextMessageWithUserHeadersCreator.getInstance(usersHeaders), null, jmsCorrelationID);
	}

	/**
	 * ���������� ��������� ��������� � ������� ESB
	 * @param messageText - ���������
	 * @param queue  - ������� ���������
	 * @param connectionFactory - ������� ����������
	 * @param creator - ������ ���������
	 * @return ��������� ���������
	 * @throws JMSException
	 */
	public <T extends Message> T sendToQueue(String messageText, Queue queue, QueueConnectionFactory connectionFactory, MessageCreator<T> creator) throws JMSException
	{
		return sendToQueue(messageText, queue, connectionFactory, creator, null, null);
	}

	/**
	 * ���������� ��������� ��������� � ������� ESB
	 * @param messageText ���������
	 * @param queue ������� ���������
	 * @param connectionFactory ������� ����������
	 * @param creator ������ ���������
	 * @param jmsCorrelationId ������������� ���������.
	 * @return ��������� ���������
	 * @throws JMSException
	 */
	public <T extends Message> T sendToQueue(String messageText, Queue queue, QueueConnectionFactory connectionFactory, MessageCreator<T> creator, String jmsCorrelationId) throws JMSException
	{
		return sendToQueue(messageText, queue, connectionFactory, creator, null, jmsCorrelationId);
	}


	/**
	 * ���������� ��������� � �������
	 * @param object - ���������
	 * @param queueName  - �������� �������
	 * @param connectionFactoryName - �������� ������� ����������
	 * @param jmsType
	 * @param jmsCorrelationID
	 * @return ��������� ���������
	 * @throws JMSException
	 */
	public ObjectMessage sendObjectToQueue(Serializable object, String queueName, String connectionFactoryName, String jmsType, String jmsCorrelationID) throws JMSException
	{
		return sendToQueue(object, queueName, connectionFactoryName, ObjectMessageCreator.getInstance(), jmsType, jmsCorrelationID);
	}

	private <T extends Message> T sendToQueue(Serializable object, String queueName, String connectionFactoryName, MessageCreator<T> creator, String jmsType, String jmsCorrelationID) throws JMSException
	{
		InitialContext ctx = null;
		try
		{
			ctx = new InitialContext();
			QueueConnectionFactory connectionFactory = (QueueConnectionFactory)ctx.lookup(connectionFactoryName);
			Queue queue = (Queue) ctx.lookup(queueName);

			return sendToQueue(object, queue, connectionFactory, creator, jmsType, jmsCorrelationID);
		}
		catch (NamingException e)
		{
			log.error(e.getMessage(), e);
			//noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
			throw new JMSException(e.getLocalizedMessage());
		}
		finally
		{
			if (ctx != null)
				try { ctx.close(); } catch (NamingException ignored) {}
		}
	}

	private <T extends Message> T sendToQueue(Serializable object, Queue queue, QueueConnectionFactory connectionFactory, MessageCreator<T> creator, String jmsType, String jmsCorrelationID) throws JMSException
	{
		QueueConnection connection = null;
		QueueSession session = null;
		QueueSender sender = null;
		try
		{

			// 1. �������� JMS-��������: ����������, ������ � ������
			connection = connectionFactory.createQueueConnection();
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			sender = session.createSender(queue);

			// 2. �������� ���������
			T message = creator.create(session, object);
			message.setJMSType(jmsType);
			message.setJMSCorrelationID(jmsCorrelationID);
			sender.send(message);
			return message;
		}
		finally
		{

			if (sender != null)
				try { sender.close(); } catch (JMSException ignored) {}
			if (session != null)
				try { session.close(); } catch (JMSException ignored) {}
			if (connection != null)
				try { connection.close(); } catch (JMSException ignored) {}
		}
	}

	/**
	 * �������� ��������� ��������� �� ������� � ���������� � �������� ���������
	 * @param queueName  - �������� �������
	 * @param connectionFactoryName - �������� ������� ����������
	 * @param selector - ��������. ���� �� ����� ��� ������, ��������� ���������� ��� ����������
	 * @param timeout - ������� �������� ������ � �������������. 0 - ��� ��������.
	 * @return ��������� ��������� �� �������. ���� null - ����� �������.
	 */
	public <T extends Message> T receiveMessageFormQueue(String queueName, String connectionFactoryName, String selector, long timeout) throws JMSException
	{

		InitialContext ctx = null;
		try
		{
			ctx = new InitialContext();
			QueueConnectionFactory connectionFactory = (QueueConnectionFactory)ctx.lookup(connectionFactoryName);
			Queue queue = (Queue) ctx.lookup(queueName);
			return (T) receiveMessageFormQueue(queue, connectionFactory, selector, timeout);
		}
		catch (NamingException e)
		{
			log.error(e.getMessage(), e);
			//noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
			throw new JMSException(e.getLocalizedMessage());
		}
		finally
		{
			if (ctx != null)
				try { ctx.close(); } catch (NamingException ignored) {}
		}
	}

	/**
	 * �������� ��������� ��������� �� ������� � ���������� � �������� ���������
	 * @param queue  - �������
	 * @param connectionFactory - ������� ����������
	 * @param selector - ��������. ���� �� ����� ��� ������, ��������� ���������� ��� ����������
	 * @param timeout - ������� �������� ������ � �������������. 0 - ��� ��������.
	 * @return ��������� ��������� �� �������. ���� null - ����� �������.
	 */
	public <T extends Message> T receiveMessageFormQueue(Queue queue, QueueConnectionFactory connectionFactory, String selector, long timeout) throws JMSException
	{

		QueueConnection connection = null;
		QueueSession session = null;
		QueueReceiver receiver = null;
		try
		{
			connection = connectionFactory.createQueueConnection();
			connection.start();
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			receiver = session.createReceiver(queue, selector);
			//noinspection unchecked
			return (T) receiver.receive(timeout);
		}
		finally
		{
			if (receiver != null)
				try { receiver.close(); } catch (JMSException ignored) {}
			if (session != null)
				try { session.close(); } catch (JMSException ignored) {}
			if (connection != null)
				try { connection.close(); } catch (JMSException ignored) {}
		}
	}
}
