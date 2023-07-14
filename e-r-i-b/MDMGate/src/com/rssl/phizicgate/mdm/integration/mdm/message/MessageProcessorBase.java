package com.rssl.phizicgate.mdm.integration.mdm.message;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * базовый процессор jms запросов в шину
 */

public abstract class MessageProcessorBase<MP extends MessageProcessor<MP>> implements MessageProcessor<MP>
{
	private static final Object QUEUES_LOCKER = new Object();
	private static final Map<String, Queue> queues = new HashMap<String, Queue>();

	private Request<MP> request;

	protected abstract void addHeaders(TextMessage message) throws JMSException;

	/**
	 * @return идентификатор запроса (для логирования)
	 */
	protected abstract String getRequestId();

	/**
	 * @return идентификатор системы (для логирования)
	 */
	protected abstract String getRequestSystemId();

	/**
	 * @return тип запроса (для логирования)
	 */
	protected abstract String getRequestMessageType();

	/**
	 * инициализация процессора (сборка запроса)
	 * @return объект запроса
	 * @throws GateException
	 * @throws GateLogicException
	 */
	protected abstract Object initialize() throws GateException, GateLogicException;

	public final Request<MP> makeRequest() throws GateException, GateLogicException
	{
		Object requestObject = initialize();
		String requestString = JAXBMessageHelper.getInstance().buildMessage(requestObject);
		//noinspection unchecked
		request = new Request<MP>(getRequestId(), getRequestMessageType(), requestString, (MP) this, getRequestSystemId());
		return request;
	}

	protected final Request<MP> getRequest()
	{
		return request;
	}

	protected final void setReplyTo(TextMessage message, String queueName) throws JMSException
	{
		message.setJMSReplyTo(getQueue(queueName));
	}

	public final TextMessage create(Session session, Serializable object) throws JMSException
	{
		if (!(object instanceof String))
			throw new JMSException("Некорректный тип объекта " + object.getClass());

		TextMessage message = session.createTextMessage((String) object);
		addHeaders(message);
		return message;
	}

	private Queue getQueue(String queueName) throws JMSException
	{
		Queue queue = queues.get(queueName);
		if (queue != null)
			return queue;

		synchronized (QUEUES_LOCKER)
		{
			queue = queues.get(queueName);
			if (queue != null)
				return queue;

			try
			{
				InitialContext ctx = new InitialContext();
				queue = (Queue) ctx.lookup(queueName);
				queues.put(queueName, queue);
			}
			catch (NamingException ne)
			{
				JMSException jmsException = new JMSException("Ошибка создания очереди " + queueName);
				jmsException.setLinkedException(ne);
				throw jmsException;
			}
		}

		return queue;
	}
}
