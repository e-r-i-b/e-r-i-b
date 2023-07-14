package com.rssl.phizic.web.jms;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.naming.NamingHelper;
import org.apache.commons.logging.LogFactory;

import java.lang.IllegalStateException;
import java.util.ArrayList;
import java.util.List;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author krenev
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 *
 */

public class JMSReceiverTreadBase implements Runnable
{
	private static final long POST_JMS_ERROR_WAIT_TIMEOUT = 1000;
	protected static final org.apache.commons.logging.Log log = LogFactory.getLog(JMSReceiverTreadBase.class);

	private volatile boolean isActive = false;
	private Queue queue;
	private long timeout;
	private int batchSize;
	private int flushTryCount;
	private int currentFlushTryCount;
	private QueueConnectionFactory connectionFactory;
	private Command currentCommand = Command.START_CONNECTION;
	private QueueConnection connection;
	private QueueSession session;
	private QueueReceiver receiver;
	private String factoryName;
	private String queueName;
	private String dbInstanceName;
	private List<Object> messages;

	protected enum Command
	{
		START_CONNECTION, CREATE_SESSION, PROCESS_MESSAGE, CLOSE_CONNECTION, CLOSE_SESSION;
	}

	public JMSReceiverTreadBase(long timeout, int batchSize, int flushTryCount, String queueName, String queueFactoryName, String dbInstanceName)
	{
		if (timeout < 1)
		{
			throw new IllegalArgumentException("Таймаут ожидания должен быть не менее  1 мс");
		}
		if (batchSize < 1)
		{
			throw new IllegalArgumentException("Размер паки сообщений должен быть не менее 1");
		}
		if (flushTryCount < 1)
		{
			throw new IllegalArgumentException("Количество попыток сброса сообщений в БД должно быть не менее 1");
		}
		this.timeout = timeout;
		this.batchSize = batchSize;
		this.flushTryCount = flushTryCount;
		messages = new ArrayList<Object>(batchSize);
		factoryName = queueFactoryName;
		this.queueName = queueName;
		this.dbInstanceName = dbInstanceName;
	}

	protected String getQueueName()
	{
		return queueName;
	}

	protected String getFactoryName()
	{
		return factoryName;
	}

	/**
	 * @return инстанснейм базы логирования
	 */
	public String getDbInstanceName()
	{
		return dbInstanceName;
	}

	protected void storeMessage(org.hibernate.Session session, Object message) throws Exception
	{
		session.save(message);
	}

	protected void start() throws JMSException, NamingException
	{
		if (isActive)
		{
			throw new IllegalStateException("Ресивер уже запущен");
		}
		InitialContext context = NamingHelper.getInitialContext();
		connectionFactory = (QueueConnectionFactory) context.lookup(getFactoryName());
		queue = (Queue) context.lookup(getQueueName());

		isActive = true;
		new Thread(this).start();
	}

	protected void stop()
	{
		connectionFactory = null;
		queue = null;
		isActive = false;
	}

	public void run()
	{
		log.info("Начинаем получение сообщений из очереди " + getQueueName());
		currentFlushTryCount = 0;
		while (isActive)
		{
			if(isIdle())
			{
				sleepIdle();
				continue;
			}

			try
			{
				switch (currentCommand)
				{
					case START_CONNECTION:
						startConnection();
						break;
					case CLOSE_CONNECTION:
						closeConnection();
						break;
					case CREATE_SESSION:
						createSession();
						break;
					case CLOSE_SESSION:
						closeSession();
						break;
					case PROCESS_MESSAGE:
						processMessage();
				}
			}
			catch (Exception e)
			{
				log.error("Ошибка обработки текущего шага", e);
			}
		}
		log.info("Заканчиваем получение сообщений из очереди " + getQueueName());
		closeSession();
		closeConnection();
	}

	protected boolean isIdle()
	{
		return false;
	}

	protected void sleepIdle()
	{
		sleep();
	}

	private void sleep()
	{
		try
		{
			log.debug("Усыпаем на " + POST_JMS_ERROR_WAIT_TIMEOUT + "мс");
			Thread.sleep(POST_JMS_ERROR_WAIT_TIMEOUT);// усыпаем
		}
		catch (InterruptedException ignored)
		{
		}
	}

