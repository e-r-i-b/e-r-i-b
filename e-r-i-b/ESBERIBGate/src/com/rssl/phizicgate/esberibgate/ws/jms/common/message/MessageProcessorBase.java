package com.rssl.phizicgate.esberibgate.ws.jms.common.message;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;

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
 * @ created 20.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * базовый процессор jms запросов в шину
 */

public abstract class MessageProcessorBase<MP extends MessageProcessor<MP>> implements MessageProcessor<MP>
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final Object QUEUES_LOCKER = new Object();
	private static final Map<String, Queue> queues = new HashMap<String, Queue>();

	private final ESBSegment segment;
	private Request<MP> request;

	protected MessageProcessorBase(ESBSegment segment)
	{
		this.segment = segment;
	}

	public final ESBSegment getSegment()
	{
		return segment;
	}

	protected void addHeaders(TextMessage message) throws JMSException
	{
		setGroupId(message);
	}

	protected abstract String getRequestId();
	protected abstract String getRequestSystemId();
	protected abstract String getRequestMessageType();
	protected abstract String getMonitoringDocumentType();

	protected abstract Object initialize() throws GateException, GateLogicException;

	public final Request<MP> makeRequest() throws GateException, GateLogicException
	{
		Object requestObject = initialize();
		String requestString = getSegment().getMessageBuilder().buildMessage(requestObject);
		//noinspection unchecked
		request = new Request<MP>(getRequestId(), getRequestMessageType(), getMonitoringDocumentType(), requestString, (MP) this, getRequestSystemId());
		return request;
	}

	protected final Request<MP> getRequest()
	{
		return request;
	}

	protected final void addStringHeader(TextMessage message, String key, String value) throws JMSException
	{
		message.setStringProperty(key, value);
	}

	protected final void addBooleanHeader(TextMessage message, String key, boolean value) throws JMSException
	{
		message.setBooleanProperty(key, value);
	}

	protected final void setReplyTo(TextMessage message, String queueName)  throws JMSException
	{
		message.setJMSReplyTo(getQueue(queueName));
	}

	protected final String getGroupId()
	{
		return ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getGroupId();
	}

	private void setGroupId(TextMessage message) throws JMSException
	{
		String groupId = getGroupId();
		if(StringHelper.isEmpty(groupId))
			return;

		addStringHeader(message, "JMSXGroupID", groupId);
		addBooleanHeader(message, "JMS_IBM_Last_Msg_In_Group", true);
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
				//noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
				throw new JMSException("Ошибка создания очереди " + queueName + ": " + ne.getLocalizedMessage());
			}
		}

		return queue;
	}
}
