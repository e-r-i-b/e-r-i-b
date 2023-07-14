package com.rssl.phizic.gate.monitoring.fraud.jms.senders;

import com.rssl.phizic.gate.monitoring.fraud.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.jms.JmsService;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import static com.rssl.phizic.logging.Constants.LOG_MODULE_GATE;

/**
 * @author khudyakov
 * @ created 15.06.15
 * @ $Author$
 * @ $Revision$
 */
public class BlockClientJMSSender
{
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_GATE);
	private static final JmsService jmsService = new JmsService();

	private static final Queue queue;
	private static final QueueConnectionFactory factory;

	static
	{
		InitialContext ctx = null;

		try
		{
			ctx = new InitialContext();
			queue = (Queue) ctx.lookup(Constants.BLOCKING_CLIENT_QUEUE_NAME);
			factory = (QueueConnectionFactory) ctx.lookup(Constants.BLOCKING_CLIENT_QUEUE_FACTORY_NAME);
		}
		catch (NamingException e)
		{
			log.error(e.getMessage(), e);
			//noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
			throw new RuntimeException(e.getLocalizedMessage());
		}
		finally
		{
			if (ctx != null)
			{
				try { ctx.close(); } catch (NamingException ignored) {}
			}
		}
	}

	/**
	 * Отправляет текстовое сообщение в очередь
	 * @param message сообщение
	 * @param correlationId correlationId
	 * @throws javax.jms.JMSException
	 */
	public static void sendToQueue(String message, String correlationId) throws JMSException
	{
		jmsService.sendToQueue(message, queue, factory, new FraudMonitoringMessageCreator(correlationId), correlationId);
	}
}
