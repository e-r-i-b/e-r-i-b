package com.rssl.phizic.test.externalSystem.monitoring;

import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.utils.naming.NamingHelper;
import org.apache.commons.logging.LogFactory;

import java.lang.IllegalStateException;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author akrenev
 * @ created 24.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * поток, собирающий инфу
 */

class MonitoringESRequestJMSReceiverTread implements Runnable
{
	protected static final org.apache.commons.logging.Log log = LogFactory.getLog(MonitoringESRequestJMSReceiverTread.class);

	private volatile boolean isActive = false;

	private String queueFactoryName;
	private String queueName;
	private long timeout;

	MonitoringESRequestJMSReceiverTread(long timeout, String queueName, String queueFactoryName)
	{
		if (timeout < 1)
		{
			throw new IllegalArgumentException("Таймаут ожидания должен быть не менее 1 мс.");
		}
		this.timeout = timeout;
		this.queueFactoryName = queueFactoryName;
		this.queueName = queueName;
	}

	void start() throws JMSException, NamingException
	{
		if (isActive)
			throw new IllegalStateException("Ресивер уже запущен.");

		isActive = true;
	}

	void stop()
	{
		isActive = false;
	}

	public void run()
	{
		log.info("Начинаем получение сообщений из очереди " + queueName);
		QueueConnection connection = null;
		QueueSession session = null;
		QueueReceiver receiver = null;
		try
		{
			InitialContext context = NamingHelper.getInitialContext();
			QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup(queueFactoryName);
			Queue queue = (Queue) context.lookup(queueName);
			connection = connectionFactory.createQueueConnection();
			connection.start();
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			receiver = session.createReceiver(queue);
			while (isActive)
			{
				Message msg = receiver.receive(timeout);
				if (msg == null)
					continue;

				process(msg);
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка обработки текущего шага.", e);
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
		log.info("Заканчиваем получение сообщений из очереди " + queueName);
	}

	private void process(Message message) throws JMSException
	{
		try
		{
			log.trace("Обновляем информацию.");
			MessagingLogEntry logEntry = (MessagingLogEntry) ((ObjectMessage) message).getObject();
			MonitoringESRequestCollector.getInstance().add(logEntry);
		}
		catch (Exception e)
		{
			log.error("Ошибка обработки информации.", e);
		}
	}
}