	private void startConnection()
	{
		log.debug("Создаем connection");
		try
		{
			connection = connectionFactory.createQueueConnection();
			connection.start();
			//соединение стартанули - будем дальше создавать сессию.
			currentCommand = Command.CREATE_SESSION;
		}
		catch (JMSException e)
		{
			log.error("Ошибка создания старта JMS соединения", e);
			sleep();  // усыпаем и пробуем снова законектиться в сл итерации.
		}
	}

	private void closeConnection()
	{
		log.debug("Закрываем connection");
		try
		{
			if (connection != null)
			{
				connection.close();
			}
		}
		catch (JMSException ignored)
		{
		}
		finally
		{
			connection = null;
			currentCommand = Command.START_CONNECTION; //на сл итерации будем стартовать соединение заново
		}
	}

	private void createSession()
	{
		log.debug("Создаем session и receiver");
		try
		{
			session = connection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
			receiver = session.createReceiver(queue);
			//сесиию создали - будем получать сообщения
			currentCommand = Command.PROCESS_MESSAGE;
		}
		catch (JMSException e)
		{
			log.error("Ошибка создания JMS сессии", e);
			closeSession();//закрываем сесиию
			sleep();  // усыпаем
			currentCommand = Command.CLOSE_CONNECTION; // и пробуем снова законектиться в сл итерации.
		}
	}

	private void closeSession()
	{
		log.debug("Закрываем session и receiver");
		try
		{
			if (receiver != null)
			{
				receiver.close();
			}
			if (session != null)
			{
				session.close();
			}
		}
		catch (JMSException ignored)
		{
		}
		finally
		{
			receiver = null;
			session = null;
			currentCommand = Command.CREATE_SESSION;
		}
	}

	protected Object getObjectFromMessage(Message msg) throws JMSException
	{
		return ((ObjectMessage) msg).getObject();
	}

	protected void processMessage()
	{
		log.trace("Получаем сообщение");
		Message msg;
		try
		{
			msg = receiver.receive(timeout);
		}
		catch (JMSException e)
		{
			log.error("Ошибка получения сообщения. Чистим локальную очередь и откатываем сесиию", e);
			messages.clear();
			try
			{
				session.rollback();
			}
			catch (JMSException e1)
			{
				log.error("Ошибка получения сообщения", e1);
			}
			finally
			{
				currentCommand = Command.CLOSE_SESSION;
			}
			return;
		}
		if (msg != null)
		{
			try
			{
				log.trace("Сохраняем сообщение в 'локальную очередь'");
				messages.add(getObjectFromMessage(msg));
			}
			catch (Exception e)
			{
				log.error("Ошибка 'распаковки сообщения'. Пропускаем его", e);
				return;
			}
			if (messages.size() < batchSize)
			{
				return; // пачки еще не достигли - продолжаем получать сообщения
			}
		}
		else
		{
			//сообщение не пришло
			if (messages.isEmpty())
			{
				log.trace("Пачка пуста - продолжаем получать сообщения");
				return;
			}
		}

		try
		{
			log.trace("Cбрасываем сообщения в БД.");
			currentFlushTryCount++;
			storeMessages(); // сбрасываем сообщения в БД.
			log.trace("Kоммитим сессию.");
			session.commit(); // коммитим сессию
			currentFlushTryCount = 0;
		}
		catch (Exception e)
		{
			log.error("Ошибка записи пачки сообщений. Осталось попыток:" + (flushTryCount - currentFlushTryCount), e);
			try
			{
				if (currentFlushTryCount < flushTryCount)
				{
					//не исчерпали количество попыток
					session.rollback();
				}
				else
				{
					currentFlushTryCount = 0;
					log.warn("Исчерпали количество попыток сброса сообщений в БД (" + flushTryCount + "). Потеряно сообщений:" + messages.size() + ". Очередь:" + getQueueName());
					session.commit();
				}
			}
			catch (JMSException e1)
			{
				log.error("Ошибка отката или фиксации сессии", e1);
			}
		}
		finally
		{
			currentCommand = Command.CLOSE_SESSION;
			messages.clear();
		}
	}

	private void storeMessages() throws Exception
	{
		HibernateExecutor.getInstance(dbInstanceName).execute(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				for (Object message : messages)
				{
					storeMessage(session, message);
				}
				return null;
			}
		}
		);
	}

	protected QueueReceiver getReceiver()
	{
		return receiver;
	}

	protected long getTimeOut()
	{
		return timeout;
	}

	protected QueueSession getSession()
	{
		return session;
	}

	protected void setCurrentCommand(Command command)
	{
		currentCommand = command;
	}
}