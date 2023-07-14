package com.rssl.phizic.ejbtest.service;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.utils.jms.JmsService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author komarov
 * @ created 07.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class JMSTransportTestProvider
{
	private static final Object LOCKER = new Object();
	private static final String DESTINATION_QUEUE_NAME = "jms/esb/esbOutQueue";
	private static final String QUEUE_FACTORY = "jms/esb/esbQCF";

	private static volatile JMSTransportTestProvider INSTANCE = null;

	private static final JmsService jmsService = new JmsService();

	private QueueConnectionFactory factory;
	private Queue queue;

	private JMSTransportTestProvider()
	{
		InitialContext ctx = null;
		try
		{
			ctx = new InitialContext();
			factory = (QueueConnectionFactory)ctx.lookup(QUEUE_FACTORY);
			queue = (Queue) ctx.lookup(DESTINATION_QUEUE_NAME);
		}
		catch (NamingException e)
		{
			throw new InternalErrorException("[ESB] Сбой при инициализации JMSTransportTestProvider", e);
		}
		finally
		{
			if (ctx != null)
				try { ctx.close(); } catch (NamingException ignored) {}
		}
	}

	/**
	 * @return инстанс
	 */
	public static JMSTransportTestProvider getInstance()
	{
		if(INSTANCE != null)
			return INSTANCE;

		synchronized (LOCKER)
		{
			if (INSTANCE == null)
			{
				INSTANCE = new JMSTransportTestProvider();
			}
			return INSTANCE;
		}
	}

	public Message doRequest(String xml, boolean synchronous) throws JMSException
	{
		Message message = jmsService.sendToQueue(xml, queue, factory, ESBMessageTestCreator.getInstance());
		if (!synchronous)
			return message;

		return jmsService.receiveMessageFormQueue(DESTINATION_QUEUE_NAME, QUEUE_FACTORY, null, 1000000);
	}
}
