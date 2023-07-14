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
			throw new IllegalArgumentException("������� �������� ������ ���� �� �����  1 ��");
		}
		if (batchSize < 1)
		{
			throw new IllegalArgumentException("������ ���� ��������� ������ ���� �� ����� 1");
		}
		if (flushTryCount < 1)
		{
			throw new IllegalArgumentException("���������� ������� ������ ��������� � �� ������ ���� �� ����� 1");
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
	 * @return ����������� ���� �����������
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
			throw new IllegalStateException("������� ��� �������");
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
		log.info("�������� ��������� ��������� �� ������� " + getQueueName());
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
				log.error("������ ��������� �������� ����", e);
			}
		}
		log.info("����������� ��������� ��������� �� ������� " + getQueueName());
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
			log.debug("������� �� " + POST_JMS_ERROR_WAIT_TIMEOUT + "��");
			Thread.sleep(POST_JMS_ERROR_WAIT_TIMEOUT);// �������
		}
		catch (InterruptedException ignored)
		{
		}
	}

	private void startConnection()
	{
		log.debug("������� connection");
		try
		{
			connection = connectionFactory.createQueueConnection();
			connection.start();
			//���������� ���������� - ����� ������ ��������� ������.
			currentCommand = Command.CREATE_SESSION;
		}
		catch (JMSException e)
		{
			log.error("������ �������� ������ JMS ����������", e);
			sleep();  // ������� � ������� ����� ������������� � �� ��������.
		}
	}

	private void closeConnection()
	{
		log.debug("��������� connection");
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
			currentCommand = Command.START_CONNECTION; //�� �� �������� ����� ���������� ���������� ������
		}
	}

	private void createSession()
	{
		log.debug("������� session � receiver");
		try
		{
			session = connection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
			receiver = session.createReceiver(queue);
			//������ ������� - ����� �������� ���������
			currentCommand = Command.PROCESS_MESSAGE;
		}
		catch (JMSException e)
		{
			log.error("������ �������� JMS ������", e);
			closeSession();//��������� ������
			sleep();  // �������
			currentCommand = Command.CLOSE_CONNECTION; // � ������� ����� ������������� � �� ��������.
		}
	}

	private void closeSession()
	{
		log.debug("��������� session � receiver");
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
		log.trace("�������� ���������");
		Message msg;
		try
		{
			msg = receiver.receive(timeout);
		}
		catch (JMSException e)
		{
			log.error("������ ��������� ���������. ������ ��������� ������� � ���������� ������", e);
			messages.clear();
			try
			{
				session.rollback();
			}
			catch (JMSException e1)
			{
				log.error("������ ��������� ���������", e1);
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
				log.trace("��������� ��������� � '��������� �������'");
				messages.add(getObjectFromMessage(msg));
			}
			catch (Exception e)
			{
				log.error("������ '���������� ���������'. ���������� ���", e);
				return;
			}
			if (messages.size() < batchSize)
			{
				return; // ����� ��� �� �������� - ���������� �������� ���������
			}
		}
		else
		{
			//��������� �� ������
			if (messages.isEmpty())
			{
				log.trace("����� ����� - ���������� �������� ���������");
				return;
			}
		}

		try
		{
			log.trace("C��������� ��������� � ��.");
			currentFlushTryCount++;
			storeMessages(); // ���������� ��������� � ��.
			log.trace("K������� ������.");
			session.commit(); // �������� ������
			currentFlushTryCount = 0;
		}
		catch (Exception e)
		{
			log.error("������ ������ ����� ���������. �������� �������:" + (flushTryCount - currentFlushTryCount), e);
			try
			{
				if (currentFlushTryCount < flushTryCount)
				{
					//�� ��������� ���������� �������
					session.rollback();
				}
				else
				{
					currentFlushTryCount = 0;
					log.warn("��������� ���������� ������� ������ ��������� � �� (" + flushTryCount + "). �������� ���������:" + messages.size() + ". �������:" + getQueueName());
					session.commit();
				}
			}
			catch (JMSException e1)
			{
				log.error("������ ������ ��� �������� ������", e1);
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